package cc103.group3.philspot.pages;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.pages.shared.Footer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Objects;

public class LandingPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    public LandingPage(Main app, double width, double height) {
        this.app = app;
        this.width = width;
        this.height = height;

        container.getStyleClass().setAll("container");
        container.getChildren().setAll(this.body());

        this.scene = new Scene(container, width, height);
        this.scene.getStylesheets().add(this.getResource("/css/landing_page.css"));
    }

    private Node body() {
        VBox body = new VBox();
        body.getStyleClass().setAll("body");
        VBox.setVgrow(body, Priority.ALWAYS);

        HBox hero = new HBox();
        hero.setMinHeight(768);

        hero.getChildren().add(this.left());
        hero.getChildren().add(this.right());

        body.getChildren().add(hero);

        return body;
    }

    private Node left() {
        VBox left = new VBox();
        left.getStyleClass().setAll("left");

        ImageView logo = new ImageView();
        logo.setImage(new Image(this.getResource("/images/logo_text.png")));
        logo.setPreserveRatio(true);

        Label tagLine = new Label("EXPLORE THE FUN IN\nTHE PHILIPPINES");
        tagLine.setAlignment(Pos.BOTTOM_CENTER);
        tagLine.getStyleClass().setAll("tagline");

        Label travelEase = new Label("Make your travel at ease");
        travelEase.setAlignment(Pos.BOTTOM_CENTER);
        travelEase.getStyleClass().setAll("travel-ease");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> this.app.switchScreen(this.app.LoginPage));

        loginButton.setAlignment(Pos.BOTTOM_CENTER);
        loginButton.getStyleClass().setAll("login-button");

        Button signupButton = new Button("Sign Up");
        signupButton.setAlignment(Pos.BOTTOM_CENTER);
        signupButton.getStyleClass().setAll("signup-button");
        signupButton.setOnAction(event -> this.app.switchScreen(this.app.RegisterPage));

        left.getChildren().setAll(
                logo,
                tagLine,
                travelEase,
                loginButton,
                signupButton
        );

        return left;
    }

    private Node right() {
        HBox container = new HBox();
        HBox.setHgrow(container, Priority.ALWAYS);
        container.setAlignment(Pos.CENTER);
        container.getStyleClass().setAll("right");

        double IMAGE_WIDTH = 220;
        double IMAGE_SCALE = 0.9;

        ImageView leftImage = new ImageView(new Image(getResource("/images/left.jpg"), true));
        leftImage.setPreserveRatio(true);
        leftImage.setFitWidth(IMAGE_WIDTH);
        leftImage.setTranslateX(-leftImage.getFitWidth() / 1.2);
        leftImage.setScaleX(IMAGE_SCALE);
        leftImage.setScaleY(IMAGE_SCALE);
        leftImage.getStyleClass().add("stacked-image");

        ImageView centerImage = new ImageView(new Image(getResource("/images/center.jpg"), true));
        centerImage.setPreserveRatio(true);
        centerImage.setFitWidth(IMAGE_WIDTH + 28);
        centerImage.getStyleClass().add("stacked-image");

        ImageView rightImage = new ImageView(new Image(getResource("/images/right.jpg"), true));
        rightImage.setPreserveRatio(true);
        rightImage.setFitWidth(IMAGE_WIDTH);
        rightImage.setTranslateX(rightImage.getFitWidth() / 1.2);
        rightImage.setScaleX(IMAGE_SCALE);
        rightImage.setScaleY(IMAGE_SCALE);
        rightImage.getStyleClass().add("stacked-image");

        StackPane images = new StackPane();
        images.getChildren().setAll(leftImage, rightImage, centerImage);

        container.getChildren().setAll(images);

        return container;
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}