package com.mycompany.logistik;

/**
 * Launcher class sebagai entry point alternatif untuk menjalankan aplikasi JavaFX.
 * Class ini tidak meng-extends javafx.application.Application, sehingga dapat
 * dijalankan langsung (Run File / exec:exec) tanpa kendala "JavaFX runtime components are missing".
 */
public class Launcher {
    public static void main(String[] args) {
        App.main(args);
    }
}

