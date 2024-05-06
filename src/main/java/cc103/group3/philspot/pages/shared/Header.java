package cc103.group3.philspot.pages.shared;

import cc103.group3.philspot.Main;
import cc103.group3.philspot.PersistentStore;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.util.Objects;
import java.util.Properties;

public class Header extends HBox {
    private final Main app;

    private Label username = new Label();

    public Header(Main app) {
        super();

        this.app = app;

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

        Popup profilePopup = new Popup();
        profilePopup.getContent().setAll(this.profilePopupContent(profilePopup));
        profilePopup.setAutoHide(true);

        profileButton.setOnAction(event -> {
            double buttonX = profileButton.localToScene(profileButton.getBoundsInLocal()).getMinX();
            double buttonY = profileButton.localToScene(profileButton.getBoundsInLocal()).getMinY();


            Properties store = PersistentStore.loadData();
            this.username.setText(store.getProperty("USERNAME"));

            profilePopup.show(app.primaryStage, buttonX, buttonY + profileButton.getHeight());
        });

        icons.getChildren().setAll(heartButton, profileButton);

        this.getChildren().setAll(
                logoButton,
                icons
        );

        this.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(this.getClass().getResource("/css/shared.css")).toExternalForm()));
        this.getStyleClass().setAll("header");
    }

    private Node profilePopupContent(Popup popup) {
        VBox profilePopupContent = new VBox();
        profilePopupContent.setMinWidth(250);
        profilePopupContent.setPadding(new Insets(24));
        profilePopupContent.setStyle(
                "-fx-background-color: rgba(40, 79, 132, 0.4);" +
                "-fx-background-radius: 8px;" +
                "-fx-background-insets: 0 16px;"
        );

        ImageView profile = new ImageView(new Image(this.getResource("/images/icons/user_profile.png")));
        profile.setPreserveRatio(true);
        profile.setFitWidth(50);

        username.setStyle(
                "-fx-text-fill: #fff;" +
                "-fx-font-size: 24px;"
        );

        HBox metaContainer = new HBox();
        metaContainer.setAlignment(Pos.CENTER_LEFT);
        metaContainer.setSpacing(8);
        metaContainer.getChildren().setAll(profile, username);

        Button aboutUsButton = new Button("About Us");
        aboutUsButton.setBackground(Background.EMPTY);
        aboutUsButton.setCursor(Cursor.HAND);
        aboutUsButton.setStyle(
                "-fx-text-fill: #fff;" +
                "-fx-font-size: 18px;"
        );

        aboutUsButton.setOnAction(event -> {
            popup.hide();

            this.app.switchScreen(this.app.AboutUsPage);
        });

        Button faqButton = new Button("FaQ");
        faqButton.setBackground(Background.EMPTY);
        faqButton.setCursor(Cursor.HAND);
        faqButton.setStyle(
                "-fx-text-fill: #fff;" +
                "-fx-font-size: 18px;"
        );

        Button logoutButton = new Button("Logout");
        logoutButton.setBackground(Background.EMPTY);
        logoutButton.setCursor(Cursor.HAND);
        logoutButton.setStyle(
                "-fx-text-fill: #fff;" +
                "-fx-font-size: 18px;"
        );

        logoutButton.setOnAction(event -> {
            popup.hide();

            PersistentStore.saveData("");
            this.app.switchScreen(this.app.LandingPage);
        });

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER_LEFT);
        buttons.setSpacing(4);
        buttons.setPadding(new Insets(8, 0, 0, 0));
        buttons.getChildren().setAll(
                aboutUsButton,
                faqButton,
                logoutButton
        );

        profilePopupContent.getChildren().setAll(
                metaContainer,
                buttons
        );

        return profilePopupContent;
    }

    private String getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path)).toExternalForm();
    }
}
