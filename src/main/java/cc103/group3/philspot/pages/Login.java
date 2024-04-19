package cc103.group3.philspot.pages;

import cc103.group3.philspot.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class Login {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main main;
    private final double width;
    private final double height;

    public Login(Main main, double width, double height) {
        this.main = main;
        this.width = width;
        this.height = height;

        ImageView logoImage = new ImageView(new Image(getResource("/images/philspot_logo.png")));
        logoImage.setPreserveRatio(true);
        logoImage.setFitWidth(148);

        container.getStyleClass().setAll("container");
        container.setPadding(new Insets(32));
        container.getChildren().setAll(
                logoImage,
                this.body()
        );

        this.scene = new Scene(container, width, height);
        this.scene.getStylesheets().add(this.getResource("/css/login_page.css"));
    }

    private Node body() {
        VBox body = new VBox();
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(64, 0, 0, 0));
        body.setSpacing(32);
        body.getStyleClass().setAll("body");

        Label loginTxt = new Label("Login");
        loginTxt.getStyleClass().setAll("login-txt");

        TextField email = new TextField();
        email.getStyleClass().setAll("input", "email");
        email.setPromptText("Email");
        email.setMaxWidth(300);

        TextField password = new TextField();
        password.getStyleClass().setAll("input", "password");
        password.setPromptText("Password");
        password.setMaxWidth(300);

        HBox loginContainer = new HBox();
        loginContainer.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setAlignment(Pos.CENTER);
        loginButton.getStyleClass().setAll("login-button");
        loginButton.setMinWidth(300);

        Label dontHaveAnAccount = new Label("Dont't have an account?");
        Hyperlink register = new Hyperlink("Register");
        register.setOnAction(event -> {
            this.main.primaryStage.setScene(this.main.register);
        });

        loginContainer.getChildren().setAll(loginButton, dontHaveAnAccount, register);

        body.getChildren().setAll(
                loginTxt,
                email,
                password,
                loginButton,
                loginContainer
        );

        return body;
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}