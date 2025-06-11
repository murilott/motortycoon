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
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private float custoTotal;
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<ItemCarrinho> itens;

    @Override
    public String toString() {
        return "Carrinho [id=" + id 
            + ", usuarioId=" + (usuario != null ? usuario.getId() : "nullN")
            + ", custoTotal=" + custoTotal 
            + ", itensCount=" + (itens != null ? itens.size() : 0) 
            + "]";
    }

    public float calcularCustoTotal() {
        float soma = 0;

        for (int i=0; i<this.itens.size(); i++) {
            ItemCarrinho item = this.itens.get(i);
            soma += item.getCusto();
        }

        return soma;
    }

    
}
