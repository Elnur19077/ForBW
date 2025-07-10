package bw.black.repository;

import bw.black.entity.LukoilCostCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LukoilCostCalculationRepository  extends JpaRepository<LukoilCostCalculation,Long> {
}
