package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "zone")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_seq")
    @SequenceGenerator(name = "zone_seq", sequenceName = "zone_seq", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneCountry> countries;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZonePricing> pricingList;
}
