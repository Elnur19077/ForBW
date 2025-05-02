package bw.black.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

@NoArgsConstructor
public class RespStatus {

    private Integer code;
    private String message;

    public static RespStatus getSuccessMessage() {
        return new RespStatus(1, "success");
    }
}
