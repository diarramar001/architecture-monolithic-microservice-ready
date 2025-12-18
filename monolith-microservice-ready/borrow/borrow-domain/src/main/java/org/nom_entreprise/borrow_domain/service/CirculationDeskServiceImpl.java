package org.nom_entreprise.borrow_domain.service;

import lombok.extern.slf4j.Slf4j;
import org.jmolecules.architecture.hexagonal.Port;
import org.nom_entreprise.borrow_domain.dto.HoldDto;
import org.nom_entreprise.common.event.BookAddedToCatalog;
import org.nom_entreprise.common.event.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.in.CirculationDeskServicePort;
import org.nom_entreprise.borrow_domain.port.out.BookPersistencePort;
import org.nom_entreprise.borrow_domain.port.out.HoldPersistencePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.beans.factory.InitializingBean;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Port
@Service
@Transactional
public class CirculationDeskServiceImpl implements CirculationDeskServicePort, InitializingBean {

    private final BookPersistencePort bookPersistencePort;
    private final HoldPersistencePort holdPersistencePort;
    private final ApplicationEventPublisher eventPublisher;

    public CirculationDeskServiceImpl(BookPersistencePort bookPersistencePort,
                                      HoldPersistencePort holdPersistencePort,
                                      ApplicationEventPublisher eventPublisher) {
        this.bookPersistencePort = bookPersistencePort;
        this.holdPersistencePort = holdPersistencePort;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("[BORROW] CirculationDeskServiceImpl initialisé - les listeners d'événements sont prêts");
    }

    public HoldDto placeHold(Hold.PlaceHold command) {
        bookPersistencePort.findAvailableBook(command.inventoryNumber())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        var hold = Hold.placeHold(command);
        var savedHold = holdPersistencePort.save(hold);
        var event = new BookPlacedOnHold(savedHold.getId().id(), savedHold.getOnBook().barcode(), savedHold.getDateOfHold());
        eventPublisher.publishEvent(event);
        return HoldDto.from(savedHold);
    }

    public Optional<HoldDto> locate(UUID holdId) {
        return holdPersistencePort.findById(new Hold.HoldId(holdId))
                .map(HoldDto::from);
    }


    @EventListener
    @Transactional
    @Override
    public void handle(BookPlacedOnHold event) {
        log.info("[BORROW] Réception de l'événement BookPlacedOnHold - inventoryNumber: {}", event.inventoryNumber());
        bookPersistencePort.findAvailableBook(new Book.Barcode(event.inventoryNumber()))
                .map(Book::markOnHold)
                .map(bookPersistencePort::save)
                .orElseThrow(() -> new IllegalArgumentException("Duplicate hold?"));
        log.info("[BORROW] Livre marqué comme en attente - inventoryNumber: {}", event.inventoryNumber());
    }

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void handle(BookAddedToCatalog event) {
        log.info("[BORROW] ===== DÉBUT TRAITEMENT ÉVÉNEMENT BookAddedToCatalog =====");
        log.info("[BORROW] Réception de l'événement BookAddedToCatalog - title: {}, inventoryNumber: {}, isbn: {}, author: {}", 
                event.title(), event.inventoryNumber(), event.isbn(), event.author());
        try {
            var command = new Book.AddBook(new Book.Barcode(event.inventoryNumber()), event.title(), event.isbn());
            log.info("[BORROW] Création de la commande AddBook pour inventoryNumber: {}", event.inventoryNumber());
            bookPersistencePort.save(Book.addBook(command));
            log.info("[BORROW] Livre ajouté avec succès dans le module borrow - inventoryNumber: {}", event.inventoryNumber());
            log.info("[BORROW] ===== FIN TRAITEMENT ÉVÉNEMENT BookAddedToCatalog (SUCCÈS) =====");
        } catch (Exception e) {
            log.error("[BORROW] ===== ERREUR lors du traitement de l'événement BookAddedToCatalog =====", e);
            throw e;
        }
    }


}
