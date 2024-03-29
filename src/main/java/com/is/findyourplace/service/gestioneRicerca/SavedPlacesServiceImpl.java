package com.is.findyourplace.service.gestioneRicerca;

import com.is.findyourplace.persistence.dto.LuogoPreferitoDto;
import com.is.findyourplace.persistence.entity.CompositeKeys.PreferitiKey;
import com.is.findyourplace.persistence.entity.Luogo;
import com.is.findyourplace.persistence.entity.Preferiti;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.LuogoRepository;
import com.is.findyourplace.persistence.repository.PreferitiRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedPlacesServiceImpl implements SavedPlacesService {
    /**
     * Repository dei Preferiti.
     */
    private final PreferitiRepository preferitiRepository;
    /**
     * Repository di Luogo.
     */
    private final LuogoRepository luogoRepository;

    /**
     * Costruttore del service.
     * @param preferitiRepository preferitiRepository
     * @param luogoRepository luogoRepository
     */
    public SavedPlacesServiceImpl(
            final PreferitiRepository preferitiRepository,
            final LuogoRepository luogoRepository) {
        this.preferitiRepository = preferitiRepository;
        this.luogoRepository = luogoRepository;
    }

    @Override
    @Transactional
    public void savePreferito(final Utente utente, final Luogo luogo) {
        Preferiti luogoSalvato = new Preferiti(utente, luogo);
        utente.getPreferiti().add(luogoSalvato);
        luogo.getPreferiti().add(luogoSalvato);
    }

    @Override
    public List<LuogoPreferitoDto>
    findLuoghiPreferitiDtoByIdUtente(final Long idUtente) {
        List<LuogoPreferitoDto> luoghiPreferitiDto = new ArrayList<>();
        for (Preferiti preferito
                : preferitiRepository.findByIdUtente(idUtente)) {
            Luogo luogo = luogoRepository.findByIdLuogo(
                    preferito.getIdPreferiti().getIdLuogo());
            luoghiPreferitiDto.add(mapToLuogoPreferitoDto(luogo, preferito));
        }
        return luoghiPreferitiDto;
    }

    @Override
    public Preferiti findPreferito(final Long idUtente, final Long idLuogo) {
        return preferitiRepository.findByIdPreferiti(
                new PreferitiKey(idUtente, idLuogo)
        );
    }

    @Override
    public void deletePreferito(final Preferiti luogoSalvato) {
        preferitiRepository.delete(luogoSalvato);
    }

    @Override
    public void updateNotPreferito(final Preferiti preferito,
                                   final boolean notifiche) {
        preferito.setNotifiche(notifiche);
        preferitiRepository.save(preferito);
    }

    @Override
    public Luogo findLuogoById(final Long idLuogo) {
        return luogoRepository.findByIdLuogo(idLuogo);
    }

    private LuogoPreferitoDto mapToLuogoPreferitoDto(
            final Luogo luogo, final Preferiti preferiti) {
        LuogoPreferitoDto luogoPreferitoDto = new LuogoPreferitoDto();

        luogoPreferitoDto.setIdLuogo(preferiti.getIdPreferiti().getIdLuogo());
        luogoPreferitoDto.setIdUtente(preferiti.getIdPreferiti().getIdUtente());
        luogoPreferitoDto.setNome(luogo.getNome());
        luogoPreferitoDto.setLatitude((float) luogo.getCoordinate().getX());
        luogoPreferitoDto.setLongitude((float) luogo.getCoordinate().getY());
        luogoPreferitoDto.setQualityIndexLuogo(luogo.getQualityIndex());
        luogoPreferitoDto.setQualityIndexFound(preferiti.getQualityIndex());
        luogoPreferitoDto.setNotifiche(preferiti.isNotifiche());

        return luogoPreferitoDto;
    }
}
