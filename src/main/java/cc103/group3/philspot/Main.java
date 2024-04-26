package cc103.group3.philspot;

import cc103.group3.philspot.pages.LandingPage;
import cc103.group3.philspot.pages.LoginPage;
import cc103.group3.philspot.pages.RegisterPage;
import cc103.group3.philspot.pages.auth.MainPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public Stage primaryStage;
    public Scene LandingPage, LoginPage, RegisterPage,
                 MainPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        this.LandingPage = new LandingPage(this, screenWidth, screenHeight).getScene();
        this.LoginPage = new LoginPage(this, screenWidth, screenHeight).getScene();
        this.RegisterPage = new RegisterPage(this, screenWidth, screenHeight).getScene();
        this.MainPage = new MainPage(this, screenWidth, screenHeight).getScene();

        primaryStage.setScene(this.LandingPage);
        primaryStage.setTitle("PhilSpot");
        primaryStage.show();
    }

    public void switchScreen(Scene scene) {
        this.primaryStage.setScene(scene);
    }
}
