package br.edu.univille.motortycoon.entity;

public enum Cargo {
    ADMIN("admin"),
    USER("user");

    private String cargo;

    Cargo(String cargo) {
        this.cargo = cargo;
    }

    public String getNome() {
        return cargo;
    }
}
