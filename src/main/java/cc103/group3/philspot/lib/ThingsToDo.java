package cc103.group3.philspot.lib;

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
}
