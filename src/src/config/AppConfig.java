package config;

import controllers.*;
import repository.implementation.*;
import services.implementation.*;
import cli.paciente.*;
import cli.medico.*;

import java.util.Scanner;

public class AppConfig {
    private final Scanner scanner = new Scanner(System.in);

    // Repositories
    private final PacienteFileRepository pacienteRepo = new PacienteFileRepository();
    private final MedicoFileRepository medicoRepo = new MedicoFileRepository();
    private final ConsultaFileRepository consultaRepo = new ConsultaFileRepository();
    private final AvaliacaoFileRepository avaliacaoRepo = new AvaliacaoFileRepository();

    // Services
    private final PacienteServiceImpl pacienteService =  new PacienteServiceImpl(pacienteRepo);
    private final MedicoServiceImpl medicoService = new MedicoServiceImpl(medicoRepo);
    private final ConsultaServiceImpl consultaService = new ConsultaServiceImpl(consultaRepo);
    private final AvaliacaoServiceImpl avaliacaoService = new AvaliacaoServiceImpl(avaliacaoRepo, consultaRepo);

    // Controllers
    public PacienteController pacienteController() {
        return new PacienteController(pacienteService);
    }

    public MedicoController medicoController() {
        return new MedicoController(medicoService);
    }

    public ConsultaController consultaController() {
        return new ConsultaController(consultaService);
    }

    public AvaliacaoController avaliacaoController() {
        return new AvaliacaoController(avaliacaoService);
    }
    //Input helper menus p/m

    private final PacienteInputHelper pacienteInput = new PacienteInputHelper(scanner, pacienteController());
    private final MedicoInputHelper medicoInput = new MedicoInputHelper(scanner, medicoController());

    //Menus cadastro p/m

    private final MenuPacienteCadastro menuPacienteCadastro = new MenuPacienteCadastro(pacienteController(), pacienteInput);
    private final MenuMedicoCadastro menuMedicoCadastro = new MenuMedicoCadastro(medicoController(), medicoInput);

    //Menus logados p/m

    private final MenuPacienteLogado menuPacienteLogado = new MenuPacienteLogado(medicoController(), consultaController(), avaliacaoController());
    private final MenuMedicoLogado menuMedicoLogado = new MenuMedicoLogado(medicoController(), consultaController());

    //Menus logins p/m

    private final MenuPacienteLogin menuPacienteLogin = new MenuPacienteLogin(pacienteController(), menuPacienteLogado);
    private final MenuMedicoLogin menuMedicoLogin = new MenuMedicoLogin(medicoController(),menuMedicoLogado);

    //Menus Acesso p/m

    private final MenuMedicoAcesso menuMedicoAcesso = new MenuMedicoAcesso(menuMedicoCadastro, menuMedicoLogin);
    private final MenuPacienteAcesso menuPacienteAcesso = new MenuPacienteAcesso(menuPacienteCadastro, menuPacienteLogin);

    // Funções menus

    public MenuPacienteAcesso pacienteAcesso() {
        return menuPacienteAcesso;
    }
    public MenuPacienteLogin pacienteLogin() {
        return menuPacienteLogin;
    }
    public MenuPacienteCadastro pacienteCadastro() {
        return menuPacienteCadastro;
    }

    public MenuMedicoAcesso medicoAcesso() {
        return menuMedicoAcesso;
    }
    public MenuMedicoLogin medicoLogin() {
        return menuMedicoLogin;
    }
    public MenuMedicoCadastro medicoCadastro() {
        return menuMedicoCadastro;
    }
}
