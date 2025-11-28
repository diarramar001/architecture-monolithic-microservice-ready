package org.nom_entreprise.borrow_domain.event.consumer;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record BookAddedToCatalog(String title, String inventoryNumber,
                                 String isbn, String author) {
}
