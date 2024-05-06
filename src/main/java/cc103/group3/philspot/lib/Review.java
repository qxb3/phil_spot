package cc103.group3.philspot.lib;

import java.util.Arrays;

public class Review {
    private String userImage;
    private String username;
    private String description;
    private int rating;
    private String[] images;

    public String getUserImage() {
        return userImage;
    }

    public Review setUserImage(String userImage) {
        this.userImage = userImage;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Review setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Review setDescription(String description) {
        this.description = description;
        return this;
    }

    public int getRating() {
        return rating;
    }

    public Review setRating(int rating) {
        this.rating = rating;
        return this;
    }

    public String[] getImages() {
        return images;
    }

    public Review setImages(String ...images) {
        this.images = images;
        return this;
    }

    public static Review builder() {
        return new Review();
    }

    public Review build() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Image: ").append(userImage).append("\n");
        sb.append("User: ").append(username).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Rating: ").append(rating).append("\n");
        sb.append("Images: ").append(Arrays.toString(images)).append("\n");

        return sb.toString();
    }
}