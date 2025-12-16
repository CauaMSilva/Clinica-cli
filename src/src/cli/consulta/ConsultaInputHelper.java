package cli.consulta;

import controllers.ConsultaController;
import models.entities.Consulta;
import controllers.AvaliacaoController;

import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsultaInputHelper {

    private final Scanner scanner;
    private final ConsultaController controller;
    private final AvaliacaoController avaliacaoController;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ConsultaInputHelper(Scanner scanner, ConsultaController controller, AvaliacaoController avaliacaoController) {
        this.scanner = scanner;
        this.controller = controller;
        this.avaliacaoController = avaliacaoController;
    }

    public void lerNovaConsulta() {
        try{
            int idPaciente = lerInteiroValido("ID do paciente: ");
            int idMedico = lerInteiroValido("ID do médico: ");
            String data = lerDataValida("Data da consulta (dd/mm/aaaa): ");

            Consulta consultaAgendada = controller.agendar(idPaciente,idMedico,data);

            System.out.println("\n✅ CONSULTA REGISTRADA COM SUCESSO!");
            System.out.println("------------------------------------");
            System.out.println("ID DA CONSULTA : " + consultaAgendada.getId());
            System.out.println("STATUS          : " + consultaAgendada.getStatus());
            System.out.println("DATA            : " + consultaAgendada.getData());
            System.out.println("------------------------------------");
            System.out.println("Anote o ID da consulta para futuras alterações.");

        } catch (IllegalArgumentException e){
            System.out.println("\nErro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nErro Inesperado: " + e.getMessage());
        }
    }
    private String lerDataValida(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String dataInput = scanner.nextLine();

            // Validação básica de formato por Regex antes de enviar ao Service
            if (dataInput.matches("\\d{2}/\\d{2}/\\d{4}")) {
                return dataInput;
            } else {
                System.out.println("Formato inválido! Use o padrão dia/mês/ano (Ex: 20/12/2025).");
            }
        }
    }
    private int lerInteiroValido(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, digite apenas números.");
            }
        }
    }

    public int lerIdConsulta() {
        System.out.print("ID da consulta: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public void menuAvaliacao() {
        try {
            System.out.println("\n--- AVALIAÇÃO DE ATENDIMENTO ---");
            int idConsulta = lerInteiroValido("Digite o ID da consulta realizada: ");
            int estrelas = lerInteiroValido("Nota (1 a 5 estrelas): ");
            System.out.print("Comentário (opcional): ");
            String comentario = scanner.nextLine();

            // Note que não passamos mais o ID do Médico, o Service resolve isso!
            avaliacaoController.avaliar(idConsulta, estrelas, comentario);

            System.out.println("✅ Obrigado! Sua avaliação ajuda a melhorar nossos serviços.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao avaliar: " + e.getMessage());
        }
    }
}
