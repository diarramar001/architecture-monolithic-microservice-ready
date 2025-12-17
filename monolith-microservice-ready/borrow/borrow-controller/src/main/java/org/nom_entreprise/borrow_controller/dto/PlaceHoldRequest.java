package org.nom_entreprise.borrow_controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PlaceHoldRequest(
        @NotBlank(message = "Le numéro d'inventaire est requis")
        @JsonProperty("inventoryNumber")
        String inventoryNumber,
        
        @NotNull(message = "La date de réservation est requise")
        @JsonProperty("dateOfHold")
        LocalDate dateOfHold
) {
}
