package com.example.agenda.controller;

import com.example.agenda.model.Contato;
import com.example.agenda.repository.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {

    @Autowired
    private ContatoRepository contatoRepository;

    @GetMapping
    public List<Contato> listarTodos() {
        return contatoRepository.findAll();
    }

    @PostMapping
    public Contato salvar(@RequestBody Contato contato) {
        return contatoRepository.save(contato);
    }

    @GetMapping("/{id}")
    public Contato buscarPorId(@PathVariable Long id) {
        return contatoRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Contato atualizar(@PathVariable Long id, @RequestBody Contato contatoAtualizado) {
        return contatoRepository.findById(id).map(contato -> {
            contato.setNome(contatoAtualizado.getNome());
            contato.setTelefone(contatoAtualizado.getTelefone());
            return contatoRepository.save(contato);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        contatoRepository.deleteById(id);
    }
}
