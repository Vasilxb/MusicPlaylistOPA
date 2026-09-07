package mp.musicplaylist.model.common.base;

import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;


import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


import static org.apache.commons.lang3.Validate.matchesPattern;
import static org.apache.commons.lang3.Validate.notNull;


@MappedSuperclass
@Embeddable
public class DomainObjectId implements Serializable {
    private String id;

    protected DomainObjectId(String uuid) {
        notNull(uuid, "uuid must not be null");
        matchesPattern(uuid,
                "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$",
                "The UUID must be in a valid format");
        this.id = uuid;
    }

    protected DomainObjectId() {
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DomainObjectId that = (DomainObjectId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
