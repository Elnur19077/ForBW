package bw.black.entity;

import bw.black.confiq.CustomLocalDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HRR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personId;
    private String name;
    private String department;
    @Column(name = "time", columnDefinition = "TIMESTAMP")
    @JsonDeserialize(using = CustomLocalDateTimeDeserializer.class)
    private LocalDateTime time;
    private String attendanceStatus;
    private String attendanceCheckPoint;
    private String customName;
    private String dataSource;
    private String handlingType;
    private String temperature;
    private String abnormal;
}
