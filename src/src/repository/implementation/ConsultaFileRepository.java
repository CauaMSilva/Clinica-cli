package repository.implementation;

import models.entities.Consulta;
import models.entities.Medico;
import repository.interfaces.ConsultaRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaFileRepository implements ConsultaRepository {

    private final String caminho = "data/consultas.txt";

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
    public void salvar(Consulta c) {
        try (FileWriter fw = new FileWriter(caminho, true)) {
            fw.write(toLinha(c) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar consulta");
        }
    }

    public int gerarNovoId(){
        garantirArquivoExiste();
        List<Consulta> lista = listar();
        if (lista.isEmpty()) return 1;

        return lista.get(lista.size()-1).getId() + 1;
    }

    @Override
    public List<Consulta> listar() {
        List<Consulta> consultas = new ArrayList<>();

        File file = new File(caminho);
        if (!file.exists()) return consultas;

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                consultas.add(fromLinha(linha));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler consultas");
        }

        return consultas;
    }

    @Override
    public Consulta buscarPorId(int id) {
        for (Consulta c : listar()) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    @Override
    public void atualizar(Consulta consulta) {
        List<Consulta> consultas = listar();

        try (PrintWriter pw = new PrintWriter(new FileWriter(caminho))) {
            for (Consulta c : consultas) {
                if (c.getId() == consulta.getId()) {
                    pw.println(toLinha(consulta));
                } else {
                    pw.println(toLinha(c));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao atualizar consulta");
        }
    }

    private String toLinha(Consulta c) {
        return c.getId() + ";" +
                c.getIdPaciente() + ";" +
                c.getIdMedico() + ";" +
                c.getData() + ";" +
                c.getStatus();
    }

    private Consulta fromLinha(String linha) {
        String[] p = linha.split(";");
        return new Consulta(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                p[3],
                p[4]
        );
    }
}
