package escapeRoom.AssetsArea_Classes.Classes_testing;

import java.util.Objects;

public class Game {
    private final String title;
    private final String genre;
    private final int releaseYear;
    private final double price;

    public Game(String title, String genre, int releaseYear, double price) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return releaseYear == game.releaseYear &&
                Double.compare(game.price, price) == 0 &&
                title.equals(game.title) &&
                genre.equals(game.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, releaseYear, price);
    }
}
