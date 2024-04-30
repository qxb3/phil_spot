package cc103.group3.philspot.pages;

import cc103.group3.philspot.Main;
import com.mongodb.client.MongoCollection;
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
import org.bson.Document;

import java.util.Objects;

public class RegisterPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;

    public RegisterPage(Main app, double width, double height) {
        this.app = app;

        ImageView logoImage = new ImageView(new Image(getResource("/images/philspot_logo.png")));
        logoImage.setPreserveRatio(true);
        logoImage.setFitWidth(148);

        Button logoButton = new Button();
        logoButton.setBackground(Background.EMPTY);
        logoButton.setGraphic(logoImage);
        logoButton.setCursor(Cursor.HAND);
        logoButton.setOnAction(event -> this.app.switchScreen(this.app.LandingPage));

        container.getStyleClass().setAll("container");
        container.setPadding(new Insets(32));
        container.getChildren().setAll(
                logoButton,
                this.body()
        );

        this.scene = new Scene(container, width, height);
        this.scene.getStylesheets().add(this.getResource("/css/register_page.css"));
    }

    private Node body() {
        VBox body = new VBox();
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(64, 0, 0, 0));
        body.setSpacing(32);
        body.getStyleClass().setAll("body");

        Label loginTxt = new Label("Register");
        loginTxt.getStyleClass().setAll("register-txt");

        HBox errorBox = new HBox();
        errorBox.setMaxWidth(300);
        errorBox.getStyleClass().setAll("error-box");
        errorBox.setVisible(false);

        Label errorMessage = new Label("");
        errorBox.getChildren().setAll(errorMessage);

        TextField username = new TextField();
        username.getStyleClass().setAll("input", "username");
        username.setPromptText("Username");
        username.setMaxWidth(300);
        username.setCursor(Cursor.TEXT);

        PasswordField password = new PasswordField();
        password.getStyleClass().setAll("input", "password");
        password.setPromptText("Password");
        password.setMaxWidth(300);
        password.setCursor(Cursor.TEXT);

        HBox loginContainer = new HBox();
        loginContainer.setAlignment(Pos.CENTER);

        Button registerButton = new Button("Register");
        registerButton.setAlignment(Pos.CENTER);
        registerButton.getStyleClass().setAll("register-button");
        registerButton.setMinWidth(300);
        registerButton.setOnAction(event -> {
            String userName = username.getText();
            String userPassword = password.getText();

            if (userName.isEmpty()) {
                errorMessage.setText("Username cannot be empty");
                errorBox.setVisible(true);
                return;
            }

            if (userPassword.isEmpty()) {
                errorMessage.setText("Password cannot be empty");
                errorBox.setVisible(true);
                return;
            }

            MongoCollection<Document> users = this.app.database.getCollection("Users");
            Document newUser = new Document()
                    .append("username", userName)
                    .append("password", userPassword);

            users.insertOne(newUser);

            this.app.switchScreen(this.app.LoginPage);
        });

        Label alreadyHaveAnAccount = new Label("Already have an account?");
        Hyperlink login = new Hyperlink("Login");
        login.setOnAction(event -> this.app.switchScreen(this.app.LoginPage));

        loginContainer.getChildren().setAll(registerButton, alreadyHaveAnAccount, login);

        body.getChildren().setAll(
                loginTxt,
                errorBox,
                username,
                password,
                registerButton,
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