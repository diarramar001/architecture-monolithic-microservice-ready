package org.nom_entreprise.catalog_domain.port;

import org.nom_entreprise.catalog_domain.model.CatalogBook;

import java.util.List;

public interface CatalogRepositoryPort {
    CatalogBook save(CatalogBook book);
    CatalogBook findById(Long id);
    List<CatalogBook> findAll();
}
