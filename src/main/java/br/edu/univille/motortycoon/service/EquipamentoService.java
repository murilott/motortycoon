package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.motortycoon.entity.Equipamento;
import br.edu.univille.motortycoon.repository.EquipamentoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository repository;

    public Optional<Equipamento> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Equipamento> obterTodos(){
        return repository.findAll();
    }

    public Equipamento salvar(Equipamento Equipamento) {
        return repository.save(Equipamento);
    }

    public void excluir(Equipamento Equipamento) {
        repository.delete(Equipamento);
    }
}
