package org.nom_entreprise.borrow_domain.port.out;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.nom_entreprise.borrow_domain.event.producer.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Hold;

@SecondaryPort
public interface HoldEventPublisherPort {

    void holdPlaced(BookPlacedOnHold event);

    default Hold holdPlaced(Hold hold) {
        BookPlacedOnHold event = new BookPlacedOnHold(hold.getId().id(), hold.getOnBook().barcode(), hold.getDateOfHold());
        this.holdPlaced(event);
        return hold;
    }

}
