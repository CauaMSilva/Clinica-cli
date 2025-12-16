package services.interfaces;

import models.entities.Consulta;
import java.util.List;

public interface ConsultaService {

    Consulta agendar(int idPaciente, int idMedico, String data);

    void cancelar(int idConsulta);

    void realizar(int idConsulta);

    List<Consulta> listarPorPaciente(int idPaciente);

    List<Consulta> listarPorMedico(int idMedico);

    void promoverFilaEspera(int idMedico, String data);

    int gerarNovoId();

    Consulta buscarPorId(int idConsulta);
}
