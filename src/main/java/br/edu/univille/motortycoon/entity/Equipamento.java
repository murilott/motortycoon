package br.edu.univille.motortycoon.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @DecimalMin(value = "0.01", message = "Custo mpinimo 0.01")
    @NotBlank(message = "Custo não deve ficar em branco")
    private float custo;
    @Min(value = 1, message = "Estoque mínimo 1")
    @NotBlank(message = "Estoque não deve ficar em branco")
    private int estoque;
    @ManyToOne(cascade = CascadeType.MERGE) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
    private String imagem;
}
