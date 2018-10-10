package io.baselogic.batch.partition.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Movie
 *
 * title,release_date,tagline
 * Avatar,12/10/09,Enter the World of Pandora.
 */
@SuppressWarnings("restriction")
@XmlRootElement(name = "movie")
public class Movie {

    private String title;
    private LocalDate releaseDate;
    private String tagLine;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", tagLine='" + tagLine + '\'' +
                '}';
    }
} // The End...
