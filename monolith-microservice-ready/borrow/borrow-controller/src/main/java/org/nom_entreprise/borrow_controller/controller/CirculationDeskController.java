package org.nom_entreprise.borrow_controller.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.nom_entreprise.borrow_controller.dto.PlaceHoldRequest;
import org.nom_entreprise.borrow_domain.dto.HoldDto;
import org.nom_entreprise.borrow_domain.model.Book;
import org.nom_entreprise.borrow_domain.model.Hold;
import org.nom_entreprise.borrow_domain.port.in.CirculationDeskServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/holds")
@Tag(name = "Borrow - Circulation Desk", description = "API pour la gestion des réservations de livres")
public class CirculationDeskController {

    private final CirculationDeskServicePort circulationDeskService;

    public CirculationDeskController(CirculationDeskServicePort circulationDeskService) {
        this.circulationDeskService = circulationDeskService;
    }

    @PostMapping
    @Operation(summary = "Créer une réservation", description = "Place une nouvelle réservation pour un livre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Réservation créée avec succès"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    public ResponseEntity<HoldDto> placeHold(@Valid @RequestBody PlaceHoldRequest request) {
        try {
            var command = new Hold.PlaceHold(
                    new Book.Barcode(request.inventoryNumber()),
                    request.dateOfHold()
            );
            HoldDto holdDto = circulationDeskService.placeHold(command);
            return ResponseEntity.status(HttpStatus.CREATED).body(holdDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{holdId}")
    @Operation(summary = "Localiser une réservation", description = "Récupère les informations d'une réservation par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Réservation trouvée"),
            @ApiResponse(responseCode = "404", description = "Réservation non trouvée")
    })
    public ResponseEntity<HoldDto> locateHold(
            @Parameter(description = "ID de la réservation", required = true)
            @PathVariable UUID holdId) {
        Optional<HoldDto> holdDto = circulationDeskService.locate(holdId);
        return holdDto
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
