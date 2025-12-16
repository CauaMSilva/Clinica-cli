package services.implementation;

import controllers.ConsultaController;
import models.entities.Consulta;
import repository.interfaces.ConsultaRepository;
import repository.interfaces.MedicoRepository;
import services.interfaces.ConsultaService;
import services.interfaces.PacienteService;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaServiceImpl implements ConsultaService {

    private final ConsultaRepository repository;
    private final PacienteService pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaServiceImpl(ConsultaRepository repository,PacienteService pacienteRepository, MedicoRepository medicoRepository) {
        this.repository = repository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Consulta agendar(int idPaciente, int idMedico, String data){
        if (pacienteRepository.buscarPorId(idPaciente) == null){
            throw new IllegalArgumentException("Erro: Paciente ID " + idPaciente + "não encontrado.");
        }
        if (medicoRepository.buscarPorId(idMedico) == null){
            throw new IllegalArgumentException("Erro: Medico ID " + idMedico + " não encontrado.");
        }

        try{
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate dataDigitada = java.time.LocalDate.parse(data, fmt);

            if (dataDigitada.isBefore(java.time.LocalDate.now())){
                throw new IllegalArgumentException("Não é possivel agendar para uma data passada.");
            }
        } catch (java.time.format.DateTimeParseException e){
            throw new IllegalArgumentException("Formatode data Inválido. Use dd/mm/aaaa.");
        }


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

        return consulta;
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
