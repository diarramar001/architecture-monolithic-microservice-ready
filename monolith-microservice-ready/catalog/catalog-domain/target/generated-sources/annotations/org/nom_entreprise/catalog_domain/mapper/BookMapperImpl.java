package org.nom_entreprise.catalog_domain.mapper;

import javax.annotation.processing.Generated;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-17T19:25:38+0000",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto toDto(CatalogBook catalogBook) {
        if ( catalogBook == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        CatalogBook.Barcode catalogNumber = null;
        String isbn = null;
        CatalogBook.Author author = null;

        id = map( catalogBook.getId() );
        title = catalogBook.getTitle();
        catalogNumber = catalogBook.getCatalogNumber();
        isbn = catalogBook.getIsbn();
        author = catalogBook.getAuthor();

        BookDto bookDto = new BookDto( id, title, catalogNumber, isbn, author );

        return bookDto;
    }

    @Override
    public CatalogBook toEntity(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        String title = null;
        CatalogBook.Barcode catalogNumber = null;
        String isbn = null;
        CatalogBook.Author author = null;

        title = bookDto.title();
        catalogNumber = bookDto.catalogNumber();
        isbn = bookDto.isbn();
        author = bookDto.author();

        CatalogBook catalogBook = new CatalogBook( title, catalogNumber, isbn, author );

        catalogBook.setId( map( bookDto.id() ) );

        return catalogBook;
    }
}
