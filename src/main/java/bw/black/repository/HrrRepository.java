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
        SELECT
           person_id,
           name,
           department,
           DATE_TRUNC('day', time) AS day,
           MIN(time) AS min_time
            FROM hrr
            WHERE time BETWEEN TO_TIMESTAMP(:startDate, 'YYYY-MM-DD')
                      AND TO_TIMESTAMP(:endDate, 'YYYY-MM-DD') + INTERVAL '1 day' - INTERVAL '1 second'
            GROUP BY person_id, name, department, DATE_TRUNC('day', time)
            ORDER BY DATE_TRUNC('day', time), person_id;
       
        """, nativeQuery = true)
    List<Object[]> getAttendance(@Param("startDate") String startDate,
                                 @Param("endDate") String endDate);


}
