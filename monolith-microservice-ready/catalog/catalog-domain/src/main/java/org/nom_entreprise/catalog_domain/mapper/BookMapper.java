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
    
    default Long map(CatalogBook.CatalogBookId value) {
        if (value == null || value.id() == null) {
            return null;
        }
        return value.id().getLeastSignificantBits();
    }
    
    default CatalogBook.CatalogBookId map(Long value) {
        if (value == null) {
            return null;
        }
        // Créer un UUID avec le Long comme bits les moins significatifs
        return new CatalogBook.CatalogBookId(new UUID(0, value));
    }
}
