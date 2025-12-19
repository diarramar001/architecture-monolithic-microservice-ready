package org.nom_entreprise.borrow_infrastructure.database.adapter;

import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.out.HoldPersistencePort;
import org.nom_entreprise.borrow_infrastructure.database.mapper.HoldMapper;
import org.nom_entreprise.borrow_infrastructure.database.model.HoldDao;
import org.nom_entreprise.borrow_infrastructure.database.repository.HoldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class HoldAdapter implements HoldPersistencePort {

    @Autowired
    private HoldRepository holdRepository;

    @Override
    public Hold save(Hold hold) {
        if (hold == null) {
            return null;
        }
        HoldDao holdDao = HoldMapper.INSTANCE.mapToEntity(hold);
        HoldDao savedDao = holdRepository.save(holdDao);
        return HoldMapper.INSTANCE.mapToModel(savedDao);
    }

    @Override
    public Optional<Hold> findById(Hold.HoldId id) {
        if (id == null || id.id() == null) {
            return Optional.empty();
        }
        Optional<HoldDao> holdDao = holdRepository.findById(id.id().toString());
        return holdDao.map(HoldMapper.INSTANCE::mapToModel);
    }

    @Override
    public List<Hold> activeHolds() {
        List<HoldDao> holdDaoList = holdRepository.findByStatusIn(
                Arrays.asList(HoldDao.HoldStatus.HOLDING, HoldDao.HoldStatus.ACTIVE)
        );
        return HoldMapper.INSTANCE.mapToModelList(holdDaoList);
    }

}
