package com.is.findyourplace.persistence.repository;

import com.is.findyourplace.persistence.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    Utente findByUsernameOrEmail(String username, String email);
}
