package io.baselogic.batch.partition.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 * Movie
 *
 * title,release_date,tagline
 * Avatar,12/10/09,Enter the World of Pandora.
 */
@Getter
@Setter
@ToString
@SuppressWarnings("restriction")
@XmlRootElement(name = "movie")
public class Movie {

    private String title;
    private LocalDate releaseDate;
    private String tagLine;

} // The End...
