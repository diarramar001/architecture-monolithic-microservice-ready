package org.nom_entreprise.catalog_infrastructure.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ctg_catalog_book")
public class CatalogBookDao {
    @Id
    @Column(name = "id")
    private String id;
    private String title;
    private String catalogNumber;
    private String isbn;
    private String author;
    private Long version;
}
