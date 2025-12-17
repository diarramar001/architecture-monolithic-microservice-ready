package org.nom_entreprise.catalog_domain.port.in;

import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;

import java.util.List;
import java.util.Optional;

public interface CatalogBookServicePort {
    BookDto addToCatalog(String title, CatalogBook.Barcode catalogNumber, String isbn, String authorName);
    Optional<BookDto> locate(Long id);
    List<BookDto> fetchBooks();
}
