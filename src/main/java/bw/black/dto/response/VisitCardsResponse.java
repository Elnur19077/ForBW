package bw.black.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitCardsResponse {
    private Long id;
    private String name;
    private String company;
    private String jobTitle;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String email;
    private String phone;
    private String website;
    private String phone2;
    private String phone3;
    private String email2;
}
