package cc103.group3.philspot.pages;

import cc103.group3.philspot.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class LoginPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;

    public LoginPage(Main app, double width, double height) {
        this.app = app;

        ImageView logoImage = new ImageView(new Image(getResource("/images/philspot_logo.png")));
        logoImage.setPreserveRatio(true);
        logoImage.setFitWidth(148);

        Button logoButton = new Button();
        logoButton.setBackground(Background.EMPTY);
        logoButton.setGraphic(logoImage);
        logoButton.setCursor(Cursor.HAND);
        logoButton.setOnAction(event -> {
            this.app.switchScreen(this.app.HomePage);
        });

        container.getStyleClass().setAll("container");
        container.setPadding(new Insets(32));
        container.getChildren().setAll(
                logoButton,
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

        HBox errorBox = new HBox();
        errorBox.setMaxWidth(300);
        errorBox.getStyleClass().setAll("error-box");
        errorBox.setVisible(false);

        Label errorMessage = new Label("");
        errorBox.getChildren().setAll(errorMessage);

        TextField email = new TextField();
        email.getStyleClass().setAll("input", "email");
        email.setPromptText("Email");
        email.setMaxWidth(300);
        email.setCursor(Cursor.TEXT);

        PasswordField password = new PasswordField();
        password.getStyleClass().setAll("input", "password");
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setCursor(Cursor.TEXT);

        HBox loginContainer = new HBox();
        loginContainer.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setAlignment(Pos.CENTER);
        loginButton.getStyleClass().setAll("login-button");
        loginButton.setMinWidth(300);
        loginButton.setOnAction(event -> {
            String userEmail = email.getText();
            String userPassword = password.getText();

            if (userEmail.isEmpty()) {
                errorMessage.setText("Email cannot be empty");
                errorBox.setVisible(true);
                return;
            }

            if (userPassword.isEmpty()) {
                errorMessage.setText("Password cannot be empty");
                errorBox.setVisible(true);
                return;
            }

            if (!userEmail.contentEquals("test") || !userPassword.contentEquals("test123")) {
                errorMessage.setText("Invalid email or password");
                errorBox.setVisible(true);
                return;
            }

            errorMessage.setText("");
            errorBox.setVisible(false);

            // this.app.switchScreen();
        });

        Label dontHaveAnAccount = new Label("Dont't have an account?");
        Hyperlink register = new Hyperlink("Register");
        register.setOnAction(event -> this.app.switchScreen(this.app.RegisterPage));

        loginContainer.getChildren().setAll(loginButton, dontHaveAnAccount, register);

        body.getChildren().setAll(
                loginTxt,
                errorBox,
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