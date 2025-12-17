package org.nom_entreprise.catalog_controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.nom_entreprise.catalog_controller.dto.AddBookRequest;
import org.nom_entreprise.catalog_domain.dto.BookDto;
import org.nom_entreprise.catalog_domain.model.CatalogBook;
import org.nom_entreprise.catalog_domain.port.in.CatalogBookServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Catalog - Gestion du catalogue", description = "API pour la gestion du catalogue de livres")
public class CatalogBookController {

    private final CatalogBookServicePort catalogBookService;

    public CatalogBookController(CatalogBookServicePort catalogBookService) {
        this.catalogBookService = catalogBookService;
    }

    @PostMapping
    @Operation(summary = "Ajouter un livre au catalogue", description = "Ajoute un nouveau livre dans le catalogue de la bibliothèque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livre ajouté avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    public ResponseEntity<BookDto> addToCatalog(@Valid @RequestBody AddBookRequest request) {
        try {
            BookDto bookDto = catalogBookService.addToCatalog(
                    request.title(),
                    new CatalogBook.Barcode(request.catalogNumber()),
                    request.isbn(),
                    request.authorName()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(bookDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Localiser un livre", description = "Récupère les informations d'un livre par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livre trouvé"),
            @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    public ResponseEntity<BookDto> locateBook(
            @Parameter(description = "ID du livre", required = true)
            @PathVariable Long id) {
        Optional<BookDto> bookDto = catalogBookService.locate(id);
        return bookDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Lister tous les livres", description = "Récupère la liste de tous les livres du catalogue")
    @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès")
    public ResponseEntity<List<BookDto>> fetchBooks() {
        List<BookDto> books = catalogBookService.fetchBooks();
        return ResponseEntity.ok(books);
    }
}
