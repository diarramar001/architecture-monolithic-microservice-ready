package org.nom_entreprise.borrow_infrastructure.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "brw_book")
public class BookDao {
    @Id
    private String id;
    private String title;
    private String isbn;
    private String inventoryNumber;
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    public enum BookStatus {
        AVAILABLE, ON_HOLD, ISSUED
    }
}
