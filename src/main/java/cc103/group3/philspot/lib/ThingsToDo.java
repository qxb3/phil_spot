package cc103.group3.philspot.lib;

import java.util.Arrays;

public class ThingsToDo {
    private String title;
    private String image;

    public String getTitle() {
        return title;
    }

    public ThingsToDo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImage() {
        return image;
    }

    public ThingsToDo setImage(String image) {
        this.image = image;
        return this;
    }

    public static ThingsToDo builder() {
        return new ThingsToDo();
    }

    public ThingsToDo build() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(title).append("\n");
        sb.append("Image: ").append(image).append("\n");

        return sb.toString();
    }
}
