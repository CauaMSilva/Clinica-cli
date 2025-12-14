package repository.implementation;

import models.entities.Medico;
import models.entities.Paciente;
import repository.interfaces.PacienteRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteFileRepository implements PacienteRepository {

    private final String caminho = "data/pacientes.txt";

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

    public void salvar(Paciente paciente) {
        garantirArquivoExiste();
        try (FileWriter fw = new FileWriter(caminho, true)) {
            fw.write(toLinha(paciente) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar paciente ");
        }
    }

    public List<Paciente> listar() {
        garantirArquivoExiste();
        List<Paciente> pacientes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                Paciente p = fromLinha(linha);
                pacientes.add(p);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler pacientes");
        }

        return pacientes;
    }

    public Paciente buscarPorNome(String nome) {
        garantirArquivoExiste();
        for (Paciente p: listar()) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    public Paciente buscarPorId(int id) {
        garantirArquivoExiste();
        for (Paciente p: listar()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private void salvarLista(List<Paciente> pacientes) {
        garantirArquivoExiste();
        try (FileWriter fw = new FileWriter(caminho, false)) { // sobrescreve
            for (Paciente p : pacientes) {
                fw.write(toLinha(p) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao reescrever arquivo de pacientes.");
        }
    }

    public void atualizar(Paciente paciente) {
        garantirArquivoExiste();
        List<Paciente> pacientes = listar();
        boolean encontrado = false;

        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId() == paciente.getId()) {
                pacientes.set(i, paciente);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Paciente não encontrado para atualização.");
        }

        salvarLista(pacientes);
    }

    public void deletar(int id) {
        garantirArquivoExiste();
        List<Paciente> pacientes = listar();

        boolean removido = pacientes.removeIf(p -> p.getId() == id);

        if (!removido) {
            throw new IllegalArgumentException("Paciente não encontrado para exclusão.");
        }

        salvarLista(pacientes);
    }

    public int gerarNovoId() {
        garantirArquivoExiste();
        List<Paciente> lista = listar();
        if (lista.isEmpty()) return 1;

        return lista.get(lista.size()-1).getId() + 1;
    }


    private String toLinha(Paciente paciente) {
        return paciente.getId() + ";" +
                paciente.getNome() + ";" +
                paciente.getIdade() + ";" +
                paciente.getPlanoSaude();
    }

    private Paciente fromLinha(String linha) {
        String[] partes = linha.split(";");

        return new Paciente(
                Integer.parseInt(partes[0]),
                partes[1],
                Integer.parseInt(partes[2]),
                partes[3]
        );
    }
}
