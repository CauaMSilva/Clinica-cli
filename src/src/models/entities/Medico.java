package models.entities;

import models.base.Pessoa;

public class Medico extends Pessoa {
    private String especialidade;
    private String planoAtendido;

    public Medico(int id, String nome, String especialidade, String planoAtendido) {
        super(id, nome);
        this.especialidade = especialidade;
        this.planoAtendido = planoAtendido;
    }

    public String getEspecialidade() { return especialidade; }

    public void setEspecialidade(String especialidade){ this.especialidade = especialidade; }

    public String getPlanoAtendido() {
        return planoAtendido;
    }

    public void setPlanoAtendido(String planoAtendido) { this.planoAtendido = planoAtendido; }

}
