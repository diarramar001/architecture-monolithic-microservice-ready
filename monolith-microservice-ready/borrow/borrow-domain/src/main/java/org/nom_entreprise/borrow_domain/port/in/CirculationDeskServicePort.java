package org.nom_entreprise.borrow_domain.port.in;

import org.nom_entreprise.borrow_domain.dto.HoldDto;
import org.nom_entreprise.common.event.BookAddedToCatalog;
import org.nom_entreprise.common.event.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Hold;

import java.util.Optional;
import java.util.UUID;

public interface CirculationDeskServicePort {
    HoldDto placeHold(Hold.PlaceHold command);
    Optional<HoldDto> locate(UUID holdId);
    void handle(BookPlacedOnHold event);
    void handle(BookAddedToCatalog event);
}
