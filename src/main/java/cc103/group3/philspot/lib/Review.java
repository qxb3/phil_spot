package cc103.group3.philspot.lib;

public class Review {
    private String userImage;
    private String username;
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
}