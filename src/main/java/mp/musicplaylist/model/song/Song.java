package mp.musicplaylist.model.song;

import jakarta.persistence.*;
import mp.musicplaylist.model.common.base.AbstractEntity;
import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;


@Entity
public class Song extends AbstractEntity<SongId> {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "title"))
    private Title title;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "artist"))
    private Artist artist;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "album"))
    private Album album;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "year"))
    private Year year;
}