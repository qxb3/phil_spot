package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.pages.shared.Footer;
import cc103.group3.philspot.pages.shared.Header;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SearchPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    ArrayList<Location> result = new ArrayList<>();
    String query = "";

    public SearchPage(Main app, double width, double height) {
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

        this.scene.getStylesheets().add(this.getResource("/css/search_page.css"));
    }

    private Node header() {
        HBox headerContainer = new HBox();
        headerContainer.getStyleClass().setAll("header-container");
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        headerContainer.setSpacing(16);

        Button backButton = new Button("◄");
        backButton.setCursor(Cursor.HAND);

        backButton.getStyleClass().setAll("back-button");
        backButton.setOnAction(event -> {
            this.app.switchScreen(this.app.MainPage);
        });

        TextField findDestinations = new TextField();
        findDestinations.getStyleClass().setAll("destinations");
        findDestinations.setPromptText("Find destinations");
        findDestinations.setMinWidth(500);
        findDestinations.setMaxWidth(500);
        findDestinations.setCursor(Cursor.TEXT);
        findDestinations.setText(this.query);

        findDestinations.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ENTER || findDestinations.getText().isEmpty()) return;

            ArrayList<Location> searchResult = new ArrayList<>();
            String query = findDestinations.getText().toLowerCase();

            for (Location location : this.app.locations.getLocations().values()) {
                if (location.getName().toLowerCase().contains(query)) {
                    searchResult.add(location);
                }
            }

            this.app.SearchPageInstance.setResult(searchResult, findDestinations.getText());
        });

        headerContainer.getChildren().setAll(backButton, findDestinations);

        return headerContainer;
    }

    private Node results() {
        VBox resultsContainer = new VBox();
        resultsContainer.getStyleClass().setAll("results-container");
        resultsContainer.setPadding(new Insets(30));
        resultsContainer.setSpacing(32);

        for (Location location : this.result) {
            Button locationButton = new Button();
            locationButton.getStyleClass().setAll("location-img");
            locationButton.setCursor(Cursor.HAND);

            locationButton.setMinWidth(400);
            locationButton.setMaxWidth(400);
            locationButton.setMinHeight(250);
            locationButton.setMaxHeight(250);
            locationButton.setAlignment(Pos.TOP_LEFT);
            locationButton.setPadding(new Insets(16));

            locationButton.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[0]) + "');");

            locationButton.setOnAction(event -> {
                this.app.LocationPageInstance.setLocation(location);
                this.app.switchScreen(this.app.LocationPage);
            });

            Label locationName = new Label(location.getName());
            locationName.getStyleClass().setAll("location-name");

            HBox locationAddressContainer = new HBox();
            locationAddressContainer.setSpacing(4);
            locationAddressContainer.setAlignment(Pos.CENTER_LEFT);

            ImageView locationImg = new ImageView(new Image(this.getResource("/images/icons/location.png")));
            locationImg.setPreserveRatio(true);
            locationImg.setFitWidth(20);

            Label locationAddress = new Label(location.getLocation());
            locationAddress.getStyleClass().setAll("location-address");

            locationAddressContainer.getChildren().setAll(locationImg, locationAddress);

            Label locationDescription = new Label(location.getDescription());
            locationDescription.getStyleClass().setAll("location-description");
            locationDescription.setMaxWidth(this.width / 2.3);
            locationDescription.setMaxHeight(100);
            locationDescription.setMaxHeight(100);
            locationDescription.setWrapText(true);

            HBox ratingContainer = new HBox();
            ratingContainer.setAlignment(Pos.CENTER_LEFT);
            ratingContainer.setPadding(new Insets(16, 0, 0, 0));
            ratingContainer.setSpacing(8);

            int rating = (int) Arrays.stream(location.getReviews())
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0);

            for (int i = 0; i < rating; i++) {
                ImageView star = new ImageView(new Image(this.getResource("/images/icons/star.png")));
                star.setPreserveRatio(true);
                star.setFitWidth(28);

                ratingContainer.getChildren().add(star);
            }

            VBox locationRight = new VBox();
            locationRight.setSpacing(10);
            locationRight.setPadding(new Insets(18, 0, 18, 0));
            locationRight.getChildren().setAll(
                    locationName,
                    locationAddressContainer,
                    locationDescription,
                    ratingContainer
            );

            HBox locationContainer = new HBox();
            locationContainer.getStyleClass().setAll("location");
            locationContainer.setSpacing(24);
            locationContainer.getChildren().setAll(locationButton, locationRight);
            resultsContainer.getChildren().add(locationContainer);
        }

        return resultsContainer;
    }

    public void setResult(ArrayList<Location> result, String query) {
        this.result = result;
        this.query = query;

        Label noResult = new Label("No Result :(");
        noResult.getStyleClass().setAll("no-result");
        noResult.setPadding(new Insets(32));

        container.getChildren().setAll(
                new Header(this.app),
                this.header(),
                this.result.isEmpty() ? noResult : this.results(),
                new Footer()
        );
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}