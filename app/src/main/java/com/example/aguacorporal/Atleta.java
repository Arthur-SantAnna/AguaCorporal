package com.example.aguacorporal;

public class Atleta {
    String nome;
    int idade;
    double peso;

    public Atleta(double peso, int idade, String nome){
        this.idade = idade;
        this.peso = peso;
        this.nome = nome;
    }
    @Override
    public String toString() {
        return (nome + "  -  " + idade + " Anos " + "  -  " + peso + " Kg");
    }
}
