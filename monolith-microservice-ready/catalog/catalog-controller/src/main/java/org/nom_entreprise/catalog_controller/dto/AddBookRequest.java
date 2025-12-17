package org.nom_entreprise.catalog_controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record AddBookRequest(
        @NotBlank(message = "Le titre est requis")
        @JsonProperty("title")
        String title,
        
        @NotBlank(message = "Le numéro de catalogue est requis")
        @JsonProperty("catalogNumber")
        String catalogNumber,
        
        @NotBlank(message = "L'ISBN est requis")
        @JsonProperty("isbn")
        String isbn,
        
        @NotBlank(message = "Le nom de l'auteur est requis")
        @JsonProperty("authorName")
        String authorName
) {
}
