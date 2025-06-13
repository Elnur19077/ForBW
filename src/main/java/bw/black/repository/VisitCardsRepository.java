package bw.black.repository;

import bw.black.entity.VisitCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitCardsRepository extends JpaRepository<VisitCards, Long> {
}
