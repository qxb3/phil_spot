package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.PersistentStore;
import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.pages.shared.Footer;
import cc103.group3.philspot.pages.shared.Header;
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
import javafx.scene.layout.*;
import org.bson.Document;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class LocationPage {
    private static final VBox container = new VBox();
    private final Scene scene;
    private final Main app;
    private final double width;
    private final double height;

    private Location location;

    public LocationPage(Main app, double width, double height) {
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

        this.scene.getStylesheets().add(this.getResource("/css/location_page.css"));
    }

    private Node carousel() {
        HBox carousel = new HBox();
        carousel.getStyleClass().setAll("carousel");
        carousel.setAlignment(Pos.CENTER);

        Pane image = new Pane();
        image.setMinWidth(((double) 80 / 100) * this.width);
        image.setMaxWidth(((double) 80 / 100) * this.width);
        image.setMinHeight(500);
        image.setMaxHeight(500);

        AtomicInteger currentImage = new AtomicInteger();
        image.getStyleClass().setAll("carousel-img");
        image.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[currentImage.get()]) + "');");

        Button leftButton = new Button("<");
        HBox.setHgrow(leftButton, Priority.ALWAYS);
        VBox.setVgrow(leftButton, Priority.ALWAYS);
        leftButton.getStyleClass().setAll("control-button");
        leftButton.setAlignment(Pos.CENTER);
        leftButton.setCursor(Cursor.HAND);

        leftButton.setOnAction(event -> {
            if (currentImage.get() <= 0) return;

            currentImage.set(currentImage.get() - 1);
            image.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[currentImage.get()]) + "');");
        });

        Button rightButton = new Button(">");
        HBox.setHgrow(rightButton, Priority.ALWAYS);
        VBox.setVgrow(rightButton, Priority.ALWAYS);
        rightButton.getStyleClass().setAll("control-button");
        rightButton.setAlignment(Pos.CENTER);
        rightButton.setCursor(Cursor.HAND);

        rightButton.setOnAction(event -> {
            if (currentImage.get() >= this.location.getImages().length) return;

            currentImage.set(currentImage.get() + 1);
            image.setStyle("-fx-background-image: url('" + this.getResource(location.getImages()[currentImage.get()]) + "');");
        });

        carousel.getChildren().setAll(
                leftButton,
                image,
                rightButton
        );

        return carousel;
    }

    private Node body() {
        VBox body = new VBox();
        body.getStyleClass().setAll("body");
        body.setPadding(new Insets(32));
        body.setSpacing(38);

        body.getChildren().setAll(
                this.nameAndRating(),
                this.locationAddress(),
                this.locationDetails(),
                this.locationHistory(),
                this.thingsToDo(),
                this.reviews()
        );

        return body;
    }

    private Node nameAndRating() {
        HBox nameAndRating = new HBox();
        nameAndRating.setAlignment(Pos.CENTER_LEFT);
        nameAndRating.setSpacing(32);

        Label name = new Label(this.location.getName());
        name.getStyleClass().setAll("name");

        HBox ratingContainer = new HBox();
        ratingContainer.setAlignment(Pos.CENTER_LEFT);
        ratingContainer.setSpacing(8);

        int rating = (int) Arrays.stream(this.location.getReviews())
                .mapToInt(Review::getRating)
                .average()
                .orElse(0);

        for (int i = 0; i < rating; i++) {
            ImageView star = new ImageView(new Image(this.getResource("/images/icons/star.png")));
            star.setPreserveRatio(true);
            star.setFitWidth(32);

            ratingContainer.getChildren().add(star);
        }

        Properties store = PersistentStore.loadData();
        MongoCollection<Document> wishlists = this.app.database.getCollection("Wishlists");

        ImageView heartImg = new ImageView();
        Document existingWishlist = wishlists.find(
                new Document()
                        .append("user_id", store.get("ID"))
                        .append("location", this.location.getName())
        ).first();

        if (existingWishlist == null)
            heartImg.setImage(new Image(this.getResource("/images/icons/heart.png")));
        else
            heartImg.setImage(new Image(this.getResource("/images/icons/heart_red.png")));

        heartImg.setPreserveRatio(true);
        heartImg.setFitWidth(48);

        Button wishlistButton = new Button();
        wishlistButton.setBackground(Background.EMPTY);
        wishlistButton.setGraphic(heartImg);
        wishlistButton.setCursor(Cursor.HAND);

        wishlistButton.setOnAction(event -> {
            Document newWishlist = new Document()
                    .append("user_id", store.get("ID"))
                    .append("location", this.location.getName());

            wishlists.insertOne(newWishlist);

            heartImg.setImage(new Image(this.getResource("/images/icons/heart_red.png")));
            wishlistButton.setGraphic(heartImg);
        });

        nameAndRating.getChildren().setAll(name, ratingContainer, wishlistButton);

        return nameAndRating;
    }

    private Node locationAddress() {
        HBox locationContainer = new HBox();
        locationContainer.getStyleClass().setAll("location");
        locationContainer.setSpacing(16);
        locationContainer.setAlignment(Pos.CENTER_LEFT);

        ImageView icon = new ImageView(new Image(this.getResource("/images/icons/location.png")));
        icon.setPreserveRatio(true);
        icon.setFitWidth(24);

        Label address = new Label(this.location.getLocation());

        locationContainer.getChildren().setAll(icon, address);

        return locationContainer;
    }

    private Node locationDetails() {
        Label details = new Label(this.location.getDescription());
        details.setWrapText(true);
        details.setMaxWidth(this.width / 2);

        return details;
    }

    private Node locationHistory() {
        VBox historyContainer = new VBox();
        historyContainer.getStyleClass().setAll("history");
        historyContainer.setPadding(new Insets(32, 0, 0, 0));

        Label title = new Label("History");
        title.getStyleClass().setAll("title");
        title.setPadding(new Insets(0, 0, 24, 0));

        Label history = new Label(this.location.getHistory());
        history.setWrapText(true);
        history.setMaxWidth(this.width / 2);

        historyContainer.getChildren().setAll(title, history);

        return historyContainer;
    }

    private Node thingsToDo() {
        VBox thingsContainer = new VBox();
        thingsContainer.getStyleClass().setAll("things-to-do");
        thingsContainer.setPadding(new Insets(32, 0, 0, 0));

        Label title = new Label("Things To Do");
        title.getStyleClass().setAll("title");
        title.setPadding(new Insets(0, 0, 24, 0));

        HBox toDos = new HBox();
        toDos.getStyleClass().setAll("to-dos");
        toDos.setSpacing(24);

        for (int i = 0; i < this.location.getThingsToDos().length; i++) {
            VBox toDo = new VBox();
            toDo.getStyleClass().setAll("to-do");
            toDo.setMinWidth(150);
            toDo.setMaxWidth(150);
            toDo.setMinHeight(250);
            toDo.setMaxHeight(250);
            toDo.setAlignment(Pos.TOP_CENTER);
            toDo.setPadding(new Insets(8));

            Label toDoTitle = new Label(location.getThingsToDos()[i].getTitle());
            toDoTitle.setWrapText(true);
            toDoTitle.setAlignment(Pos.TOP_CENTER);

            toDo.getChildren().add(toDoTitle);

            toDo.setStyle("-fx-background-image: url('" + this.getResource(location.getThingsToDos()[i].getImage()) + "');");

            toDos.getChildren().add(toDo);
        }

        thingsContainer.getChildren().setAll(
                title,
                toDos
        );

        return thingsContainer;
    }

    private Node reviews() {
        VBox reviewsContainer = new VBox();
        reviewsContainer.getStyleClass().setAll("reviews");
        reviewsContainer.setPadding(new Insets(32, 0, 0, 0));

        Label title = new Label("Reviews");
        title.getStyleClass().setAll("title");
        title.setPadding(new Insets(0, 0, 24, 0));

        reviewsContainer.getChildren().add(title);

        for (int i = 0; i < this.location.getReviews().length; i++) {
            Review review = this.location.getReviews()[i];

            ImageView userImage = new ImageView(new Image(this.getResource(review.getUserImage())));
            userImage.setPreserveRatio(true);
            userImage.setFitWidth(38);

            Label username = new Label(review.getUsername());
            username.getStyleClass().setAll("username");

            HBox userMeta = new HBox();
            userMeta.setSpacing(16);
            userMeta.setAlignment(Pos.CENTER_LEFT);

            userMeta.getChildren().setAll(userImage, username);

            HBox rating = new HBox();
            rating.getStyleClass().setAll("rating");
            rating.setSpacing(12);
            rating.setAlignment(Pos.CENTER_LEFT);

            for (int j = 0; j < review.getRating(); j++) {
                ImageView star = new ImageView(new Image(this.getResource("/images/icons/star.png")));
                star.setPreserveRatio(true);
                star.setFitWidth(24);

                rating.getChildren().add(star);
            }

            Label description = new Label(review.getDescription());
            description.setMaxWidth(this.width / 2.5);
            description.setWrapText(true);

            VBox leftSide = new VBox();
            leftSide.setSpacing(8);
            leftSide.getChildren().setAll(
                    userMeta,
                    rating,
                    description
            );

            HBox reviewImages = new HBox();
            reviewImages.setAlignment(Pos.CENTER);
            reviewImages.setSpacing(18);

            for (int j = 0; j < review.getImages().length; j++) {
                VBox reviewImg = new VBox();
                reviewImg.getStyleClass().setAll("review-img");
                reviewImg.setMinWidth(200);
                reviewImg.setMaxWidth(200);
                reviewImg.setMinHeight(250);
                reviewImg.setMaxHeight(250);

                reviewImg.setStyle("-fx-background-image: url('" + this.getResource(review.getImages()[j]) + "');");
                reviewImages.getChildren().add(reviewImg);
            }

            HBox reviewBox = new HBox();
            reviewBox.setSpacing(32);
            reviewBox.getChildren().setAll(leftSide, reviewImages);

            reviewsContainer.getChildren().add(reviewBox);
        }

        return reviewsContainer;
    }

    public void setLocation(Location location) {
        this.location = location;

        container.getChildren().setAll(
                new Header(this.app),
                this.carousel(),
                this.body(),
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