package cc103.group3.philspot;

import cc103.group3.philspot.pages.HomePage;
import cc103.group3.philspot.pages.Login;
import cc103.group3.philspot.pages.Register;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage primaryStage;
    public Scene homePage, login, register;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.homePage = new HomePage(this, 1024, 768).getScene();
        this.login = new Login(this, 1024, 768).getScene();
        this.register = new Register(this, 1024, 768).getScene();

        primaryStage.setScene(this.homePage);
        primaryStage.setTitle("PhilSpot");
        primaryStage.show();
    }
}