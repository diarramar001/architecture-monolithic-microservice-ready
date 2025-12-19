package org.nom_entreprise.borrow_infrastructure.database.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_infrastructure.database.model.BookDao;

import java.util.List;
import java.util.UUID;

@Mapper
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    
    default Book mapToModel(BookDao bookDao) {
        if (bookDao == null) {
            return null;
        }
        Book.BookId id = map(bookDao.getId());
        Book.Barcode barcode = mapBarcode(bookDao.getInventoryNumber());
        Book.BookStatus status = mapStatus(bookDao.getStatus());
        return Book.toBook(id, barcode, bookDao.getTitle(), bookDao.getIsbn(), status);
    }
    
    default BookDao mapToEntity(Book book) {
        if (book == null) {
            return null;
        }
        BookDao bookDao = new BookDao();
        bookDao.setId(map(book.getId()));
        bookDao.setTitle(book.getTitle());
        bookDao.setIsbn(book.getIsbn());
        bookDao.setInventoryNumber(mapBarcode(book.getInventoryNumber()));
        bookDao.setStatus(mapStatus(book.getStatus()));
        return bookDao;
    }
    List<Book> mapToModelList(List<BookDao> bookDaoList);
    List<BookDao> mapToEntityList(List<Book> bookList);
    
    // Mapping String -> Book.BookId
    default Book.BookId map(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return new Book.BookId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    // Mapping Book.BookId -> String
    default String map(Book.BookId value) {
        if (value == null || value.id() == null) {
            return null;
        }
        return value.id().toString();
    }
    
    // Mapping String -> Book.Barcode
    default Book.Barcode mapBarcode(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return new Book.Barcode(value);
    }
    
    // Mapping Book.Barcode -> String
    default String mapBarcode(Book.Barcode value) {
        if (value == null) {
            return null;
        }
        return value.barcode();
    }
    
    // Mapping BookDao.BookStatus -> Book.BookStatus
    default Book.BookStatus mapStatus(BookDao.BookStatus value) {
        if (value == null) {
            return null;
        }
        return Book.BookStatus.valueOf(value.name());
    }
    
    // Mapping Book.BookStatus -> BookDao.BookStatus
    default BookDao.BookStatus mapStatus(Book.BookStatus value) {
        if (value == null) {
            return null;
        }
        return BookDao.BookStatus.valueOf(value.name());
    }
}
