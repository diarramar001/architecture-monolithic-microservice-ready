package org.nom_entreprise.borrow_database.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "brw_book")
public class BookDao {
    @Id
    private String id;
}
