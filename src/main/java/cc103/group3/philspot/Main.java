package cc103.group3.philspot;

import cc103.group3.philspot.pages.HomePage;
import cc103.group3.philspot.pages.LoginPage;
import cc103.group3.philspot.pages.RegisterPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
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

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        this.HomePage = new HomePage(this, screenWidth, screenHeight).getScene();
        this.LoginPage = new LoginPage(this, screenWidth, screenHeight).getScene();
        this.RegisterPage = new RegisterPage(this, screenWidth, screenHeight).getScene();

        primaryStage.setScene(this.HomePage);
        primaryStage.setTitle("PhilSpot");
        primaryStage.show();
    }

    public void switchScreen(Scene scene) {
        this.primaryStage.setScene(scene);
    }
}