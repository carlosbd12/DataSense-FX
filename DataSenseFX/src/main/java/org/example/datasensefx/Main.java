package org.example.datasensefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.datasensefx.utils.SceneManager;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Registramos el stage principal en el SceneManager
        SceneManager.setPrimaryStage(stage);

        // Icono de la ventana (usa el mismo logo.png que en el login)
        Image appIcon = new Image(
                Main.class.getResourceAsStream("/org/example/datasensefx/images/logo.png")
        );
        stage.getIcons().add(appIcon);

        FXMLLoader fxmlLoader = new FXMLLoader(
                Main.class.getResource("/org/example/datasensefx/views/login-view.fxml")
        );

        // Login ya al mismo tamaño que el dashboard
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("DataSense - Iniciar Sesión");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
