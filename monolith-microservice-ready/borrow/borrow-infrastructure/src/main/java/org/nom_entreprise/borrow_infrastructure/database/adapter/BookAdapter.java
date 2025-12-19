package org.nom_entreprise.borrow_infrastructure.database.adapter;

import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.port.out.BookPersistencePort;
import org.nom_entreprise.borrow_infrastructure.database.mapper.BookMapper;
import org.nom_entreprise.borrow_infrastructure.database.model.BookDao;
import org.nom_entreprise.borrow_infrastructure.database.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookAdapter implements BookPersistencePort {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Optional<Book> findAvailableBook(Book.Barcode inventoryNumber) {
        if (inventoryNumber == null || inventoryNumber.barcode() == null) {
            return Optional.empty();
        }
        Optional<BookDao> bookDao = bookRepository.findByInventoryNumberAndStatus(
                inventoryNumber.barcode(), 
                BookDao.BookStatus.AVAILABLE
        );
        return bookDao.map(BookMapper.INSTANCE::mapToModel);
    }

    @Override
    public Optional<Book> findOnHoldBook(Book.Barcode inventoryNumber) {
        if (inventoryNumber == null || inventoryNumber.barcode() == null) {
            return Optional.empty();
        }
        Optional<BookDao> bookDao = bookRepository.findByInventoryNumberAndStatus(
                inventoryNumber.barcode(), 
                BookDao.BookStatus.ON_HOLD
        );
        return bookDao.map(BookMapper.INSTANCE::mapToModel);
    }

    @Override
    public Book save(Book book) {
        if (book == null) {
            return null;
        }
        BookDao bookDao = BookMapper.INSTANCE.mapToEntity(book);
        BookDao savedDao = bookRepository.save(bookDao);
        return BookMapper.INSTANCE.mapToModel(savedDao);
    }

    @Override
    public Optional<Book> findByBarcode(String barcode) {
        if (barcode == null || barcode.isEmpty()) {
            return Optional.empty();
        }
        Optional<BookDao> bookDao = bookRepository.findByInventoryNumber(barcode);
        return bookDao.map(BookMapper.INSTANCE::mapToModel);
    }

}
