package org.nom_entreprise.ms_launcher.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.IncompleteEventPublications;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * Composant responsable de la republication périodique des événements
 * en attente dans Spring Modulith Events JPA.
 * 
 * Les événements sont enregistrés dans la base de données pendant la transaction
 * et doivent être republiés pour être traités par les listeners.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EventRepublisher {

    private final IncompleteEventPublications incompleteEventPublications;

    /**
     * Republie les événements en attente toutes les 5 secondes.
     * Les événements qui sont en attente depuis plus de 1 seconde seront republiés.
     */
    @Scheduled(fixedRate = 5000) // Exécution toutes les 5 secondes
    @Transactional
    public void republishOutstandingEvents() {
        try {
            int count = incompleteEventPublications.resubmitIncompletePublicationsOlderThan(Duration.ofSeconds(1));
            if (count > 0) {
                log.debug("[EVENT-REPUBLISHER] {} événement(s) en attente republié(s)", count);
            }
        } catch (Exception e) {
            log.error("[EVENT-REPUBLISHER] Erreur lors de la republication des événements en attente", e);
        }
    }
}
