package bw.black.repository;

import bw.black.dto.response.HrrResponse;
import bw.black.entity.HRR;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HrrRepository extends JpaRepository<HRR, Long> {


    @Query(value = """
            SELECT person_id,
                   name,
                   department,
                   "pg_catalog".DATE_TRUNC('day', time) AS attendance_date,
                   MIN(time) AS first_check_in
            FROM hrr
            WHERE time >= CAST(:startDate AS DATE)
              AND time < CAST(:endDate AS DATE)
            GROUP BY person_id, name, department, DATE_TRUNC('day', time)
            ORDER BY attendance_date, person_id;
            """, nativeQuery = true)
    List<Object[]> getAttendance(@Param("startDate") String startDate,
                                 @Param("endDate") String endDate);
}




