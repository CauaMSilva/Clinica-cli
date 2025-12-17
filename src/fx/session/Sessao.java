package fx.session;

import models.entities.Consulta;
import models.entities.Paciente;
import models.entities.Medico;

public class Sessao {

    private static Paciente pacienteLogado;
    private static Medico medicoLogado;
    private static Consulta consulta;

    public static void setPacienteLogado(Paciente paciente) {
        pacienteLogado = paciente;
    }

    public static Paciente getPacienteLogado() {
        return pacienteLogado;
    }

    public static void logoutPaciente() {
        pacienteLogado = null;
    }

    public static void setMedicoLogado(Medico medico) {
        medicoLogado = medico;
    }

    public static Medico getMedicoLogado() {
        return medicoLogado;
    }

    public static void logoutMedico() {
        medicoLogado = null;
    }

    public static void setConsultaSelecionada(Consulta selecionada) {
        consulta = selecionada;
    }

    public static Consulta getConsultaSelecionada() {
        return consulta;
    }
}
