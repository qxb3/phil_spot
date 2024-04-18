package cc103.group3.philspot.pages.shared;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class Footer extends VBox {
    public Footer() {
        super();

        ImageView logoImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/images/philspot_logo.png")).toExternalForm(), true));

        HBox socialsContainer = new HBox();
        socialsContainer.setAlignment(Pos.CENTER);
        socialsContainer.setPadding(new Insets(16, 0, 0, 0));
        socialsContainer.setSpacing(16);

        ImageView twitterImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/images/twitter.png")).toExternalForm(), true));
        twitterImage.setFitWidth(54);
        twitterImage.setFitHeight(54);
        twitterImage.setPreserveRatio(true);

        ImageView facebookImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/images/facebook.png")).toExternalForm(), true));
        facebookImage.setFitWidth(54);
        facebookImage.setFitHeight(54);
        facebookImage.setPreserveRatio(true);

        ImageView instagramImage = new ImageView(new Image(Objects.requireNonNull(this.getClass().getResource("/images/instagram.png")).toExternalForm(), true));
        instagramImage.setFitWidth(64);
        instagramImage.setFitHeight(64);
        instagramImage.setPreserveRatio(true);

        socialsContainer.getChildren().setAll(
                twitterImage,
                facebookImage,
                instagramImage
        );

        this.getStylesheets().add(Objects.requireNonNull(Objects.requireNonNull(this.getClass().getResource("/css/shared.css")).toExternalForm()));
        this.getStyleClass().setAll("footer");
        this.setPadding(new Insets(64));
        this.setAlignment(Pos.CENTER);
        this.getChildren().setAll(
                logoImage,
                socialsContainer
        );
    }
}
