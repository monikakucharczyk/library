package monika.library.server.data;

public enum Genre {
    Fantasy("Fantasy"),
    Horror("Horror"),
    Romance("Romance"),
    Drama("Drama"),
    Thriller("Thriller"),
    Children("Children"),
    Mystery("Mystery"),
    Historical("Historical"),
    School("School"),
    Other("Other");

    private String title;

    Genre(String title) {
        this.title = title;
    }

    public static Genre fromString(String genre) {
        switch (genre.toLowerCase().trim()) {
            case "fantasy": return Genre.Fantasy;
            case "horror": return Genre.Horror;
            case "romance": return Genre.Romance;
            case "drama": return Genre.Drama;
            case "thriller": return Genre.Thriller;
            case "children": return Genre.Children;
            case "mystery": return Genre.Mystery;
            case "historical": return Genre.Historical;
            case "school": return Genre.School;
            default: return Genre.Other;
        }
    }

    @Override
    public String toString() {
        return this.title;
    }
}
