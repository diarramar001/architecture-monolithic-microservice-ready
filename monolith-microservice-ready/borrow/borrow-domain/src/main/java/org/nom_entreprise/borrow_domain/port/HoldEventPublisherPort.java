package org.nom_entreprise.borrow_domain.port;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.nom_entreprise.borrow_domain.event.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Hold;

@SecondaryPort
public interface HoldEventPublisherPort {

    void holdPlaced(BookPlacedOnHold event);

    default Hold holdPlaced(Hold hold) {
        BookPlacedOnHold event = new BookPlacedOnHold(hold.getId().id(), hold.getOnBook().barcode(), hold.getDateOfHold());
        this.holdPlaced(event);
        return hold;
    }

//    void bookCheckedOut(Checkout.BookCheckedOut event);
//
//    default Checkout bookCheckedOut(Checkout checkout) {
//        Checkout.BookCheckedOut event = new Checkout.BookCheckedOut(checkout);
//        this.bookCheckedOut(event);
//        return checkout;
//    }
}
