package org.nom_entreprise.borrow_domain.model;

import lombok.Setter;
import org.jmolecules.ddd.types.Identifier;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.UnaryOperator;

import lombok.Getter;

@Setter
@Getter
public class Hold {

    private HoldId id;
    private Book.Barcode onBook;
    private LocalDate dateOfHold;

    private Hold(PlaceHold placeHold) {
        this.id = new HoldId(UUID.randomUUID());
        this.onBook = placeHold.inventoryNumber();
        this.dateOfHold = placeHold.dateOfHold();
    }

    public Hold() {
    }

    public static Hold placeHold(PlaceHold command) {
        return new Hold(command);
    }

    public Hold then(UnaryOperator<Hold> function) {
        return function.apply(this);
    }

    public record HoldId(UUID id) implements Identifier {
    }

    public record PlaceHold(Book.Barcode inventoryNumber, LocalDate dateOfHold) {
    }
}
