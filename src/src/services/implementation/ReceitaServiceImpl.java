package services.implementation;

import models.entities.Receita;
import repository.interfaces.ReceitaRepository;
import services.interfaces.ReceitaService;

import java.util.List;

public class ReceitaServiceImpl implements ReceitaService {

    private final ReceitaRepository repository;

    public ReceitaServiceImpl(ReceitaRepository repository) { this.repository = repository; }

    @Override
    public void cadastrar(Receita receita) { repository.salvar(receita); }

    @Override
    public Receita buscarPorId(int id) { return repository.buscarPorId(id); }

    @Override
    public List<Receita> listar() { return repository.listar(); }

    @Override
    public int gerarNovoId() { return listar().size() + 1; }
}
