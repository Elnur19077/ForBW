package bw.black.repository;

import bw.black.entity.RfqRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RfqRequestRepository extends JpaRepository<RfqRequest, Long> {
}
