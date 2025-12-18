package org.nom_entreprise.catalog_domain.service;

import org.nom_entreprise.common.event.BookAddedToCatalog;
import org.nom_entreprise.catalog_domain.mapper.BookMapper;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_domain.port.in.CatalogBookServicePort;
import org.nom_entreprise.catalog_domain.port.out.CatalogBookPersistencePort;
import org.nom_entreprise.catalog_domain.port.out.CatalogEventPublisherPort;
import org.springframework.context.annotation.Lazy;
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
    private final CatalogEventPublisherPort eventPublisher;

    public CatalogBookServiceImpl(
            CatalogBookPersistencePort catalogBookPersistencePort,
            BookMapper mapper,
            @Lazy CatalogEventPublisherPort eventPublisher
    ) {
        this.catalogBookPersistencePort = catalogBookPersistencePort;
        this.mapper = mapper;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Add a new book to the library.
     */
    public BookDto addToCatalog(String title, CatalogBook.Barcode catalogNumber, String isbn, String authorName) {
        log.info("[CATALOG] Début de l'ajout d'un livre au catalogue - title: {}, catalogNumber: {}, isbn: {}", title, catalogNumber, isbn);
        var book = new CatalogBook(title, catalogNumber, isbn, new CatalogBook.Author(authorName));
        var dto = mapper.toDto(catalogBookPersistencePort.save(book));
        log.info("[CATALOG] Livre sauvegardé dans la base de données - id: {}", dto.id());
        var event = new BookAddedToCatalog(dto.title(), dto.catalogNumber().barcode(), dto.isbn(), dto.author().name());
        log.info("[CATALOG] Création de l'événement BookAddedToCatalog - title: {}, inventoryNumber: {}, isbn: {}, author: {}", 
                event.title(), event.inventoryNumber(), event.isbn(), event.author());
        eventPublisher.bookAddedToCatalog(event);
        log.info("[CATALOG] Événement BookAddedToCatalog envoyé au port de publication");
        log.info("[CATALOG] Fin de l'ajout d'un livre au catalogue - la transaction va être commitée");
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
