package bw.black.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import oracle.sql.TIMESTAMP;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Entity
@Table(name = "HRR")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HRR {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HRR_seq")
    @SequenceGenerator(name = "hrr_seq", sequenceName = "HRR_SEQ", allocationSize = 1)
    private Long id;
    private String personId;
    private String Name;
    private String Department;


    private String attendanceStatus;
    private String attendanceCheckPoint;
    private String customName;
    private String dataSource;
    private String HandlingType;
    private String Temperature;
    private String Abnormal;

    private String timee;
}
