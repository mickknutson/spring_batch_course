package io.baselogic.batch.partition.processors;

import io.baselogic.batch.partition.domain.Movie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Mapping:
 * title,release_date,tagline
 * Avatar,12/10/09,Enter the World of Pandora.
 */
@Slf4j
public class MovieFieldSetMapper implements FieldSetMapper<Movie> {

    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yy");
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
            .withLocale(Locale.ENGLISH);

    public Movie mapFieldSet(FieldSet fieldSet) throws BindException {

        Movie movie = new Movie();

        movie.setTitle(fieldSet.readString("title"));
        movie.setTagLine(fieldSet.readString("tagline"));

        // Converting the date
        String dateString = fieldSet.readString("release_date");

        try {
            movie.setReleaseDate(LocalDate.parse(dateString, formatter));
        } catch (DateTimeParseException e) {
            log.error("DateTimeParseException: {}", e.getMessage());
            log.debug("\t--> Movie Error: {}, dateString: [{}]", movie.toString(), dateString);
        }

        return movie;

    }

} // The End...