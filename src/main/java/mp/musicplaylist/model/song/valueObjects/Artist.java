package mp.musicplaylist.model.song.valueObjects;

import jakarta.persistence.Embeddable;
import mp.musicplaylist.model.common.base.ValueObject;
import mp.musicplaylist.model.common.valueObjects.Description;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notNull;

@Embeddable
public class Artist implements ValueObject {
    private final String value;

    public Artist(String value) {
        notNull(value, "Artist must not be null");
        matchesPattern(value, "^[A-Za-z0-9 .,'()-]{2,100}$", "artist must be 2-100 safe characters");
        this.value = value;
    }

    protected Artist() {
        this.value = null;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist that = (Artist) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
