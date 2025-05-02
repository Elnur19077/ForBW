package bw.black.dto.request;

import bw.black.enums.Role;
import lombok.Data;

@Data
public class ReqEmployee {
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Role role;
    private String password;
}
