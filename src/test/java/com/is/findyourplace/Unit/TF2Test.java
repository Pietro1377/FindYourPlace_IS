package com.is.findyourplace.Unit;

import com.is.findyourplace.FindYourPlaceApplication;
import com.is.findyourplace.persistence.dto.NotificaDto;
import com.is.findyourplace.service.gestioneAmministratori.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class TF2Test {

        @Autowired
        private MockMvc mockMvc;
        @MockBean
        private NotificationService notificationService;

        @Test
        public void testSendNot() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setDestinatario("ADMIN_ALE");
            notificaDto.setTesto("testtesto");



            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(201));
        }
        @Test
        public void testSendNotAutNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setDestinatario("ADMIN_ALE");
            notificaDto.setTesto("testtesto");


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


            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
        @Test
        public void testSendNotTextNull() throws Exception {
            NotificaDto notificaDto = new NotificaDto();
            notificaDto.setAutore("testautore");
            notificaDto.setDestinatario("ADMIN_ALE");


            mockMvc.perform(MockMvcRequestBuilders.post("/sendNotification")
                            .with(SecurityMockMvcRequestPostProcessors.csrf())
                            .flashAttr("notifica", notificaDto))
                    .andExpect(MockMvcResultMatchers.status().is(400));
        }
}