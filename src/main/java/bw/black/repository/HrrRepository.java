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
            PERSON_ID,
            NAME,
            DEPARTMENT,
            TRUNC(Timee),
            MIN(Time)
        FROM HRR
        WHERE Time BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') 
                      AND TO_DATE(:endDate, 'YYYY-MM-DD') + INTERVAL '1' DAY - INTERVAL '1' SECOND
        GROUP BY PERSON_ID, NAME, DEPARTMENT, TRUNC(Time)
        ORDER BY TRUNC(Time), PERSON_ID
        """, nativeQuery = true)
    List<Object[]> getAttendance(@Param("startDate") String startDate,
                                 @Param("endDate") String endDate);


}
