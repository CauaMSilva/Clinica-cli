package models.entities;

import models.base.Pessoa;

public class Paciente extends Pessoa {
    private String planoSaude;
    int idade;

    public Paciente(int id, String nome, int idade, String planoDeSaude) {
        super(id, nome);
        this.planoSaude = planoDeSaude;
        this.idade = idade;
    }

    public String getPlanoSaude(){
        return planoSaude;
    }

    public int getIdade(){
        return idade;
    }

}
