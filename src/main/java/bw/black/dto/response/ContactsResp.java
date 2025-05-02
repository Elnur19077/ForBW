package bw.black.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactsResp {
    private Long id;
    private String companyName;
    private String email;
    private String address;
    private String mobile;
    private String phone;
    private String website;
    private String productRange;
    private String brand;
    private String stakeHolders;
}
