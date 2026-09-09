package mp.musicplaylist.model.song;

import jakarta.persistence.*;
import mp.musicplaylist.model.common.base.AbstractEntity;
import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;

import static org.apache.commons.lang3.Validate.notNull;


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
    @AttributeOverride(name = "value", column = @Column(name = "release_year"))
    private Year year;

    public Song(Title title, Artist artist, Album album, Year year) {
        super(new SongId());
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    protected Song() {
        super();
    }

    public Title Title() {
        return title;
    }

    public Artist Artist() {
        return artist;
    }

    public Album Album() {
        return album;
    }

    public Year Year() {
        return year;
    }

    public void updateSong(Title title, Artist artist, Album album, Year year) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public void updateTitle(Title title) {
        notNull(title, "Title cannot be null");
        this.title = title;
        this.checkInvariants();
    }

    public void updateArtist(Artist artist) {
        notNull(artist, "Artist cannot be null");
        this.artist = artist;
        this.checkInvariants();
    }

    public void updateAlbum(Album album) {
        notNull(album, "Album cannot be null");
        this.album = album;
        this.checkInvariants();
    }

    public void updateYear(Year year) {
        notNull(year, "Year cannot be null");
        this.year = year;
        this.checkInvariants();
    }

    public void clearSong() {
        this.title = null;
        this.artist = null;
        this.album = null;
        this.year = null;
    }

    public void clearTitle() {
        this.title = null;
    }

    public void clearArtist() {
        this.artist = null;
    }

    public void clearAlbum() {
        this.album = null;
    }

    public void clearYear() {
        this.year = null;
    }

    private void checkInvariants() {
        notNull(title, "Title cannot be null");
        notNull(artist, "Artist cannot be null");
        notNull(album, "Album cannot be null");
        notNull(year, "Year cannot be null");
    }
}