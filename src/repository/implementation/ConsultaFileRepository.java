package repository.implementation;

import models.entities.Consulta;
import models.entities.Medico;
import models.entities.Paciente;
import repository.interfaces.ConsultaRepository;
import repository.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaFileRepository implements ConsultaRepository {

    private final String caminho = "data/consultas.txt";
    private PacienteRepository pacienteRepository = new PacienteFileRepository();

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

    private void salvarLista(List<Consulta> consultas) {
        try (FileWriter fw = new FileWriter(caminho, false)) {
            for (Consulta c: consultas) {
                fw.write(toLinha(c) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao reescrever arquivo de pacientes.");
        }
    }

    public int gerarNovoId(){
        garantirArquivoExiste();
        List<Consulta> lista = listar();
        if (lista.isEmpty()) return 1;

        return lista.get(lista.size()-1).getId() + 1;
    }

    public void atualizar(Consulta consulta) {
        List<Consulta> todas = listar();
        for (int i = 0; i < todas.size(); i++) {
            if (todas.get(i).getId() == consulta.getId()) {
                todas.set(i, consulta);
                break;
            }
        }
        salvarLista(todas);
    }

    public Paciente buscarPacientePorId(int idPaciente) {
        List<Paciente> pacientes = pacienteRepository.listar();
        for (Paciente p : pacientes) {
            if (p.getId() == idPaciente) {
                return p;
            }
        }
        return null; // paciente não encontrado
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

    private String toLinha(Consulta c) {
        return c.getId() + ";" +
                c.getIdPaciente() + ";" +
                c.getIdMedico() + ";" +
                c.getData() + ";" +
                c.getStatus() + ";" +
                c.getComentarioMedico();
    }

    private Consulta fromLinha(String linha) {
        String[] p = linha.split(";");
        return new Consulta(
                Integer.parseInt(p[0]),
                Integer.parseInt(p[1]),
                Integer.parseInt(p[2]),
                p[3],
                p[4],
                p[5]
        );
    }
}
