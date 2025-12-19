package org.nom_entreprise.private_rest_client.catalog;

import org.nom_entreprise.private_rest_client.catalog.dto.BookResponse;

import java.util.List;
import java.util.Optional;

/**
 * Interface du client REST pour accéder aux services du catalogue de livres.
 * Permet aux autres modules d'accéder à la liste des livres du catalogue via l'API REST.
 * Le client appelle uniquement l'URL de l'API sans dépendance directe vers catalog-domain.
 */
public interface CatalogBookClient {
    
    /**
     * Récupère la liste de tous les livres du catalogue.
     * 
     * @return la liste de tous les livres du catalogue
     */
    List<BookResponse> fetchAllBooks();
    
    /**
     * Récupère un livre par son identifiant.
     * 
     * @param id l'identifiant du livre
     * @return un Optional contenant le livre s'il existe, vide sinon
     */
    Optional<BookResponse> findBookById(Long id);
}
