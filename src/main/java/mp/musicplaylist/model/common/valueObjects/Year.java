package mp.musicplaylist.model.common.valueObjects;

import jakarta.persistence.Embeddable;
import mp.musicplaylist.model.common.base.ValueObject;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notNull;

@Embeddable
public class Year implements ValueObject {
    private final String value;

    public Year(String year){
        notNull(year, "year must not be null");
        matchesPattern(year, "^\\d{4}$",
                "year must be in format YYYY (e.g., 2002)");
        this.value = year;
    }

    protected Year(){
        this.value = null;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Year year1 = (Year) o;
        return Objects.equals(value, year1.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Year{'" + value + '\'' + '}';
    }
}
