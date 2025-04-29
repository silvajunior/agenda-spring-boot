package com.example.agenda.controller;

import com.example.agenda.model.Contato;
import com.example.agenda.repository.ContatoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ContatoControllerTest {

    private MockMvc mockMvc;
    private ContatoRepository contatoRepository;
    private ContatoController contatoController;

    @BeforeEach
    public void setup() {
        contatoRepository = Mockito.mock(ContatoRepository.class);
        contatoController = new ContatoController();
        // injeção manual, porque @Autowired não funciona fora do Spring
        contatoController.getClass().getDeclaredFields()[0].setAccessible(true);
        try {
            contatoController.getClass().getDeclaredFields()[0].set(contatoController, contatoRepository);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mockMvc = MockMvcBuilders.standaloneSetup(contatoController).build();
    }

    @Test
    public void deveListarContatos() throws Exception {
        Contato contato = new Contato();
        contato.setNome("João");
        contato.setTelefone("123456789");
        when(contatoRepository.findAll()).thenReturn(Collections.singletonList(contato));

        mockMvc.perform(get("/contatos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }
}
