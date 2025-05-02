package bw.black.dto.request;

import lombok.Data;

@Data
public class ContactReq {
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
