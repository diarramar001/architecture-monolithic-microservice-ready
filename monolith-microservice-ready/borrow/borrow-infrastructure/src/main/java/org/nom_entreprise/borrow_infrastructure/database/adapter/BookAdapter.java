package org.nom_entreprise.borrow_infrastructure.database.adapter;

import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.port.out.BookPersistencePort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookAdapter implements BookPersistencePort {

    @Override
    public Optional<Book> findAvailableBook(Book.Barcode inventoryNumber) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> findOnHoldBook(Book.Barcode inventoryNumber) {
        return Optional.empty();
    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findByBarcode(String barcode) {
        return Optional.empty();
    }

}
