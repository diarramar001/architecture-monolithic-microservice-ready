package org.nom_entreprise.catalog_domain.port.out;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.nom_entreprise.common.event.BookAddedToCatalog;

@SecondaryPort
public interface CatalogEventPublisherPort {

    void bookAddedToCatalog(BookAddedToCatalog event);

}
