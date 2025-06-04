package br.edu.univille.motortycoon.entity;

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
public class ItemCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinho_id")
    private Carrinho carrinho;
    private double custo;
    private int quantidade;
    @ManyToOne(cascade = CascadeType.REFRESH) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Equipamento produto;
}
