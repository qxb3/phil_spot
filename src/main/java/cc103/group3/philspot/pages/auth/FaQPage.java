package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.pages.shared.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class FaQPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    public FaQPage(Main app, double width, double height) {
        this.app = app;
        this.width = width;
        this.height = height;

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().setAll("scrollpane");
        scrollPane.setContent(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        this.scene = new Scene(scrollPane, width, height);

        container.getStyleClass().setAll("container");
        container.getChildren().setAll(
                new Header(this.app),
                this.header(),
                this.body()
        );

        this.scene.getStylesheets().add(this.getResource("/css/faq_page.css"));
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

        Label pageTitle = new Label("Frequently Asked Questions (FAQ)");
        pageTitle.getStyleClass().setAll("page-title");

        headerContainer.getChildren().setAll(backButton, pageTitle);

        return headerContainer;
    }

    private Node body() {
        VBox bodyContainer = new VBox();
        bodyContainer.setPadding(new Insets(38));
        bodyContainer.setAlignment(Pos.TOP_CENTER);
        bodyContainer.setSpacing(38);
        bodyContainer.getStyleClass().setAll("body");

        Label first = new Label(
                "Have Quetions? Here You’ll find the answers most valued by our\n" +
                "partners, along with access to step by step instructions and support."
        );

        HBox accordionContainer = new HBox();
        accordionContainer.setAlignment(Pos.CENTER);
        accordionContainer.setSpacing(20);

        ImageView logoText = new ImageView(new Image(this.getResource("/images/philspot_logo.png")));
        logoText.setPreserveRatio(true);
        logoText.setFitWidth(400);

        Label content1 = new Label("“No, Phil Spot is not a booking platform. We focus solely on providing travel recommendations and resources to assist travelers in planning their trips. However, we do provide links to trusted booking platforms and services for users' convenience.”");
        content1.setMaxWidth(this.width / 2);
        content1.setWrapText(true);
        content1.getStyleClass().setAll("accordion-content");

        Label content2 = new Label("“Yes, Phil Spot is completely free to use. We believe that everyone should have access to reliable travel information and recommendations without any barriers.”");
        content2.setMaxWidth(this.width / 2);
        content2.setWrapText(true);
        content2.getStyleClass().setAll("accordion-content");

        Label content3 = new Label("“Absolutely! We welcome contributions from fellow travelers who want to share their experiences and insights with our community. Simply reach out to us through our contact page, and we'll be happy to discuss how you can contribute.\"");
        content3.setMaxWidth(this.width / 2);
        content3.setWrapText(true);
        content3.getStyleClass().setAll("accordion-content");

        // Create TitledPanes with titles and content
        TitledPane titledPane1 = new TitledPane("Is PhilSpot a booking platform?", content1);
        TitledPane titledPane2 = new TitledPane("Is PhilSpot free to use?", content2);
        TitledPane titledPane3 = new TitledPane("Can I contribute my travel experiences to PhilSpot?", content3);

        // Create an Accordion and add the TitledPanes to it
        Accordion accordion = new Accordion();
        accordion.getPanes().addAll(titledPane1, titledPane2, titledPane3);

        accordionContainer.getChildren().setAll(logoText, accordion);

        bodyContainer.getChildren().setAll(
                first,
                accordionContainer
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