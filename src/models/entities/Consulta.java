package models.entities;

public class Consulta {

    private int id;
    private int idPaciente;
    private int idMedico;
    private String data;   // ex: 20/12/2025
    private String status; // AGENDADA | ESPERA | CANCELADA | REALIZADA
    private String comentarioMedico;

    public Consulta(int id, int idPaciente, int idMedico, String data, String status, String comentarioMedico) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
        this.data = data;
        this.status = status;
        this.comentarioMedico = "nenhum";
    }

    public int getId() { return id; }
    public int getIdPaciente() { return idPaciente; }
    public int getIdMedico() { return idMedico; }
    public String getData() { return data; }
    public String getStatus() { return status; }
    public String getComentarioMedico() { return comentarioMedico; }

    public void setStatus(String status) { this.status = status; }
    public void setComentarioMedico(String comentarioMedico) { this.comentarioMedico = comentarioMedico; }
}
