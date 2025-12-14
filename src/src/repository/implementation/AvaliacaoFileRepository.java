package repository.implementation;

import models.entities.Avaliacao;
import models.entities.Consulta;
import repository.interfaces.AvaliacaoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoFileRepository implements AvaliacaoRepository {

    private final String caminho = "data/avaliacoes.txt";

    private void garantirArquivoExiste() {
        try {
            File arquivo = new File(caminho);
            File pasta = arquivo.getParentFile();

            if (pasta != null && !pasta.exists()) {
                pasta.mkdirs();
            }

            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao criar arquivo de pacientes");
        }
    }

    @Override
    public void salvar(Avaliacao a) {
        garantirArquivoExiste();
        try (FileWriter fw = new FileWriter(caminho, true)) {
            fw.write(toLinha(a) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar avaliação");
        }
    }

    public int gerarNovoId(){
        garantirArquivoExiste();
        List<Avaliacao> lista = listar();
        if (lista.isEmpty()) return 1;

        return lista.get(lista.size()-1).getId() + 1;
    }

    @Override
    public List<Avaliacao> listar() {
        garantirArquivoExiste();
        List<Avaliacao> avaliacoes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                avaliacoes.add(fromLinha(linha));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler avaliações");
        }

        return avaliacoes;
    }

    @Override
    public List<Avaliacao> listarPorMedico(int id) {
        garantirArquivoExiste();
        List<Avaliacao> listaMedico = new ArrayList<>();
        for (Avaliacao a : listar()) {
            if (a.getIdMedico() == id){
                listaMedico.add(a);
            }
        }
        return listaMedico;
    }

    public double mediaEstrelas(int idMedico) {
        garantirArquivoExiste();
        List<Avaliacao> avalsMedico = listar().stream()
                .filter(a -> a.getIdMedico() == idMedico)
                .toList();

        if (avalsMedico.isEmpty()) return 0;

        return avalsMedico.stream()
                .mapToInt(Avaliacao::getNota)  // usando nota como estrelas
                .average()
                .orElse(0);
    }

    private String toLinha(Avaliacao a) {
        return a.getId() + ";" +
                a.getIdConsulta() + ";" +
                a.getIdMedico() + ";" +
                a.getNota() + ";" +
                a.getComentario();
    }

    private Avaliacao fromLinha(String linha) {
        String[] p = linha.split(";");

        return new Avaliacao(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                Integer.parseInt(p[3]),
                p[4]
        );
    }
}
