package org.nom_entreprise.borrow_domain.port.out;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.nom_entreprise.borrow_domain.model.Hold;

import java.util.List;
import java.util.Optional;

@SecondaryPort
public interface HoldPersistencePort {
    Hold save(Hold hold);
    Optional<Hold> findById(Hold.HoldId id);
    List<Hold> activeHolds();
}
