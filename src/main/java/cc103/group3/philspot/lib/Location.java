package cc103.group3.philspot.lib;

public class Location {
    private String[] images;
    private String name;
    private String description;
    private String location;
    private String history;
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
}