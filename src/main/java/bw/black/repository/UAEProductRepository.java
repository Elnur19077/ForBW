package bw.black.repository;

import bw.black.entity.UAEProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UAEProductRepository extends JpaRepository<UAEProduct, Long> {
    List<UAEProduct> findByProductNameContainingIgnoreCase(String name);
}