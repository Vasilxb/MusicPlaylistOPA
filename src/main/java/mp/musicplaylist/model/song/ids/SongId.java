package mp.musicplaylist.model.song.ids;

import mp.musicplaylist.model.common.base.DomainObjectId;

public class SongId extends DomainObjectId {
    public SongId(String id) {
        super(id);
    }
    public SongId(){
        super();
    }
}
