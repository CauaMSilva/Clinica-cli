package repository.implementation;

import models.entities.Medico;
import models.entities.Paciente;
import repository.interfaces.MedicoRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoFileRepository implements MedicoRepository {

    private final String caminho = "data/medicos.txt";

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
    public void salvar(Medico medico) {
        garantirArquivoExiste();
        try (FileWriter fw = new FileWriter(caminho, true)) {
            fw.write(toLinha(medico) + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar médico");
        }
    }

    private void salvarLista(List<Medico> medicos) {
        try (FileWriter fw = new FileWriter(caminho, false)) { // sobrescreve
            for (Medico m: medicos) {
                fw.write(toLinha(m) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao reescrever arquivo de pacientes.");
        }
    }

    @Override
    public List<Medico> listar() {
        garantirArquivoExiste();
        List<Medico> medicos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;

            while ((linha = br.readLine()) != null) {
                Medico m = fromLinha(linha);
                medicos.add(m);
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler médicos");
        }

        return medicos;
    }

    @Override
    public Medico buscarPorId(int id) {
        garantirArquivoExiste();
        for (Medico m: listar()) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public Medico buscarPorNome(String nome){
        garantirArquivoExiste();
        for (Medico m: listar()){
            if (m.getNome().equals(nome)){
                return m;
            }
        }
        return null;
    }

    public List<Medico> listarPorPlano(String plano){
        garantirArquivoExiste();
            List<Medico> resultado = new ArrayList<>();

        for (Medico m: listar()){
            if (m.getPlanoAtendido().equalsIgnoreCase(plano)){
                resultado.add(m);
            }
        }
        return resultado;
    }

    public void deletar(int id){
        garantirArquivoExiste();
        List<Medico> medicos = listar();

        boolean removido = medicos.removeIf(p -> p.getId() == id);

        if (!removido){
            throw new IllegalArgumentException("Medico não encontrado para exclusão");
        }

        salvarLista(medicos);
    }

    public void atualizar(Medico medico){
        garantirArquivoExiste();
        List<Medico> medicos = listar();
        boolean encontrado = false;

        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId() == medico.getId()) {
                medicos.set(i, medico);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new IllegalArgumentException("Paciente não encontrado para atualização.");
        }

        salvarLista(medicos);
    }

    @Override
    public int gerarNovoId() {
        garantirArquivoExiste();
        List<Medico> lista = listar();
        if (lista.isEmpty()) return 1;

        return lista.get(lista.size()-1).getId() + 1;
    }

    private String toLinha(Medico m) {
        return m.getId() + ";" +
                m.getNome() + ";" +
                m.getEspecialidade() + ";" +
                m.getPlanoAtendido();
    }

    private Medico fromLinha(String linha) {
        if (linha == null || linha.isBlank()) {
            return null;
        }

        String[] partes = linha.split(";");
        if (partes.length < 4) {
            return null;
        }

        int id;
        try {
            id = Integer.parseInt(partes[0].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        String nome = partes[1].trim();
        String especialidade = partes[2].trim();
        String plano = partes[3].trim();

        return new Medico(id, nome, especialidade, plano);
    }
}
