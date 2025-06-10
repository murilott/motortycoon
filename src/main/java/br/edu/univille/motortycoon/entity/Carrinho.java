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
    @OneToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    private float custoTotal;
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ItemCarrinho> itens;

    @Override
public String toString() {
    return "Carrinho [id=" + id 
        + ", usuarioId=" + (usuario != null ? usuario.getId() : "null")
        + ", custoTotal=" + custoTotal 
        + ", itensCount=" + (itens != null ? itens.size() : 0) 
        + "]";
}

    
}
