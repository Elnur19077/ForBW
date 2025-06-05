package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "zoneCountry")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZoneCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_country_seq")
    @SequenceGenerator(name = "zone_country_seq", sequenceName = "zone_country_seq", allocationSize = 1)
    private Long id;

    private String name; // Məsələn: "Turkey"

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;
}
