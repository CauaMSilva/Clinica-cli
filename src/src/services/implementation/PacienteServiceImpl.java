package services.implementation;

import models.entities.Paciente;
import repository.implementation.PacienteFileRepository;
import repository.interfaces.PacienteRepository;
import services.interfaces.PacienteService;

import java.util.List;
import static java.lang.Integer.parseInt;

public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteServiceImpl(PacienteRepository pacRepo) { this.pacienteRepository = pacRepo; }

    @Override
    public void cadastrar(Paciente paciente) {
        if (paciente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        pacienteRepository.salvar(paciente);
    }

    @Override
    public Paciente buscarPorId(int id) { return pacienteRepository.buscarPorId(id); }

    @Override
    public List<Paciente> listar() { return pacienteRepository.listar(); }

    public Paciente login(String nome) { return pacienteRepository.buscarPorNome(nome); }

    @Override
    public void deletar(int id) { pacienteRepository.deletar(id); }

    public void atualizar(Paciente paciente){ pacienteRepository.atualizar(paciente); }

    public int gerarNovoId(){ return pacienteRepository.gerarNovoId(); }

    public Paciente buscarPorNome(String nome) { return pacienteRepository.buscarPorNome(nome); }
}
