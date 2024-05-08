package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.lib.Location;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    public MainPage(Main app, double width, double height) {
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

        container.getChildren().setAll(
                new Header(this.app),
                this.body(this.app.locations.getLocations().values()),
                new Footer()
        );

        this.scene.getStylesheets().add(this.getResource("/css/main_page.css"));
    }

    private Node body(Collection<Location> locations) {
        VBox body = new VBox();
        body.getStyleClass().setAll("body");
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(64));

        body.setOnMouseClicked(event -> {
            container.getChildren().setAll(
                    new Header(this.app),
                    this.body(this.app.locations.getLocations().values()),
                    new Footer()
            );
        });

        TextField findDestinations = new TextField();
        findDestinations.getStyleClass().setAll("destinations");
        findDestinations.setPromptText("Find destinations");
        findDestinations.setMaxWidth(600);
        findDestinations.setCursor(Cursor.TEXT);

        body.getChildren().setAll(
                findDestinations,
                this.categories(),
                this.locations(locations)
        );

        return body;
    }

    private Node categories() {
        VBox categories = new VBox();
        categories.getStyleClass().setAll("categories");
        categories.setAlignment(Pos.CENTER);
        categories.setSpacing(16);
        categories.setPadding(new Insets(32, 0, 0, 0));

        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER);
        row1.setSpacing(32);
        row1.getChildren().setAll(
                createCategory("/images/main_page/mountains.png", "Mountains"),
                createCategory("/images/main_page/beaches.png", "Beaches"),
                createCategory("/images/main_page/churches.png", "Churches"),
                createCategory("/images/main_page/historical.png", "Historical")
        );

        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER);
        row2.setSpacing(32);
        row2.getChildren().setAll(
                createCategory("/images/main_page/parks.png", "Parks"),
                createCategory("/images/main_page/volcanoes.png", "Volcanoes"),
                createCategory("/images/main_page/hills.png", "Hills"),
                createCategory("/images/main_page/falls.png", "Falls")
        );

        categories.getChildren().setAll(
                row1,
                row2
        );

        return categories;
    }

    private Node createCategory(String image, String text) {
        Button category = new Button(text);
        category.getStyleClass().setAll("category");
        category.setAlignment(Pos.CENTER);
        category.setMinWidth(170);
        category.setMinHeight(70);
        category.setCursor(Cursor.HAND);

        category.setStyle("-fx-background-image: url('" + this.getResource(image) + "');");

        category.setOnAction(event -> {
            Collection<Location> filteredLocation = this.app.locations
                    .getLocations()
                    .values()
                    .stream()
                    .filter(v -> v.getCategory().equals(text.toLowerCase()))
                    .collect(Collectors.toList());

            container.getChildren().setAll(
                    new Header(this.app),
                    this.body(filteredLocation),
                    new Footer()
            );
        });

        return category;
    }

    private Node locations(Collection<Location> locations) {
        VBox locationsContainer = new VBox();
        locationsContainer.getStyleClass().setAll("locations-container");
        locationsContainer.setPadding(new Insets(32, 128, 128, 128));

        Label moreToExplore = new Label("More to explore...");
        moreToExplore.getStyleClass().setAll("more-to-explore");
        moreToExplore.setPadding(new Insets(0, 0, 32, 0));

        locationsContainer.getChildren().add(moreToExplore);

        HBox locationsRow1 = new HBox();
        HBox.setHgrow(locationsRow1, Priority.ALWAYS);
        locationsRow1.setSpacing(32);

        for (Location location : locations) {
            Button locationButton = createLocation(location);
            locationsRow1.getChildren().add(locationButton);
        }

        locationsContainer.getChildren().add(locationsRow1);

        return locationsContainer;
    }

    private Button createLocation(Location location) {
        Button button = new Button();
        HBox.setHgrow(button, Priority.ALWAYS);
        button.getStyleClass().setAll("location");
        button.setCursor(Cursor.HAND);

        button.setOnAction(event -> {
            this.app.LocationPageInstance.setLocation(location);
            this.app.switchScreen(this.app.LocationPage);
        });

        HBox name = new HBox();
        name.setMinWidth(250);
        name.setMaxWidth(250);
        name.setMinHeight(350);
        name.setMaxHeight(350);
        name.setAlignment(Pos.TOP_LEFT);
        name.setSpacing(8);

        ImageView locIcon = new ImageView(new Image(this.getResource("/images/icons/location.png")));
        locIcon.setPreserveRatio(true);
        locIcon.setFitWidth(32);

        name.getChildren().add(locIcon);
        name.getChildren().add(new Label(location.getName()));

        button.setGraphic(name);

        button.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[0]) + "');");

        return button;
    }


    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}
