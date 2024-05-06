package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.pages.shared.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class AbousUsPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    public AbousUsPage(Main app, double width, double height) {
        this.app = app;
        this.width = width;
        this.height = height;

        this.scene = new Scene(container, width, height);

        container.getStyleClass().setAll("container");
        container.getChildren().setAll(
                new Header(this.app),
                this.header(),
                this.body()
        );

        this.scene.getStylesheets().add(this.getResource("/css/about_us_page.css"));
    }

    private Node header() {
        HBox headerContainer = new HBox();
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        headerContainer.setSpacing(16);
        headerContainer.getStyleClass().setAll("header-container");

        Button backButton = new Button("◄");
        backButton.setCursor(Cursor.HAND);

        backButton.getStyleClass().setAll("back-button");
        backButton.setOnAction(event -> {
            this.app.switchScreen(this.app.MainPage);
        });

        Label pageTitle = new Label("About Us");
        pageTitle.getStyleClass().setAll("page-title");

        headerContainer.getChildren().setAll(backButton, pageTitle);

        return headerContainer;
    }

    private Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.setPadding(new Insets(20, 0, 0, 0));
        bodyContainer.setAlignment(Pos.TOP_CENTER);
        bodyContainer.setSpacing(30);
        bodyContainer.getStyleClass().setAll("body");

        Label first = new Label(
                "Welcome to Phil Spot, your ultimate destination for travel enthusiasts\n" +
                "seeking personalized recommendations and unforgettable experiences. At\n" +
                "Phil Spot, we believe that every journey is unique, and we are dedicated to\n" +
                "helping you discover the perfect destinations tailored to your preferences."
        );

        HBox secondContainer = new HBox();
        secondContainer.setAlignment(Pos.CENTER);
        secondContainer.setSpacing(20);

        Label second = new Label(
                "Join our community of explorers,\n" +
                "share your travel experiences, and\n" +
                "inspire others to embark on their own\n" +
                "adventures. Let Phil Spot be your compass\n" +
                "as you navigate the world and create\n" +
                "lifelong memories one destination at a time."
        );

        ImageView logoText = new ImageView(new Image(this.getResource("/images/philspot_logo.png")));
        logoText.setPreserveRatio(true);
        logoText.setFitWidth(400);

        secondContainer.getChildren().setAll(second, logoText);

        Label third = new Label("Start your journey today with Phil Spot – where travel dreams become reality.");
        third.getStyleClass().setAll("third-txt");

        bodyContainer.getChildren().setAll(
                first,
                secondContainer,
                third
        );

        return bodyContainer;
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}