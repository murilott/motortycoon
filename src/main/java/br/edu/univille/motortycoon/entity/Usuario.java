package br.edu.univille.motortycoon.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Este campo deve conter uma data no passado")
    private LocalDate dataNascimento;
    private String endereco;
    private String senha;
    private int cpf;
    // @Enumerated(EnumType.STRING)
    // private Cargo cargo;
    private String cargo;
    @ManyToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER)
    private List<Pagamento> formaPagamento;
    @OneToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST }) // (fetch = FetchType.EAGER) //(cascade = CascadeType.ALL)
    @JoinColumn(name = "carrinhoAtual_id")
    private Carrinho carrinhoAtual;
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ItemCarrinho> historico;

    
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    @Override
public String toString() {
    return "Usuario [id=" + id 
           + ", nomeCompleto=" + nomeCompleto 
           + ", email=" + email 
           + ", dataNascimento=" + dataNascimento 
           + ", endereco=" + endereco 
           + ", cpf=" + cpf 
           + ", cargo=" + cargo
           + ", formaPagamentoCount=" + (formaPagamento != null ? formaPagamento.size() : 0)
           + ", carrinhoAtualId=" + (carrinhoAtual != null ? carrinhoAtual.getId() : "null")
           + ", historicoCount=" + (historico != null ? historico.size() : 0)
           + "]";
}
}
