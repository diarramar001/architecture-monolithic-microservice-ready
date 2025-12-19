package org.nom_entreprise.catalog_domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookDto toDto(CatalogBook catalogBook);
    CatalogBook toEntity(BookDto bookDto);
    
    default String map(CatalogBook.CatalogBookId value) {
        if (value == null || value.id() == null) {
            return null;
        }
        return value.id().toString();
    }
    
    default CatalogBook.CatalogBookId map(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new CatalogBook.CatalogBookId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format: " + value, e);
        }
    }
}
