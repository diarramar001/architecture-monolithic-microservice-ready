package org.nom_entreprise.borrow_infrastructure.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "brw_hold")
public class HoldDao {
    @Id
    private String id;
    private String onBook;
    private LocalDate dateOfHold;
    @Enumerated(EnumType.STRING)
    private HoldStatus status;

    public enum HoldStatus {
        HOLDING, ACTIVE, COMPLETED
    }

}
