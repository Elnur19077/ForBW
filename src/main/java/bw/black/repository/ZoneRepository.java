package bw.black.repository;

import bw.black.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {

    @Query("SELECT z FROM Zone z JOIN z.countries c WHERE c.name = :country")
    Optional<Zone> findByCountry(@Param("country") String country);
}
