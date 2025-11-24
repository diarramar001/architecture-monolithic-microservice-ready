package org.nom_entreprise.borrow_domain.port;

import org.jmolecules.architecture.hexagonal.SecondaryPort;
import org.nom_entreprise.borrow_domain.model.Book;

import java.util.Optional;

@SecondaryPort
public interface BookRepositoryPort {
    Optional<Book> findAvailableBook(Book.Barcode inventoryNumber);
    Optional<Book> findOnHoldBook(Book.Barcode inventoryNumber);
    Book save(Book book);
    Optional<Book> findByBarcode(String barcode);
}
