package cc103.group3.philspot.lib;

import java.util.Arrays;

public class Location {
    private String[] images;
    private String name;
    private String category;
    private String description;
    private String location;
    private String history;
    private ThingsToDo[] thingsToDos;
    private Review[] reviews;

    public String[] getImages() {
        return images;
    }

    public Location setImages(String ...images) {
        this.images = images;
        return this;
    }

    public String getName() {
        return name;
    }

    public Location setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Location setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Location setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Location setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getHistory() {
        return history;
    }

    public Location setHistory(String history) {
        this.history = history;
        return this;
    }

    public ThingsToDo[] getThingsToDos() {
        return this.thingsToDos;
    }

    public Location setThingsToDos(ThingsToDo ...thingsToDos) {
        this.thingsToDos = thingsToDos;
        return this;
    }

    public Review[] getReviews() {
        return this.reviews;
    }

    public Location setReviews(Review ...reviews) {
        this.reviews = reviews;
        return this;
    }

    public static Location builder() {
        return new Location();
    }

    public Location build() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Category: ").append(category).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("History: ").append(history).append("\n");
        sb.append("Images: ").append(Arrays.toString(images)).append("\n");

        if (thingsToDos != null && thingsToDos.length > 0) {
            sb.append("Things to Do: ").append("\n");
            for (ThingsToDo todo : thingsToDos) {
                sb.append("- ").append(todo).append("\n");
            }
        }

        if (reviews != null && reviews.length > 0) {
            sb.append("Reviews: ").append("\n");
            for (Review review : reviews) {
                sb.append(review).append("\n");
            }
        }

        return sb.toString();
    }
}