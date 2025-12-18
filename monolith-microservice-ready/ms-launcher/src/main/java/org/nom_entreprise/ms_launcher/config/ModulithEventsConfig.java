package org.nom_entreprise.ms_launcher.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Configuration pour Spring Modulith Events.
 * Fournit un TaskScheduler nécessaire au traitement asynchrone des événements
 * enregistrés dans la base de données.
 */
@Slf4j
@Configuration
public class ModulithEventsConfig {

    @Bean
    public TaskScheduler taskScheduler() {
        log.info("[CONFIG] Création du TaskScheduler pour Spring Modulith Events");
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("modulith-events-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(60);
        scheduler.initialize();
        log.info("[CONFIG] TaskScheduler créé avec succès - poolSize: 5");
        return scheduler;
    }
}
