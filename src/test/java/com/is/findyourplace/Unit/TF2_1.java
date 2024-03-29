package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.persistence.entity.Notifica;
import com.is.findyourplace.persistence.entity.Utente;
import com.is.findyourplace.persistence.repository.NotificaRepository;
import com.is.findyourplace.persistence.repository.UtenteRepository;
import com.is.findyourplace.service.gestioneUtenza.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FindYourPlaceApplication.class )
@AutoConfigureMockMvc()
public class TF2_1 {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private AccountService accountService;
        @MockBean
        private UtenteRepository utenteRepository;
        @MockBean
        private NotificaRepository notificaRepository;

        Utente utente;
        @BeforeEach
        public void setUp() {
            utente = new Utente();
            utente.setIdUtente(1L);
        }
        @Test
        public void testSendNot() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setDestinatario("testdestinatario");
            notificaDto.setTesto("testtesto");

            Mockito.when(accountService.existsByUsername("testdestinatario")).thenReturn(true);
            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(201));
        }
        @Test
        public void testSendNotAutNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setDestinatario("testdestinatario");
            notificaDto.setTesto("testtesto");

            Mockito.when(accountService.existsByUsername("testdestinatario")).thenReturn(true);
            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
        @Test
        public void testSendNotDestNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setTesto("testtesto");

            Mockito.when(accountService.existsByUsername("testdestinatario")).thenReturn(true);
            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
        @Test
        public void testSendNotTextNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setDestinatario("testdestinatario");

            Mockito.when(accountService.existsByUsername("testdestinatario")).thenReturn(true);
            Mockito.when(notificaRepository.save(Mockito.any(Notifica.class))).thenReturn(null);
            Mockito.when(utenteRepository.findByUsername(Mockito.any(String.class))).thenReturn(utente);
            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
}
