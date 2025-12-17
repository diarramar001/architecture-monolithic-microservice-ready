package org.nom_entreprise.catalog_infrastructure.database.adapter;

import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_domain.port.out.CatalogBookPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogBookAdapter implements CatalogBookPersistencePort {

    @Override
    public CatalogBook save(CatalogBook book) {
        return null;
    }

    @Override
    public CatalogBook findById(Long id) {
        return null;
    }

    @Override
    public List<CatalogBook> findAll() {
        return null;
    }

}
