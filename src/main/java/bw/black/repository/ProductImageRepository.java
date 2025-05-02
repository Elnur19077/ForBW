package bw.black.repository;

import bw.black.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findProductImageByContactsIdAndActive(Long contactsId, Integer active);
    ProductImage findProductImageByIdAndActive(Long imageId, Integer active);
    @Transactional
    @Modifying
    @Query("UPDATE ProductImage pi SET pi.active = 0 WHERE pi.contacts.id = :contactsId")
    void deactivateProductImagesByContactsId(@Param("contactsId") Long contactsId);
}
