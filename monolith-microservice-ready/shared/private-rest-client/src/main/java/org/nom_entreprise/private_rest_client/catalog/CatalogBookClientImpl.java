package org.nom_entreprise.private_rest_client.catalog;

import lombok.extern.slf4j.Slf4j;
import org.nom_entreprise.private_rest_client.catalog.dto.BookResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implémentation du client REST pour accéder aux services du catalogue de livres.
 * Utilise RestTemplate pour effectuer les appels HTTP vers l'API du catalogue.
 * Le client appelle uniquement l'URL de l'API sans dépendance directe vers catalog-domain.
 */
@Slf4j
@Component
public class CatalogBookClientImpl implements CatalogBookClient {

    private final RestTemplate restTemplate;
    private final String catalogBaseUrl;

    public CatalogBookClientImpl(RestTemplate restTemplate,
                                 @Value("${catalog.api.base-url:http://localhost:7087/monolithic-app}") String catalogBaseUrl) {
        this.restTemplate = restTemplate;
        this.catalogBaseUrl = catalogBaseUrl;
    }

    @Override
    public List<BookResponse> fetchAllBooks() {
        try {
            log.debug("[CATALOG-CLIENT] Récupération de la liste de tous les livres depuis {}", catalogBaseUrl);
            
            String url = catalogBaseUrl + "/api/books";
            ParameterizedTypeReference<List<BookResponse>> responseType = new ParameterizedTypeReference<>() {};
            ResponseEntity<List<BookResponse>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    responseType
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("[CATALOG-CLIENT] {} livre(s) récupéré(s) avec succès", response.getBody().size());
                return response.getBody();
            } else {
                log.warn("[CATALOG-CLIENT] Aucun livre trouvé ou réponse vide");
                return Collections.emptyList();
            }
        } catch (RestClientException e) {
            log.error("[CATALOG-CLIENT] Erreur lors de la récupération de la liste des livres", e);
            throw new CatalogClientException("Erreur lors de la récupération de la liste des livres", e);
        }
    }

    @Override
    public Optional<BookResponse> findBookById(Long id) {
        try {
            log.debug("[CATALOG-CLIENT] Récupération du livre avec l'ID {} depuis {}", id, catalogBaseUrl);
            
            String url = catalogBaseUrl + "/api/books/" + id;
            ResponseEntity<BookResponse> response = restTemplate.getForEntity(url, BookResponse.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                log.info("[CATALOG-CLIENT] Livre avec l'ID {} récupéré avec succès", id);
                return Optional.of(response.getBody());
            } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.debug("[CATALOG-CLIENT] Livre avec l'ID {} non trouvé", id);
                return Optional.empty();
            } else {
                log.warn("[CATALOG-CLIENT] Réponse inattendue pour le livre avec l'ID {}: {}", id, response.getStatusCode());
                return Optional.empty();
            }
        } catch (RestClientException e) {
            log.error("[CATALOG-CLIENT] Erreur lors de la récupération du livre avec l'ID {}", id, e);
            throw new CatalogClientException("Erreur lors de la récupération du livre avec l'ID " + id, e);
        }
    }
}
