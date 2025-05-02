package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "contacts")
@Data
@DynamicInsert
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contacts_add_seq")
    @SequenceGenerator(name = "contacts_add_seq", sequenceName = "CONTACTS_ADD_SEQ", allocationSize = 1)
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
    private Integer active;

}