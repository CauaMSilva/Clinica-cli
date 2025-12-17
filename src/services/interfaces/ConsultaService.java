package services.interfaces;

import models.entities.Consulta;
import models.entities.Paciente;

import java.util.List;

public interface ConsultaService {

    void agendar(int idPaciente, int idMedico, String data, String comentario);

    void cancelar(int idConsulta);

    void realizar(int idConsulta);

    List<Consulta> listarPorPaciente(int idPaciente);

    List<Consulta> listarPorMedico(int idMedico);

    void promoverFilaEspera(int idMedico, String data);

    int gerarNovoId();

    Consulta buscarPorId(int idConsulta);

    void atualizar(Consulta consulta);

    Paciente buscarPacientePorId(int idPaciente);

}
