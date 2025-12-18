package org.nom_entreprise.borrow_infrastructure.events;

import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.nom_entreprise.common.event.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.port.out.HoldEventPublisherPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@SecondaryAdapter
@Component
@RequiredArgsConstructor
public class BorrowEventsPublisher implements HoldEventPublisherPort {

    private final ApplicationEventPublisher publisher;

    @Override
    public void holdPlaced(BookPlacedOnHold event) {
        publisher.publishEvent(event);
    }

}
