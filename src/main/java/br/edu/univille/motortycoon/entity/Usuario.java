package br.edu.univille.capacete.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Usuario {
    private Long id;
    private String nomeCompleto;
    private LocalDateTime dataNascimento;
    private String endereco;
    private int cpf;
    private int permissao;
    private List<Pagamento> formaPagamento;
    private ItemCarrinho carrinhoAtual;
    private List<ItemCarrinho> historico;
}
