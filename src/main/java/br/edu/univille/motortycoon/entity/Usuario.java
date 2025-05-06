package br.edu.univille.motortycoon.entity;

import java.time.LocalDateTime;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private LocalDateTime dataNascimento;
    private String endereco;
    private int cpf;
    private int permissao;
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Pagamento> formaPagamento;
    @ManyToOne(cascade = CascadeType.MERGE) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinhoAtual_id")
    private ItemCarrinho carrinhoAtual;
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ItemCarrinho> historico;
}
