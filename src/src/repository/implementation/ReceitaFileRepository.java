package repository.implementation;

import models.entities.Receita;
import repository.interfaces.ReceitaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReceitaFileRepository implements ReceitaRepository {

    private final String caminho = "data/receitas.txt";

    @Override
    public void salvar(Receita r) {
        try (FileWriter fw = new FileWriter(caminho, true)) {
            fw.write(toLinha(r) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar receita");
        }
    }

    @Override
    public List<Receita> listar() {
        List<Receita> receitas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                receitas.add(fromLinha(linha));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler receitas");
        }

        return receitas;
    }

    @Override
    public Receita buscarPorId(int id) {
        for (Receita r : listar()) {
            if (r.getId() == id) return r;
        }
        return null;
    }

    private String toLinha(Receita r) {
        return r.getId() + ";" +
                r.getIdPaciente() + ";" +
                r.getIdMedico() + ";" +
                r.getDescricao() + ";" +
                r.getData();
    }

    private Receita fromLinha(String linha) {
        String[] p = linha.split(";");

        return new Receita(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                p[3],
                p[4]
        );
    }
}
