package bw.black.repository;

import bw.black.entity.UaeService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UaeServiceRepository extends JpaRepository<UaeService,Long>{
}
