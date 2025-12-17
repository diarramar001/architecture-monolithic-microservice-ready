package org.nom_entreprise.borrow_infrastructure.database.repository;

import org.nom_entreprise.borrow_infrastructure.database.model.HoldDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoldRepository extends JpaRepository<HoldDao, String> {
}
