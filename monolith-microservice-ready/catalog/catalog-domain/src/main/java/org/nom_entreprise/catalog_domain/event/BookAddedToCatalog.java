package org.nom_entreprise.catalog_domain.event;

import org.jmolecules.event.annotation.DomainEvent;

@DomainEvent
public record BookAddedToCatalog(String title, String inventoryNumber,
                                 String isbn, String author) {
}
