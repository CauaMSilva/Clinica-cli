package services.implementation;

import models.entities.Avaliacao;
import models.entities.Consulta;
import models.entities.Medico;
import repository.interfaces.AvaliacaoRepository;
import repository.interfaces.ConsultaRepository;
import services.interfaces.AvaliacaoService;

import java.util.List;

import static java.lang.Integer.parseInt;

public class AvaliacaoServiceImpl implements AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepo;
    private final ConsultaRepository consultaRepo;

    public AvaliacaoServiceImpl(AvaliacaoRepository aRepo, ConsultaRepository cRepo) {
        this.avaliacaoRepo = aRepo;
        this.consultaRepo = cRepo;
    }

    private void validarConsulta(int idConsulta, int estrelas) {
        Consulta c = consultaRepo.buscarPorId(idConsulta);

        if (c == null)
            throw new IllegalArgumentException("Consulta não encontrada");

        if (!c.getStatus().equals("REALIZADA"))
            throw new IllegalStateException("Consulta ainda não foi realizada");

        if (estrelas < 1 || estrelas > 5)
            throw new IllegalArgumentException("Nota deve ser entre 1 e 5");
    }

    @Override
    public void avaliar(int id, int idConsulta, int idMedico, int estrelas, String comentario) {
        validarConsulta(idConsulta, estrelas);

        int novoId = avaliacaoRepo.listar().size() + 1;

        Avaliacao a = new Avaliacao(
                novoId,
                idConsulta,
                idMedico,
                estrelas,
                comentario
        );

        avaliacaoRepo.salvar(a);
    }
    public int gerarNovoId(){
        return avaliacaoRepo.gerarNovoId();
    }

    @Override
    public double mediaEstrelas(int idMedico) {
        return avaliacaoRepo.mediaEstrelas(idMedico);
    }
}
