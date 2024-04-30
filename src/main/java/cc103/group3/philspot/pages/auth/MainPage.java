package cc103.group3.philspot.pages.auth;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.lib.ThingsToDo;
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

import java.util.Objects;

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

        container.getChildren().add(new Header(this.app));
        container.getChildren().add(this.body());
        container.getChildren().add(new Footer());

        this.scene.getStylesheets().add(this.getResource("/css/main_page.css"));
    }

    private Node body() {
        VBox body = new VBox();
        body.getStyleClass().setAll("body");
        body.setAlignment(Pos.CENTER);
        body.setPadding(new Insets(64));

        TextField findDestinations = new TextField();
        findDestinations.getStyleClass().setAll("destinations");
        findDestinations.setPromptText("Find destinations");
        findDestinations.setMaxWidth(600);
        findDestinations.setCursor(Cursor.TEXT);

        body.getChildren().setAll(
                findDestinations,
                this.categories(),
                this.locations()
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

        return category;
    }

    private Node locations() {
        Location[] locationsDb = {
                Location.builder()
                        .setImages(
                                this.getResource("/images/locations/mayon_volcano/1.png"),
                                this.getResource("/images/locations/mayon_volcano/2.png"),
                                this.getResource("/images/locations/mayon_volcano/3.png")
                        )
                        .setName("Mayon Volcano")
                        .setDescription("Mayon Volcano, active volcano, southeastern Luzon, Philippines, dominating the city of Legaspi. Called the world's most perfect volcanic cone because of the symmetry of its shape, it has a base 80 miles (130 km) in circumference and rises to 8,077 feet (2,462 metres) from the shores of Albay Gulf.")
                        .setLocation("Mayon Volcano, Legazpi City, Albay, Philippines")
                        .setHistory(
                                "Mayon Volcano, renowned for its near-perfect cone shape, has a rich history intertwined with the cultural and geological narrative of the Philippines. Here's a brief overview:\n\n" +
                                "Geological Origins: Mayon is classified as a stratovolcano, characterized by its steep sides and periodic eruptions. It is part of the Pacific Ring of Fire, a region known for its intense volcanic and seismic activity due to the movement of tectonic plates. The volcano's formation is primarily attributed to the subduction of the Philippine Sea Plate beneath the Eurasian Plate.\n" +
                                "Indigenous Beliefs: Before the Spanish colonization of the Philippines, the indigenous peoples living around Mayon considered the volcano sacred and attributed its formation to various myths and legends. One legend tells of the tragic love story of Daragang Magayon (the Beautiful Lady) and Panganoron (the Brave One), whose spirits are said to inhabit the volcano.\n" +
                                "Historical Eruptions: Mayon has a long history of eruptions, with recorded activity dating back to the Spanish colonial period. Some notable eruptions include those in 1616, 1766, 1814, and 1897. The 1814 eruption, in particular, was one of the most destructive in Mayon's history, burying the town of Cagsawa and killing thousands of people. Today, the ruins of the Cagsawa Church remain as a haunting reminder of the volcano's power.\n" +
                                "Modern Activity: In recent history, Mayon has continued to be active, with eruptions occurring periodically. The volcano's activity often leads to evacuations of nearby communities and disruption of daily life in the region. Despite the risks posed by its eruptions, Mayon's scenic beauty continues to attract tourists and researchers interested in studying its geological phenomena.\n\n" +
                                "Mayon Volcano stands not only as a geological wonder but also as a symbol of the resilience of the Filipino people, who continue to live in its shadow despite the inherent risks posed by its eruptions."
                        )
                        .setThingsToDos(
                                ThingsToDo.builder()
                                        .setTitle("Atv Riding")
                                        .setImage(this.getResource("/images/locations/mayon_volcano/things_to_do/atv_riding.png")),
                                ThingsToDo.builder()
                                        .setTitle("Mountain Climbing")
                                        .setImage(this.getResource("/images/locations/mayon_volcano/things_to_do/mountain_climbing.png")),
                                ThingsToDo.builder()
                                        .setTitle("Zipline")
                                        .setImage(this.getResource("/images/locations/mayon_volcano/things_to_do/zipline.png"))
                        )
                        .setReviews(
                                Review.builder()
                                        .setUserImage(this.getResource("/images/locations/mayon_volcano/reviews/ashley/user_profile.png"))
                                        .setUsername("Ashley")
                                        .setDescription("Fantastic experience from start to finish. Can't wait to return!")
                                        .setRating(5)
                                        .setImages(
                                                this.getResource("/images/locations/mayon_volcano/reviews/ashley/1.png"),
                                                this.getResource("/images/locations/mayon_volcano/reviews/ashley/2.png")
                                        )
                                        .build()
                        )
                        .build(),
                Location.builder()
                        .setImages(
                                this.getResource("/images/locations/coron_palawan/1.png"),
                                this.getResource("/images/locations/coron_palawan/2.png"),
                                this.getResource("/images/locations/coron_palawan/3.png"),
                                this.getResource("/images/locations/coron_palawan/4.png")
                        )
                        .setName("Coron")
                        .setDescription("Coron, Palawan is a captivating destination located on the island of Coron in the Philippines. It is renowned for its breathtaking natural beauty and rich historical significance. Coron is situated on Busuanga Island, part of the Calamian Islands group in Palawan province. The town is surrounded by pristine beaches, crystal-clear waters, and towering limestone cliffs. It has a tropical climate with distinct dry and wet seasons.")
                        .setLocation("Coron, Palawan, Philippines")
                        .setHistory(
                                "Coron has a history dating back thousands of years, initially inhabited by indigenous tribes such as the Tagbanua and Batak people. During World War II, it was a strategic location for both American and Japanese forces, leading to intense battles in the area."
                        )
                        .setThingsToDos(
                                ThingsToDo.builder()
                                        .setTitle("Diving")
                                        .setImage(this.getResource("/images/locations/coron_palawan/things_to_do/diving.png")),
                                ThingsToDo.builder()
                                        .setTitle("Fishing")
                                        .setImage(this.getResource("/images/locations/coron_palawan/things_to_do/fishing.png")),
                                ThingsToDo.builder()
                                        .setTitle("Island Hopping")
                                        .setImage(this.getResource("/images/locations/coron_palawan/things_to_do/island_hopping.png"))
                        )
                        .setReviews(
                                Review.builder()
                                        .setUserImage(this.getResource("/images/locations/coron_palawan/reviews/robin/user_profile.png"))
                                        .setUsername("Ashley")
                                        .setDescription("A hidden gem! The flavors were exquisite and left us wanting more.")
                                        .setRating(5)
                                        .setImages(
                                                this.getResource("/images/locations/coron_palawan/reviews/robin/1.png"),
                                                this.getResource("/images/locations/coron_palawan/reviews/robin/2.png"),
                                                this.getResource("/images/locations/coron_palawan/reviews/robin/3.png")
                                        )
                                        .build()
                        )
                        .build()
        };

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

        for (int i = 0; i < locationsDb.length; i++) {
            Button location = createLocation(locationsDb[i]);
            locationsRow1.getChildren().add(location);
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

        button.setStyle("-fx-background-image: url('" + location.getImages()[0] + "');");

        return button;
    }


    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }

    public Scene getScene() {
        return scene;
    }
}
