package org.nom_entreprise.borrow_infrastructure.database.adapter;

import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.out.HoldPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoldAdapter implements HoldPersistencePort {

    @Override
    public Hold save(Hold hold) {
        return null;
    }

    @Override
    public Optional<Hold> findById(Hold.HoldId id) {
        return Optional.empty();
    }

    @Override
    public List<Hold> activeHolds() {
        return null;
    }

}
