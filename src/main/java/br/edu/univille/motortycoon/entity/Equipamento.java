package br.edu.univille.motortycoon.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome não deve ficar em branco")
    private String nome;
    @DecimalMin(value = "0.01", message = "Custo mínimo 0.01")
    @NotNull(message = "Custo não deve ficar em branco")
    private float custo;
    // @Min(value = 1, message = "Estoque mínimo 1")
    // @NotNull(message = "Estoque não deve ficar em branco")
    private int estoque;
    @ManyToOne(cascade = CascadeType.MERGE) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private String imagem;
}
