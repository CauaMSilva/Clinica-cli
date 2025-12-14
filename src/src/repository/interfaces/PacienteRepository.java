package repository.interfaces;

import models.entities.Paciente;
import java.util.List;

public interface PacienteRepository {
    void salvar(Paciente paciente);

    List<Paciente> listar();

    Paciente buscarPorId(int id);

    Paciente buscarPorNome(String nome);

    void deletar(int id);

    void atualizar(Paciente paciente);

    int gerarNovoId();
}
