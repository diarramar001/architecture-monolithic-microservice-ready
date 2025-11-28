package org.nom_entreprise.borrow_domain.event.producer;

import org.jmolecules.event.annotation.DomainEvent;

import java.time.LocalDate;
import java.util.UUID;

@DomainEvent
public record BookPlacedOnHold(UUID holdId,
                               String inventoryNumber,
                               LocalDate dateOfHold) {
}
