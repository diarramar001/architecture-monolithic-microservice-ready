package org.nom_entreprise.borrow_infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_infrastructure.database.model.HoldDao;

import java.util.List;
import java.util.UUID;

@Mapper
public interface HoldMapper {
    HoldMapper INSTANCE = Mappers.getMapper(HoldMapper.class);
    
    Hold mapToModel(HoldDao holdDao);
    
    @Mapping(target = "status", expression = "java(org.nom_entreprise.borrow_infrastructure.database.model.HoldDao.HoldStatus.HOLDING)")
    HoldDao mapToEntity(Hold hold);
    
    List<Hold> mapToModelList(List<HoldDao> holdDaoList);
    List<HoldDao> mapToEntityList(List<Hold> holdList);
    
    // Mapping String -> Hold.HoldId
    default Hold.HoldId map(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new Hold.HoldId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    // Mapping Hold.HoldId -> String
    default String map(Hold.HoldId value) {
        if (value == null || value.id() == null) {
            return null;
        }
        return value.id().toString();
    }
    
    // Mapping String -> Book.Barcode
    default Book.Barcode mapBarcode(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new Book.Barcode(value);
    }
    
    // Mapping Book.Barcode -> String
    default String mapBarcode(Book.Barcode value) {
        if (value == null) {
            return null;
        }
        return value.barcode();
    }
}
