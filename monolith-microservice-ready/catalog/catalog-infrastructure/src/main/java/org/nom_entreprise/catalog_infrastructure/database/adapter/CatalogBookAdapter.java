package org.nom_entreprise.catalog_infrastructure.database.adapter;

import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_domain.port.out.CatalogBookPersistencePort;
import org.nom_entreprise.catalog_infrastructure.database.mapper.CatalogBookMapper;
import org.nom_entreprise.catalog_infrastructure.database.model.CatalogBookDao;
import org.nom_entreprise.catalog_infrastructure.database.repository.CatalogBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogBookAdapter implements CatalogBookPersistencePort {

    @Autowired
    CatalogBookRepository catalogBookRepository;

    @Override
    public CatalogBook save(CatalogBook book) {
        CatalogBookDao catalogBookDao = CatalogBookMapper.INSTANCE.mapToEntity(book);
        CatalogBookDao savedDao = catalogBookRepository.save(catalogBookDao);
        return CatalogBookMapper.INSTANCE.mapToModel(savedDao);
    }

    @Override
    public CatalogBook findById(Long id) {
        CatalogBookDao catalogBookDao = catalogBookRepository.findById(String.valueOf(id)).orElse(null);
        if (catalogBookDao == null) {
            return null;
        }
        return CatalogBookMapper.INSTANCE.mapToModel(catalogBookDao);
    }

    @Override
    public List<CatalogBook> findAll() {
        List<CatalogBookDao> catalogBookDaoList = catalogBookRepository.findAll();
        return CatalogBookMapper.INSTANCE.mapToModelList(catalogBookDaoList);
    }

}
