package services.interfaces;

import models.entities.Paciente;
import java.util.List;

public interface PacienteService {

    void cadastrar(Paciente paciente);

    Paciente buscarPorId(int id);

    Paciente buscarPorNome(String nome);

    List<Paciente> listar();

    Paciente login(String nome);

    void deletar(int id);

    void atualizar(Paciente paciente);

    int gerarNovoId();
}
