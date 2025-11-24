package org.nom_entreprise.catalog_domain.model;

import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.annotation.AggregateRoot;

@AggregateRoot
@Getter
@Setter
public class CatalogBook {
    private Long id;
    private String title;
    private Barcode catalogNumber;
    private String isbn;
    private Author author;
    private Long version;

    public CatalogBook(String title, Barcode catalogNumber, String isbn, Author author) {
        this.title = title;
        this.catalogNumber = catalogNumber;
        this.isbn = isbn;
        this.author = author;
    }

    public record Barcode(String barcode) {
    }

    public record Author(String name) {
    }
}
