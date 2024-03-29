package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.RicercaDto;
import com.is.findyourplace.persistence.entity.Ricerca;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.RicercaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {
    /**
     * Repository della Ricerca.
     */
    private final RicercaRepository ricercaRepository;

    /**
     * Construttore del service.
     * @param ricercaRepository ricercaRepository
     */
    public HistoryServiceImpl(final RicercaRepository ricercaRepository) {
        this.ricercaRepository = ricercaRepository;
    }


    @Override
    public List<RicercaDto> findRicercheDtoByIdUtente(final Long idUtente) {
        List<Ricerca> ricerche = ricercaRepository.findByIdUtente(idUtente);
        return ricerche.stream()
                .map(this::mapToRicercaDto)
                .collect(Collectors.toList());
    }

    @Override
    public Ricerca findRicerca(final Long idRicerca, final Long idUtente) {
        return ricercaRepository.findByIdRicercaAndIdUtente(
                idRicerca, idUtente
        );
    }

    @Override
    @Transactional
    public void removeIdUtente(final Ricerca ricerca) {
        Utente utente = ricerca.getUtente();
        utente.getRicerche().remove(ricerca);
        ricerca.setUtente(null);
        ricerca.setIdUtente(null);

        ricercaRepository.save(ricerca);
    }

    private RicercaDto mapToRicercaDto(final Ricerca ricerca) {
        RicercaDto ricercaDto = new RicercaDto();

        ricercaDto.setIdRicerca(ricerca.getIdRicerca());
        ricercaDto.setDataRicerca(ricerca.getDataRicerca());
        ricercaDto.setLatitude((float) ricerca.getCoordinate().getX());
        ricercaDto.setLongitude((float) ricerca.getCoordinate().getY());
        ricercaDto.setRaggio(ricerca.getRaggio());

        return ricercaDto;
    }
}
