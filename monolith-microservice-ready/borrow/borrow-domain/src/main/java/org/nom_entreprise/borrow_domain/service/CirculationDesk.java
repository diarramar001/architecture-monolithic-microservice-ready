package org.nom_entreprise.borrow_domain.service;

import org.jmolecules.architecture.hexagonal.Port;
import org.nom_entreprise.borrow_domain.dto.HoldDto;
import org.nom_entreprise.borrow_domain.event.BookAddedToCatalog;
import org.nom_entreprise.borrow_domain.event.BookPlacedOnHold;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.BookRepositoryPort;
import org.nom_entreprise.borrow_domain.port.HoldEventPublisherPort;
import org.nom_entreprise.borrow_domain.port.HoldRepositoryPort;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Port
@Service
@Transactional
public class CirculationDesk {

    private final BookRepositoryPort books;
    private final HoldRepositoryPort holds;
    private final HoldEventPublisherPort eventPublisher;

    public CirculationDesk(BookRepositoryPort books, HoldRepositoryPort holds, HoldEventPublisherPort eventPublisher) {
        this.books = books;
        this.holds = holds;
        this.eventPublisher = eventPublisher;
    }

    public HoldDto placeHold(Hold.PlaceHold command) {
        books.findAvailableBook(command.inventoryNumber())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        return HoldDto.from(
                Hold.placeHold(command)
                        .then(holds::save)
                        .then(eventPublisher::holdPlaced)
        );
    }

    public Optional<HoldDto> locate(UUID holdId) {
        return holds.findById(new Hold.HoldId(holdId))
                .map(HoldDto::from);
    }


    @ApplicationModuleListener
    public void handle(BookPlacedOnHold event) {
        books.findAvailableBook(new Book.Barcode(event.inventoryNumber()))
                .map(Book::markOnHold)
                .map(books::save)
                .orElseThrow(() -> new IllegalArgumentException("Duplicate hold?"));
    }

    @ApplicationModuleListener
    public void handle(BookAddedToCatalog event) {
        var command = new Book.AddBook(new Book.Barcode(event.inventoryNumber()), event.title(), event.isbn());
        books.save(Book.addBook(command));
    }


}
