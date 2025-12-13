package org.example.datasensefx.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneManager {

    static Stage primaryStage;

    // Tamaño mínimo para que no quede todo "encogido"
    private static final double MIN_WIDTH = 1000;
    private static final double MIN_HEIGHT = 700;

    public static void setPrimaryStage(Stage stage) {
        System.out.println("[SCENE] setPrimaryStage llamado con stage = " + stage);
        primaryStage = stage;
    }

    private static void ensureStage() {
        if (primaryStage == null) {
            throw new IllegalStateException(
                    "SceneManager.primaryStage es null. " +
                    "¿Seguro que has llamado a SceneManager.setPrimaryStage(stage) en Main.start()?"
            );
        }
    }

    public static void switchScene(String fxmlFile, String title, int width, int height) throws IOException {
        ensureStage();

        URL url = SceneManager.class.getResource(fxmlFile);
        System.out.println("[SCENE] Cargando FXML: " + fxmlFile);
        System.out.println("[SCENE] URL = " + url);

        if (url == null) {
            throw new IOException("No se encontró el FXML: " + fxmlFile);
        }

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        boolean maximized = primaryStage.isMaximized();

        // Creamos la escena sin forzar tamaño fijo
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);

        if (!maximized) {
            // Ajustamos la ventana al contenido
            primaryStage.sizeToScene();

            // Y aseguramos un tamaño mínimo (el "pelín de margen")
            if (primaryStage.getWidth() < MIN_WIDTH) {
                primaryStage.setWidth(MIN_WIDTH);
            }
            if (primaryStage.getHeight() < MIN_HEIGHT) {
                primaryStage.setHeight(MIN_HEIGHT);
            }
        } else {
            // Si estaba maximizada, mantenemos pantalla completa
            primaryStage.setMaximized(true);
        }

        primaryStage.show();
    }
}
