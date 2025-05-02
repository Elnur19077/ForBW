package bw.black.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
@Data

@NoArgsConstructor
@Getter
@Setter
public class HrrResponse {

    private String personId;
    private String name;
    private String department;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Baku")
    private Date attendanceDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Baku")
    private Date firstCheckIn;


    // Constructor, Getter və Setter metodları
    public HrrResponse(String personId, String name, String department, Date attendanceDate, Date firstCheckIn) {
        this.personId = personId;
        this.name = name;
        this.department = department;
        this.attendanceDate = attendanceDate;
        this.firstCheckIn = firstCheckIn;
    }
}