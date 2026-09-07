package mp.musicplaylist.model.musicPlaylist;

import jakarta.persistence.*;
import mp.musicplaylist.model.common.base.AbstractEntity;
import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.Song;

import java.util.List;

import static org.apache.commons.lang3.Validate.notNull;

@Entity
public class MusicPlaylist extends AbstractEntity<MusicPlaylistId> {

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "playlist_name"))
    private PlaylistName playlistName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "description"))
    private Description description;

    private String userId;

    @ManyToOne
    private Song songs;

    public MusicPlaylist(PlaylistName playlistName, Description description, String userId, Song songs) {
        super(new MusicPlaylistId());
        notNull(playlistName, "Playlist name cannot be null");
        notNull(description, "Description cannot be null");
        notNull(userId, "User ID cannot be null");
        notNull(songs, "Songs cannot be null");
        this.playlistName = playlistName;
        this.description = description;
        this.userId = userId;
        this.songs = songs;
    }
}
