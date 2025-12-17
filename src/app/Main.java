package app;

import cli.MenuPrincipal;
import config.AppConfig;

public class Main {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        MenuPrincipal app = new MenuPrincipal(config);
        app.abrir();
    }
}
