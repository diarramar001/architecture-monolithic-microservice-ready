package org.nom_entreprise.catalog_infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_infrastructure.database.model.CatalogBookDao;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CatalogBookMapper {
    CatalogBookMapper INSTANCE = Mappers.getMapper(CatalogBookMapper.class);
    CatalogBook mapToModel(CatalogBookDao catalogBookDao);
    CatalogBookDao mapToEntity(CatalogBook catalogBook);
    List<CatalogBook> mapToModelList(List<CatalogBookDao> catalogBookDaoList);
    List<CatalogBookDao> mapToEntityList(List<CatalogBook> catalogBookList);
    default Page<CatalogBook> mapToPage(Page<CatalogBookDao> page) {
        return page.map(this::mapToModel);
    }
    
    // Mapping String -> CatalogBook.CatalogBookId
    default CatalogBook.CatalogBookId map(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new CatalogBook.CatalogBookId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    // Mapping CatalogBook.CatalogBookId -> String
    default String map(CatalogBook.CatalogBookId value) {
        if (value == null || value.id() == null) {
            return null;
        }
        return value.id().toString();
    }
    
    // Mapping String -> CatalogBook.Barcode
    default CatalogBook.Barcode mapBarcode(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new CatalogBook.Barcode(value);
    }
    
    // Mapping CatalogBook.Barcode -> String
    default String mapBarcode(CatalogBook.Barcode value) {
        if (value == null) {
            return null;
        }
        return value.barcode();
    }
    
    // Mapping String -> CatalogBook.Author
    default CatalogBook.Author mapAuthor(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new CatalogBook.Author(value);
    }
    
    // Mapping CatalogBook.Author -> String
    default String mapAuthor(CatalogBook.Author value) {
        if (value == null) {
            return null;
        }
        return value.name();
    }
}
