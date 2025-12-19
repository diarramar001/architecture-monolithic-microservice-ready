package org.nom_entreprise.private_rest_client.catalog;

/**
 * Exception levée lorsqu'une erreur survient lors de l'appel au client du catalogue.
 */
public class CatalogClientException extends RuntimeException {
    
    public CatalogClientException(String message) {
        super(message);
    }
    
    public CatalogClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
