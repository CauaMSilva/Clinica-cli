package models.entities;

public class Avaliacao {

    private int id;
    private int idConsulta;
    private int idMedico;
    private int nota;
    private String comentario;

    public Avaliacao(int id, int idConsulta, int idMedico, int nota, String comentario) {
        this.id = id;
        this.idConsulta = idConsulta;
        this.idMedico = idMedico;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getId() { return id; }
    public int getIdConsulta() { return idConsulta; }
    public int getIdMedico() { return idMedico; }
    public int getNota() { return nota; }
    public String getComentario() { return comentario; }

}
