package bw.black.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "zonePricing")
@Setter
@Getter
@DynamicInsert
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZonePricing {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_pricing_seq")
    @SequenceGenerator(name = "zone_pricing_seq", sequenceName = "zone_pricing_seq", allocationSize = 1)
    private Long id;

    private double weight;

    private double price;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;
}
