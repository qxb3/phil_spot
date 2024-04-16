package cc103.group3.philspot;

import cc103.group3.philspot.pages.HomePage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new HomePage(1024, 768));
        primaryStage.show();
    }
}