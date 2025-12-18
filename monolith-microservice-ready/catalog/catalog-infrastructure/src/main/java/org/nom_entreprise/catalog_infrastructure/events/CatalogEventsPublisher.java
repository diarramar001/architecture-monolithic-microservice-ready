package org.nom_entreprise.catalog_infrastructure.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.SecondaryAdapter;
import org.nom_entreprise.catalog_domain.port.out.CatalogEventPublisherPort;
import org.nom_entreprise.common.event.BookAddedToCatalog;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@SecondaryAdapter
@Service
@RequiredArgsConstructor
public class CatalogEventsPublisher implements CatalogEventPublisherPort {

    private final ApplicationEventPublisher publisher;

    @Override
    public void bookAddedToCatalog(BookAddedToCatalog event) {
        log.info("[CATALOG-INFRA] Publication de l'événement BookAddedToCatalog via ApplicationEventPublisher - title: {}, inventoryNumber: {}, isbn: {}, author: {}", 
                event.title(), event.inventoryNumber(), event.isbn(), event.author());
        publisher.publishEvent(event);
        log.info("[CATALOG-INFRA] Événement BookAddedToCatalog publié avec succès");
    }

}
