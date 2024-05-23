package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.lib.Category;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
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
            this.app.switchScreen(this.app.SearchPage);
        });

        body.getChildren().setAll(
                findDestinations,
                this.categories(),
                this.locations(locations)
        );

        return body;
    }

    private Node categories() {
        GridPane categories = new GridPane();
        VBox.setMargin(categories, new Insets(40, 0, 0, 0));
        categories.getStyleClass().setAll("categories");
        categories.setAlignment(Pos.CENTER);
        categories.setGridLinesVisible(false);
        categories.setHgap(32);
        categories.setVgap(32);

        Category[] categoryList = {
                new Category("/images/main_page/mountains.png", "Mountains"),
                new Category("/images/main_page/beaches.png", "Beaches"),
                new Category("/images/main_page/churches.png", "Churches"),
                new Category("/images/main_page/historical.png", "Historical"),
                new Category("/images/main_page/parks.png", "Parks"),
                new Category("/images/main_page/volcanoes.png", "Volcanoes"),
                new Category("/images/main_page/hills.png", "Hills"),
                new Category("/images/main_page/falls.png", "Falls")
        };

        for (int i = 0; i < categoryList.length; i++) {
            int col = i % 4;
            int row = i / 4;

            Category category = categoryList[i];

            Button categoryButton = new Button();
            categoryButton.getStyleClass().setAll("category");
            categoryButton.setAlignment(Pos.CENTER);
            categoryButton.setMinWidth(170);
            categoryButton.setMinHeight(70);
            categoryButton.setCursor(Cursor.HAND);

            Label name = new Label(category.getText());
            name.getStyleClass().setAll("text");
            name.setAlignment(Pos.CENTER);
            name.setMinWidth(170);
            name.setMinHeight(70);

            categoryButton.setGraphic(name);

            categoryButton.setStyle("-fx-background-image: url('" + this.getResource(category.getImage()) + "');");

            categoryButton.setOnAction(event -> {
                Collection<Location> filteredLocation = this.app.locations
                        .getLocations()
                        .values()
                        .stream()
                        .filter(v -> v.getCategory().equals(category.getText().toLowerCase()))
                        .collect(Collectors.toList());

                container.getChildren().setAll(
                        new Header(this.app),
                        this.body(filteredLocation),
                        new Footer()
                );
            });

            categories.add(categoryButton, col, row);
        }

        return categories;
    }

    private Node locations(Collection<Location> locations) {
        VBox locationsContainer = new VBox();
        locationsContainer.getStyleClass().setAll("locations-container");
        locationsContainer.setAlignment(Pos.CENTER);
        locationsContainer.setPadding(new Insets(32, 0, 128, 0));

        Label moreToExplore = new Label("More to explore...");
        moreToExplore.getStyleClass().setAll("more-to-explore");
        moreToExplore.setPadding(new Insets(0, 0, 32, 0));

        locationsContainer.getChildren().add(moreToExplore);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(false);
        grid.setHgap(32);
        grid.setVgap(32);

        for (int i = 0; i < locations.size(); i++) {
            int col = i % 5;
            int row = i / 5;

            Location location = (Location) locations.toArray()[i];
            Button locationButton = createLocation(location);
            grid.add(locationButton, col, row);
        }

        locationsContainer.getChildren().add(grid);

        return locationsContainer;
    }

    private Button createLocation(Location location) {
        Button button = new Button();
        button.getStyleClass().setAll("location");
        button.setMinWidth(200);
        button.setMaxWidth(200);
        button.setMinHeight(300);
        button.setMaxHeight(300);
        button.setCursor(Cursor.HAND);
        button.setAlignment(Pos.TOP_LEFT);

        button.setOnAction(event -> {
            this.app.LocationPageInstance.setLocation(location);
            this.app.switchScreen(this.app.LocationPage);
        });

        HBox name = new HBox();
        name.getStyleClass().setAll("name-container");
        name.setMaxHeight(24);
        name.setSpacing(8);

        ImageView locIcon = new ImageView(new Image(this.getResource("/images/icons/location.png")));
        locIcon.setPreserveRatio(true);
        locIcon.setFitWidth(24);

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
