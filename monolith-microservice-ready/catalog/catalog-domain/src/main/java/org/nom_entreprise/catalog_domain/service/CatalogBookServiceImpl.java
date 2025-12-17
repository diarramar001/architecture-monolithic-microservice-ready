package org.nom_entreprise.catalog_domain.service;

import org.nom_entreprise.catalog_domain.event.procuder.BookAddedToCatalog;
import org.nom_entreprise.catalog_domain.mapper.BookMapper;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_domain.port.in.CatalogBookServicePort;
import org.nom_entreprise.catalog_domain.port.out.CatalogBookPersistencePort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class CatalogBookServiceImpl implements CatalogBookServicePort {

    private final CatalogBookPersistencePort catalogBookPersistencePort;
    private final BookMapper mapper;
    private final ApplicationEventPublisher events;

    public CatalogBookServiceImpl(
            CatalogBookPersistencePort catalogBookPersistencePort,
            BookMapper mapper,
            ApplicationEventPublisher events
    ) {
        this.catalogBookPersistencePort = catalogBookPersistencePort;
        this.mapper = mapper;
        this.events = events;
    }

    /**
     * Add a new book to the library.
     */
    public BookDto addToCatalog(String title, CatalogBook.Barcode catalogNumber, String isbn, String authorName) {
        var book = new CatalogBook(title, catalogNumber, isbn, new CatalogBook.Author(authorName));
        var dto = mapper.toDto(catalogBookPersistencePort.save(book));
        events.publishEvent(new BookAddedToCatalog(dto.title(), dto.catalogNumber().barcode(), dto.isbn(), dto.author().name()));
        return dto;
    }

    @Transactional(readOnly = true)
    public Optional<BookDto> locate(Long id) {
        CatalogBook catalogBook = catalogBookPersistencePort.findById(id);
        return Optional.ofNullable(catalogBook).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<BookDto> fetchBooks() {
        return catalogBookPersistencePort.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
