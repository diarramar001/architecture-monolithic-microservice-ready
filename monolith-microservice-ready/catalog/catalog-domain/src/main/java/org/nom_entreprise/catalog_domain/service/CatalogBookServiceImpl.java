package org.nom_entreprise.catalog_domain.service;

import org.nom_entreprise.common.event.BookAddedToCatalog;
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
import lombok.RequiredArgsConstructor;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class CatalogBookServiceImpl implements CatalogBookServicePort {

    private final CatalogBookPersistencePort catalogBookPersistencePort;
    private final BookMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

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
        try {
            eventPublisher.publishEvent(event);
            log.info("[CATALOG] Événement BookAddedToCatalog publié avec succès");
        } catch (Exception e) {
            log.error("[CATALOG] Erreur lors de la publication de l'événement BookAddedToCatalog", e);
            throw e;
        }
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
