package cc103.group3.philspot;

import cc103.group3.philspot.pages.LandingPage;
import cc103.group3.philspot.pages.LoginPage;
import cc103.group3.philspot.pages.RegisterPage;
import cc103.group3.philspot.pages.auth.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    public Stage primaryStage;
    public Scene LandingPage, LoginPage, RegisterPage,
                 MainPage, SearchPage, LocationPage,
                 WishlistsPage, AboutUsPage, FaQPage;

    public Locations locations;

    public WishlistsPage WishlistsPageInstance;
    public SearchPage SearchPageInstance;
    public LocationPage LocationPageInstance;

    public MongoClient client;
    public MongoDatabase database;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        this.locations = new Locations();

        this.client = MongoClients.create(AppProperties.getProperty("MONGO_URI"));
        this.database = client.getDatabase(AppProperties.getProperty("MONGO_DB_NAME"));

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        this.LandingPage = new LandingPage(this, screenWidth, screenHeight).getScene();
        this.LoginPage = new LoginPage(this, screenWidth, screenHeight).getScene();
        this.RegisterPage = new RegisterPage(this, screenWidth, screenHeight).getScene();
        this.MainPage = new MainPage(this, screenWidth, screenHeight).getScene();
        this.AboutUsPage = new AbousUsPage(this, screenWidth, screenHeight).getScene();
        this.FaQPage = new FaQPage(this, screenWidth, screenHeight).getScene();

        this.SearchPageInstance = new SearchPage(this, screenWidth, screenHeight);
        this.SearchPage = this.SearchPageInstance.getScene();

        this.LocationPageInstance = new LocationPage(this, screenWidth, screenHeight);
        this.LocationPage = this.LocationPageInstance.getScene();

        this.WishlistsPageInstance = new WishlistsPage(this, screenWidth, screenHeight);
        this.WishlistsPage = this.WishlistsPageInstance.getScene();

        primaryStage.setScene(this.handleLogin());
        primaryStage.setTitle("PhilSpot");
        primaryStage.show();
    }

    private Scene handleLogin() {
        Properties store = PersistentStore.loadData();
        String username = store.getProperty("USERNAME");
        String password = store.getProperty("PASSWORD");

        if (username == null || password == null)
            return this.LandingPage;

        MongoCollection<Document> users = this.database.getCollection("Users");
        Document saidUser = users.find(
                new Document()
                        .append("username", username)
                        .append("password", password)
        ).first();

        if (saidUser == null)
            return this.LandingPage;

        return this.MainPage;
    }

    public void switchScreen(Scene scene) {
        this.primaryStage.setScene(scene);
    }
}