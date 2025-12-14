package services.implementation;

import controllers.ConsultaController;
import models.entities.Consulta;
import repository.interfaces.ConsultaRepository;
import services.interfaces.ConsultaService;

import java.util.List;

public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository repository;

    public ConsultaServiceImpl(ConsultaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void agendar(int idPaciente, int idMedico, String data) {

        long consultasNoDia = repository.listar().stream()
                .filter(c ->
                        c.getIdMedico() == idMedico &&
                                c.getData().equals(data) &&
                                c.getStatus().equals("AGENDADA")
                )
                .count();

        String status = (consultasNoDia < 3) ? "AGENDADA" : "ESPERA";

        Consulta consulta = new Consulta(
                gerarNovoId(),
                idPaciente,
                idMedico,
                data,
                status
        );

        repository.salvar(consulta);
    }

    @Override
    public void cancelar(int idConsulta) {
        Consulta c = repository.buscarPorId(idConsulta);
        if (c == null)
            throw new IllegalArgumentException("Consulta não encontrada");

        c.setStatus("CANCELADA");
        repository.atualizar(c);

        promoverFilaEspera(c.getIdMedico(), c.getData());
    }

    public void promoverFilaEspera(int idMedico, String data) {
        for (Consulta c : repository.listar()) {
            if (c.getIdMedico() == idMedico &&
                    c.getData().equals(data) &&
                    c.getStatus().equals("ESPERA")) {

                c.setStatus("AGENDADA");
                repository.atualizar(c);
                break;
            }
        }
    }

    @Override
    public void realizar(int idConsulta) {
        Consulta c = repository.buscarPorId(idConsulta);
        if (c == null)
            throw new IllegalArgumentException("Consulta não encontrada");

        c.setStatus("REALIZADA");
        repository.atualizar(c);
    }

    @Override
    public List<Consulta> listarPorPaciente(int idPaciente) {
        return repository.listar().stream()
                .filter(c -> c.getIdPaciente() == idPaciente)
                .toList();
    }

    @Override
    public List<Consulta> listarPorMedico(int idMedico) {
        return repository.listar().stream()
                .filter(c -> c.getIdMedico() == idMedico)
                .toList();
    }

    @Override
    public int gerarNovoId() { return repository.gerarNovoId(); }

    public Consulta buscarPorId(int idConsulta){ return repository.buscarPorId(idConsulta); }
}
