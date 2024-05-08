package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.PersistentStore;
import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.pages.shared.Footer;
import cc103.group3.philspot.pages.shared.Header;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

public class WishlistsPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    private ArrayList<Location> userWishlists = new ArrayList<>();

    public WishlistsPage(Main app, double width, double height) {
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

        this.scene.getStylesheets().add(this.getResource("/css/wishlist_page.css"));
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

        Label pageTitle = new Label("My Wishlists...");
        pageTitle.getStyleClass().setAll("page-title");

        headerContainer.getChildren().setAll(backButton, pageTitle);

        return headerContainer;
    }

    private Node locations() {
        VBox locationsContainer = new VBox();
        locationsContainer.getStyleClass().setAll("locations-container");
        locationsContainer.setSpacing(48);
        locationsContainer.setPadding(new Insets(24));

        for (Location location : this.userWishlists) {
            Button locationButton = new Button();
            locationButton.getStyleClass().setAll("image");
            locationButton.setCursor(Cursor.HAND);

            locationButton.setMinWidth(250);
            locationButton.setMaxWidth(250);
            locationButton.setMinHeight(350);
            locationButton.setMaxHeight(350);
            locationButton.setAlignment(Pos.TOP_LEFT);
            locationButton.setPadding(new Insets(16));

            locationButton.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[0]) + "');");

            locationButton.setOnAction(event -> {
                this.app.LocationPageInstance.setLocation(location);
                this.app.switchScreen(this.app.LocationPage);
            });

            ImageView heart = new ImageView(new Image(this.getResource("/images/icons/heart_red.png")));
            heart.setPreserveRatio(true);
            heart.setFitWidth(48);

            locationButton.setGraphic(heart);

            Label locationName = new Label(location.getName());
            locationName.getStyleClass().setAll("name");

            HBox locationAddressContainer = new HBox();
            locationAddressContainer.setSpacing(4);
            locationAddressContainer.setAlignment(Pos.CENTER_LEFT);

            ImageView locationImg = new ImageView(new Image(this.getResource("/images/icons/location.png")));
            locationImg.setPreserveRatio(true);
            locationImg.setFitWidth(20);

            Label locationAddress = new Label(location.getLocation());
            locationAddress.getStyleClass().setAll("address");

            locationAddressContainer.getChildren().setAll(locationImg, locationAddress);

            Label locationDescription = new Label(location.getDescription());
            locationDescription.getStyleClass().setAll("description");
            locationDescription.setMaxWidth(this.width / 2.3);
            locationDescription.setMaxHeight(150);
            locationDescription.setMaxHeight(150);
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

            locationsContainer.getChildren().add(locationContainer);
        }

        return locationsContainer;
    }

    public void populate() {
        this.userWishlists.clear();

        Properties store = PersistentStore.loadData();
        MongoCollection<Document> wishlists = this.app.database.getCollection("Wishlists");
        FindIterable<Document> userWishlists = wishlists.find(new Document("user_id", store.getProperty("ID")));

        for (Document userWishlistsLoc : userWishlists) {
            this.userWishlists.add(this.app.locations.getLocations().get(userWishlistsLoc.getString("location")));
        }

        Label noWishlist = new Label("No Wishlists :(");
        noWishlist.getStyleClass().setAll("no-wishlists");
        noWishlist.setPadding(new Insets(32));

        container.getChildren().setAll(
                new Header(this.app),
                this.header(),
                this.userWishlists.size() <= 0 ? noWishlist : this.locations(),
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
