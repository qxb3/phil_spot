package cc103.group3.philspot.pages.shared;

import cc103.group3.philspot.Main;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.Objects;

public class Header extends HBox {
    public Header(Main app) {
        super();

        ImageView logoImage = new ImageView(new Image(getResource("/images/philspot_logo.png")));
        logoImage.setPreserveRatio(true);
        logoImage.setFitWidth(148);

        Button logoButton = new Button();
        logoButton.setBackground(Background.EMPTY);
        logoButton.setGraphic(logoImage);
        logoButton.setCursor(Cursor.HAND);
        logoButton.setOnAction(event -> {
            app.switchScreen(app.MainPage);
        });

        HBox icons = new HBox();
        HBox.setHgrow(icons, Priority.ALWAYS);
        icons.setAlignment(Pos.CENTER_RIGHT);
        icons.setSpacing(16);

        ImageView heartImage = new ImageView(new Image(getResource("/images/icons/heart.png")));
        heartImage.setPreserveRatio(true);
        heartImage.setFitWidth(50);

        Button heartButton = new Button();
        heartButton.setBackground(Background.EMPTY);
        heartButton.setGraphic(heartImage);
        heartButton.setCursor(Cursor.HAND);
        heartButton.setOnAction(event -> {
        });

        ImageView profileImage = new ImageView(new Image(getResource("/images/icons/user.png")));
        profileImage.setPreserveRatio(true);
        profileImage.setFitWidth(50);

        Button profileButton = new Button();
        profileButton.setBackground(Background.EMPTY);
        profileButton.setGraphic(profileImage);
        profileButton.setCursor(Cursor.HAND);
        profileButton.setOnAction(event -> {
        });

        icons.getChildren().setAll(heartButton, profileButton);

        this.getChildren().setAll(
                logoButton,
                icons
        );

        this.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(this.getClass().getResource("/css/shared.css")).toExternalForm()));
        this.getStyleClass().setAll("header");
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }
}
