package bw.black.repository;

import bw.black.entity.CostCalculation;
import bw.black.entity.LukoilCostCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LukoilCostCalculationRepository  extends JpaRepository<LukoilCostCalculation,Long> {
    List<LukoilCostCalculation> findByProductNameContainingIgnoreCase(String keyword);
}
