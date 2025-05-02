package bw.black.repository;

import bw.black.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    List<Contacts> findAllByBrand(String brand);

    List<Contacts> findAllByCompanyName(String company);

    List<Contacts> findAllByStakeHolders(String stakeHolders);

   Contacts findByIdAndActive(Long id, Integer active);

}
