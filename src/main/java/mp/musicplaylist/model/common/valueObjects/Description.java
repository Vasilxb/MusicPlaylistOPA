package mp.musicplaylist.model.common.valueObjects;

import jakarta.persistence.Embeddable;
import mp.musicplaylist.model.common.base.ValueObject;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notNull;

@Embeddable
public class Description implements ValueObject {

    private final String value;

    public Description(String value) {
        notNull(value, "Description must not be null");
        matchesPattern(value, "^[A-Za-z0-9 .,'()-]{2,100}$", "description must be 2-100 safe characters");
        this.value = value;
    }

    protected Description() {
        this.value = null;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
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


