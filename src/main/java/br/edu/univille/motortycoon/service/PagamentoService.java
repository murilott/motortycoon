package br.edu.univille.motortycoon.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.expression.Strings;

import br.edu.univille.motortycoon.entity.Pagamento;
import br.edu.univille.motortycoon.repository.PagamentoRepository;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public class PagamentoService {
    @Autowired
    private PagamentoRepository repository;

    public Optional<Pagamento> obterPeloId(long id){
        return repository.findById(id);
    }

    public List<Pagamento> obterTodos(){
        return repository.findAll();
    }

    public void salvar(Pagamento Pagamento) {
        repository.save(Pagamento);
    }

    public void excluir(Pagamento Pagamento) {
        repository.delete(Pagamento);
    }
}
