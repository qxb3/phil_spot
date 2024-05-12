package cc103.group3.philspot;

import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.lib.ThingsToDo;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Locations {
    private HashMap<String, Location> locations = new HashMap<>();
    private static final String LOCATIONS_PATH = "images/locations";

    public Locations() throws IOException {
        BufferedReader locationsReader = this.getResourceAsStream(LOCATIONS_PATH);

        String category;
        while ((category = locationsReader.readLine()) != null) {
            BufferedReader locationReader = this.getResourceAsStream(LOCATIONS_PATH + "/" + category);

            String locationName, locationAddress = null, locationDescription = null, locationHistory = null;
            ArrayList<String> locationImages = new ArrayList<>();

            while ((locationName = locationReader.readLine()) != null) {
                BufferedReader locationMetaReader = this.getResourceAsStream(LOCATIONS_PATH + "/" + category + "/" + locationName);

                String meta;
                while ((meta = locationMetaReader.readLine()) != null) {
                    File file = new File(this.getResource("/" + LOCATIONS_PATH + "/" + category + "/" + locationName + "/" + meta).getFile());

                    if (meta.equals("location"))
                        locationAddress = Files.readString(Path.of(file.getPath())).trim();

                    if (meta.equals("description"))
                        locationDescription = Files.readString(Path.of(file.getPath())).trim();

                    if (meta.equals("history"))
                        locationHistory = Files.readString(Path.of(file.getPath())).trim();

                    if (meta.endsWith(".png"))
                        locationImages.add("/" + LOCATIONS_PATH + "/" + category + "/" + locationName + "/" + meta);
                }

                Location location = new Location()
                        .setCategory(category)
                        .setName(locationName)
                        .setLocation(locationAddress)
                        .setDescription(locationDescription)
                        .setHistory(locationHistory)
                        .setImages(locationImages.toArray(new String[0]))
                        .setThingsToDos(this.getThingsToDo(category, locationName))
                        .setReviews(this.getReviews(category, locationName));

                this.locations.put(location.getName(), location);
            }
        }
    }

    public HashMap<String, Location> getLocations() {
        return this.locations;
    }

    private ThingsToDo[] getThingsToDo(String category, String location) throws IOException {
        ArrayList<ThingsToDo> thingsToDo = new ArrayList<>();

        BufferedReader thingsToDoReader = this.getResourceAsStream(LOCATIONS_PATH + "/" + category + "/" + location + "/things_to_do");

        String thingToDo;
        while ((thingToDo = thingsToDoReader.readLine()) != null) {
            String title = this.toTitleCase(thingToDo.replace(".png", ""));
            String image = "/" + LOCATIONS_PATH + "/" + category + "/" + location + "/things_to_do/" + thingToDo;

            thingsToDo.add(
                    new ThingsToDo()
                            .setTitle(title)
                            .setImage(image)
            );
        }

        return thingsToDo.toArray(new ThingsToDo[0]);
    }

    private Review[] getReviews(String category, String location) throws IOException {
        ArrayList<Review> reviews = new ArrayList<>();

        BufferedReader reviewsReader = this.getResourceAsStream(LOCATIONS_PATH + "/" + category + "/" + location + "/reviews");

        String username;

        while ((username = reviewsReader.readLine()) != null) {
            BufferedReader reviewReader = this.getResourceAsStream(LOCATIONS_PATH + "/" + category + "/" + location + "/reviews/" + username);

            String meta, description = null;
            int rating = 0;
            ArrayList<String> reviewImages = new ArrayList<>();

            while ((meta = reviewReader.readLine()) != null) {
                File file = new File(this.getResource("/" + LOCATIONS_PATH + "/" + category + "/" + location + "/reviews/" + username + "/" + meta).getFile());

                if (meta.equals("description"))
                    description = Files.readString(Path.of(file.getPath())).trim();

                if (meta.equals("rating"))
                    rating = Integer.parseInt(Files.readString(Path.of(file.getPath())).trim());

                if (meta.endsWith(".png") && !meta.equals("user_profile.png"))
                    reviewImages.add("/" + LOCATIONS_PATH + "/" + category + "/" + location + "/reviews/" + username + "/" + meta);
            }

            reviews.add(
                    new Review()
                            .setUsername(username)
                            .setUserImage("/" + LOCATIONS_PATH + "/" + category + "/" + location + "/reviews/" + username + "/user_profile.png")
                            .setDescription(description)
                            .setRating(rating)
                            .setImages(reviewImages.toArray(new String[0]))
            );
        }

        return reviews.toArray(new Review[0]);
    }

    private String toTitleCase(String input) {
        String stringWithSpaces = input.replace("_", " ");

        StringBuilder result = new StringBuilder();
        Matcher matcher = Pattern.compile("\\b\\w").matcher(stringWithSpaces);
        while (matcher.find()) {
            matcher.appendReplacement(result, matcher.group().toUpperCase());
        }
        matcher.appendTail(result);

        return result.toString();
    }

    private BufferedReader getResourceAsStream(String path) {
        InputStream stream = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        return new BufferedReader(new InputStreamReader(stream));
    }

    private URL getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path));
    }

    public static void main(String[] args) throws IOException {
        Locations locations = new Locations();

        System.out.println(locations.getLocations().size());
    }
}