package org.nom_entreprise.catalog_infrastructure.database.repository;

import org.nom_entreprise.catalog_infrastructure.database.model.CatalogBookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogBookRepository extends JpaRepository<CatalogBookDao, String> {
}
