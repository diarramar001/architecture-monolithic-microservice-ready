package org.nom_entreprise.catalog_domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookDto toDto(CatalogBook catalogBook);
    CatalogBook toEntity(BookDto bookDto);
}
