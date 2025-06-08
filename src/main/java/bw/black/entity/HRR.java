package bw.black.entity;

import bw.black.confiq.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

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
    @Column(name = "time")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime time;
}

