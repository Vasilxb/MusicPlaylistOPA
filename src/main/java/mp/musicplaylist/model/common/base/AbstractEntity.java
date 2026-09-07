package mp.musicplaylist.model.common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import org.springframework.data.util.ProxyUtils;

import java.time.ZonedDateTime;

import static org.apache.commons.lang3.Validate.notNull;

@MappedSuperclass
@Getter
public class AbstractEntity<ID extends DomainObjectId> {

    @EmbeddedId
    private final ID id;

    @Version
    private Long version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    protected AbstractEntity(ID id) {
        this.id = notNull(id,"id must not be null");
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    protected AbstractEntity(){
        this.id = null;
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    protected void updateTimestamp() {
        this.updatedAt = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().equals(ProxyUtils.getUserClass(obj))) return false;
        var other = (AbstractEntity<?>) obj;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : id.hashCode();
    }
}

