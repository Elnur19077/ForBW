package bw.black.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetEmployeeInfoResponse {
    private String name;
    private String surname;
}
