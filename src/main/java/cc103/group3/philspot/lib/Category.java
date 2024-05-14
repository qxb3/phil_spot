package cc103.group3.philspot.lib;

public class Category {
    private String image;
    private String text;

    public Category(String image, String text) {
        this.image = image;
        this.text = text;
    }

    public String getImage() {
        return this.image;
    }

    public String getText() {
        return this.text;
    }
}
