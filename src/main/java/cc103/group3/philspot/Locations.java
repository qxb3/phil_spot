package cc103.group3.philspot;

import cc103.group3.philspot.lib.Location;
import cc103.group3.philspot.lib.Review;
import cc103.group3.philspot.lib.ThingsToDo;

import java.io.File;
import java.io.IOException;
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

    public Locations() throws IOException {
        File locationsDir = new File(this.getResource("/images/locations").getFile());


        for (File category : Objects.requireNonNull(locationsDir.listFiles())) {
            File categoryDir = new File(this.getResource("/images/locations/" + category.getName()).getFile());

            Location loc = new Location();
            loc.setCategory(category.getName());

            for (File location : Objects.requireNonNull(categoryDir.listFiles())) {
                File locationDir = new File(this.getResource("/images/locations/" + category.getName() + "/" + location.getName()).getFile());

                loc.setName(this.toTitleCase(location.getName()));

                ArrayList<String> locationImages = new ArrayList<>();

                for (File file : Objects.requireNonNull(locationDir.listFiles())) {
                    if (file.getName().equals("location"))
                        loc.setLocation(Files.readString(Path.of(file.getPath())).trim());

                    if (file.getName().equals("description"))
                        loc.setDescription(Files.readString(Path.of(file.getPath())).trim());

                    if (file.getName().equals("history"))
                        loc.setHistory(Files.readString(Path.of(file.getPath())).trim());

                    if (file.getName().endsWith(".png"))
                        locationImages.add("/images/locations/" + category.getName() + "/" + location.getName() + "/" + file.getName());
                }

                loc.setImages(locationImages.toArray(new String[0]));
                loc.setThingsToDos(this.getThingsToDo(category, location));
                loc.setReviews(this.getReviews(category, location));
            }

            this.locations.put(loc.getName(), loc);
        }
    }

    public HashMap<String, Location> getLocations() {
        return this.locations;
    }

    private ThingsToDo[] getThingsToDo(File category, File location) {
        ArrayList<ThingsToDo> thingsToDo = new ArrayList<>();

        File thingsToDoDir = new File(this.getResource("/images/locations/" + category.getName() + "/" + location.getName() + "/things_to_do").getFile());
        for (File thingToDo : Objects.requireNonNull(thingsToDoDir.listFiles())) {
            ThingsToDo toDo = new ThingsToDo();

            toDo.setTitle(this.toTitleCase(thingToDo.getName().replace(".png", "")));
            toDo.setImage("/images/locations/" + category.getName() + "/" + location.getName() + "/things_to_do/" + thingToDo.getName());

            thingsToDo.add(toDo);
        }

        return thingsToDo.toArray(new ThingsToDo[0]);
    }

    private Review[] getReviews(File category, File location) throws IOException {
        ArrayList<Review> reviews = new ArrayList<>();

        File reviewsDir = new File(this.getResource("/images/locations/" + category.getName() + "/" + location.getName() + "/reviews").getFile());
        for (File review : Objects.requireNonNull(reviewsDir.listFiles())) {
            File reviewDir = new File(this.getResource("/images/locations/" + category.getName() + "/" + location.getName() + "/reviews/" + review.getName()).getFile());

            Review rev = new Review();
            ArrayList<String> revImages = new ArrayList<>();

            rev.setUsername(this.toTitleCase(review.getName()));
            rev.setUserImage("/images/locations/" + category.getName() + "/" + location.getName() + "/reviews/" + review.getName() + "/user_profile.png");

            for (File reviewFile : Objects.requireNonNull(reviewDir.listFiles())) {
                if (reviewFile.getName().equals("description"))
                    rev.setDescription(Files.readString(Path.of(reviewFile.getPath())).trim());

                if (reviewFile.getName().equals("rating"))
                    rev.setRating(Integer.parseInt(Files.readString(Path.of(reviewFile.getPath())).trim()));

                if (reviewFile.getName().endsWith(".png") && !reviewFile.getName().equals("user_profile.png"))
                    revImages.add("/images/locations/" + category.getName() + "/" + location.getName() + "/reviews/" + reviewFile.getName());
            }

            rev.setImages(revImages.toArray(new String[0]));
            reviews.add(rev);
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

    private URL getResource(String path) {
        return Objects.requireNonNull(this.getClass().getResource(path));
    }

    public static void main(String[] args) throws IOException {
        Locations locs = new Locations();

        for (Location loc : locs.getLocations().values()) {
            System.out.println(loc);
        }
    }
}