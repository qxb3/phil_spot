package cc103.group3.philspot;

import cc103.group3.philspot.pages.HomePage;
import cc103.group3.philspot.pages.LoginPage;
import cc103.group3.philspot.pages.RegisterPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage primaryStage;
    public Scene HomePage, LoginPage, RegisterPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        this.HomePage = new HomePage(this, 1024, 768).getScene();
        this.LoginPage = new LoginPage(this, 1024, 768).getScene();
        this.RegisterPage = new RegisterPage(this, 1024, 768).getScene();

        primaryStage.setScene(this.HomePage);
        primaryStage.setTitle("PhilSpot");
        primaryStage.show();
    }

    public void switchScreen(Scene scene) {
        this.primaryStage.setScene(scene);
    }
}