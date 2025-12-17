package org.nom_entreprise.borrow_domain.service;

import org.jmolecules.architecture.hexagonal.Port;
import org.nom_entreprise.borrow_domain.dto.HoldDto;
import org.nom_entreprise.borrow_domain.event.consumer.BookAddedToCatalog;
import org.nom_entreprise.borrow_domain.event.producer.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.in.CirculationDeskServicePort;
import org.nom_entreprise.borrow_domain.port.out.BookPersistencePort;
import org.nom_entreprise.borrow_domain.port.out.HoldEventPublisherPort;
import org.nom_entreprise.borrow_domain.port.out.HoldPersistencePort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Port
@Service
@Transactional
public class CirculationDeskServiceImpl implements CirculationDeskServicePort {

    private final BookPersistencePort bookPersistencePort;
    private final HoldPersistencePort holdPersistencePort;
    private final HoldEventPublisherPort eventPublisher;

    public CirculationDeskServiceImpl(BookPersistencePort bookPersistencePort,
                                      HoldPersistencePort holdPersistencePort,
                                      HoldEventPublisherPort eventPublisher) {
        this.bookPersistencePort = bookPersistencePort;
        this.holdPersistencePort = holdPersistencePort;
        this.eventPublisher = eventPublisher;
    }

    public HoldDto placeHold(Hold.PlaceHold command) {
        bookPersistencePort.findAvailableBook(command.inventoryNumber())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return HoldDto.from(
                Hold.placeHold(command)
                        .then(holdPersistencePort::save)
                        .then(eventPublisher::holdPlaced)
        );
    }

    public Optional<HoldDto> locate(UUID holdId) {
        return holdPersistencePort.findById(new Hold.HoldId(holdId))
                .map(HoldDto::from);
    }


    @ApplicationModuleListener
    public void handle(BookPlacedOnHold event) {
        bookPersistencePort.findAvailableBook(new Book.Barcode(event.inventoryNumber()))
                .map(Book::markOnHold)
                .map(bookPersistencePort::save)
                .orElseThrow(() -> new IllegalArgumentException("Duplicate hold?"));
    }

    @ApplicationModuleListener
    public void handle(BookAddedToCatalog event) {
        var command = new Book.AddBook(new Book.Barcode(event.inventoryNumber()), event.title(), event.isbn());
        bookPersistencePort.save(Book.addBook(command));
    }


}
