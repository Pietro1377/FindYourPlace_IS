package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.NotificaRicevuta;
import com.is.findyourplace.persistence.entity.CompositeKeys.NotificaRicevutaKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificaRicevutaRepository extends JpaRepository<NotificaRicevuta, NotificaRicevutaKey> {
    /**
     * Query custom per recuperare la lista delle notifiche ricevute
     * di un determinato utente tramite l'id.
     * @param id_utente Id dell' Utente
     * @return Lista di notifiche ricevute di quell' Utente
     */
    @Query("SELECT n FROM NotificaRicevuta n WHERE n.idNotificaRicevuta.idUtente=?1")
    List<NotificaRicevuta> findByIdUtente(Long id_utente);
}
