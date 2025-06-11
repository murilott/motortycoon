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
    @Min(value = 1, message = "Estoque mínimo 1")
    @NotBlank(message = "Quantidade não deve ficar em branco")
    private int quantidade;
    @ManyToOne(cascade = CascadeType.REFRESH) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Equipamento produto;

    public float calcularCusto() {
        System.out.println("item " + this.produto);
        System.out.println("Custo " + this.produto.getCusto());
        System.out.println("QUANTIDADE: " + this.quantidade);
        return this.produto.getCusto() * this.quantidade;
    }
}
