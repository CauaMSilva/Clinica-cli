package services.implementation;

import models.entities.Medico;
import repository.interfaces.MedicoRepository;
import services.interfaces.MedicoService;

import java.util.List;
import static java.lang.Integer.parseInt;

public class MedicoServiceImpl implements MedicoService {

    private final MedicoRepository medicoRepository;

    public MedicoServiceImpl(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public void cadastrar(Medico medico) {
        if (medico.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }

        medicoRepository.salvar(medico);
    }

    @Override
    public Medico buscarPorId(int id) { return medicoRepository.buscarPorId(id); }

    public Medico buscarPorNome(String nome) { return medicoRepository.buscarPorNome(nome); }

    public List<Medico> listarPorPlano(String plano) { return medicoRepository.listarPorPlano(plano); }

    @Override
    public List<Medico> listar() {
        return medicoRepository.listar();
    }

    @Override
    public Medico login(String nome) { return medicoRepository.buscarPorNome(nome); }

    @Override
    public void deletar(int id) { medicoRepository.deletar(id); }

    @Override
    public void atualizar(Medico medico) { medicoRepository.atualizar(medico); }

    public int gerarNovoId(){ return medicoRepository.gerarNovoId(); }
}
