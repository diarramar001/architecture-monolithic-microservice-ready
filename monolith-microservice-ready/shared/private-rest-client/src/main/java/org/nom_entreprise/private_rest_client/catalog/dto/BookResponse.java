package org.nom_entreprise.private_rest_client.catalog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO représentant un livre retourné par l'API du catalogue.
 * Cette classe est indépendante du module catalog-domain.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookResponse {
    
    private Long id;
    private String title;
    
    @JsonProperty("catalogNumber")
    private CatalogNumber catalogNumber;
    
    private String isbn;
    
    @JsonProperty("author")
    private Author author;
    
    /**
     * Représente le numéro de catalogue (barcode)
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CatalogNumber {
        private String barcode;
    }
    
    /**
     * Représente l'auteur du livre
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        private String name;
    }
}
