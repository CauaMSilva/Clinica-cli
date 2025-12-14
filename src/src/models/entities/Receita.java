package models.entities;

public class Receita {

    private int id;
    private int idPaciente;
    private int idMedico;
    private String descricao;
    private String data;

    public Receita(
            int id,
            int idPaciente,
            int idMedico,
            String descricao,
            String data
    ) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.descricao = descricao;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }
}
