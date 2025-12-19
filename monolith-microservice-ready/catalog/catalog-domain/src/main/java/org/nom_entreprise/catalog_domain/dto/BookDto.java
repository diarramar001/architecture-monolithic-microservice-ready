package org.nom_entreprise.catalog_domain.dto;

import org.nom_entreprise.catalog_domain.model.CatalogBook;

public record BookDto(String id, String title, CatalogBook.Barcode catalogNumber,
                      String isbn, CatalogBook.Author author) {
}
