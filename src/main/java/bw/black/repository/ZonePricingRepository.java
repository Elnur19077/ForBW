package bw.black.repository;

import bw.black.entity.Zone;
import bw.black.entity.ZonePricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ZonePricingRepository extends JpaRepository<ZonePricing, Long> {
    List<ZonePricing> findAllByZoneAndWeight(Zone zone, double weight);

}
