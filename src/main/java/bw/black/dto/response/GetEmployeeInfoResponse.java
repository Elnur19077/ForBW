package bw.black.dto.response;

import bw.black.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetEmployeeInfoResponse {
     private Long id;
    private String name;
    private String surname;
    private Role role;
}
