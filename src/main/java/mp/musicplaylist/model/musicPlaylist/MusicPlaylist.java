package mp.musicplaylist.model.musicPlaylist;

import jakarta.persistence.*;
import mp.musicplaylist.model.common.base.AbstractEntity;
import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.Song;

import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.validState;

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

    protected MusicPlaylist() {
        super();
    }

    private void checkInvariants() {
        notNull(playlistName, "Playlist name cannot be null");
        notNull(description, "Description cannot be null");
        notNull(userId, "User ID cannot be null");
        notNull(songs, "Songs cannot be null");
    }

    public PlaylistName PlaylistName() {
        return playlistName;
    }

    public Description Description() {
        return description;
    }

    public String UserId() {
        return userId;
    }

    public Song Songs() {
        return songs;
    }

    public void updateMusicPlaylist(PlaylistName playlistName, Description description, String userId, Song songs) {
        notNull(playlistName, "Playlist name cannot be null");
        notNull(description, "Description cannot be null");
        notNull(userId, "User ID cannot be null");
        notNull(songs, "Songs cannot be null");
        this.playlistName = playlistName;
        this.description = description;
        this.userId = userId;
        this.songs = songs;
        checkInvariants();
    }

    public void updatePlaylistName(PlaylistName playlistName) {
        notNull(playlistName, "Playlist name cannot be null");
        this.playlistName = playlistName;
        checkInvariants();
    }

    public void updateDescription(Description description) {
        notNull(description, "Description cannot be null");
        this.description = description;
        checkInvariants();
    }

    public void updateUserId(String userId) {
        notNull(userId, "User ID cannot be null");
        this.userId = userId;
        checkInvariants();
    }

    public void updateSongs(Song songs) {
        notNull(songs, "Songs cannot be null");
        this.songs = songs;
        checkInvariants();
    }

    public void clearMusicPlaylist() {
        this.playlistName = null;
        this.description = null;
        this.userId = null;
        this.songs = null;
        checkInvariants();
    }

    public void clearPlaylistName() {
        this.playlistName = null;
        checkInvariants();
    }

    public void clearDescription() {
        this.description = null;
        checkInvariants();
    }

    public void clearUserId() {
        this.userId = null;
        checkInvariants();
    }

    public void clearSongs() {
        this.songs = null;
        checkInvariants();
    }

    public static class Builder {
        private MusicPlaylist musicPlaylist;

        public Builder(PlaylistName playlistName, Description description, String userId, Song songs) {
            musicPlaylist = new MusicPlaylist(playlistName, description, userId, songs);
        }

        public Builder withPlaylistName(PlaylistName playlistName) {
            musicPlaylist.updatePlaylistName(playlistName);
            return this;
        }

        public Builder withDescription(Description description) {
            musicPlaylist.updateDescription(description);
            return this;
        }

        public Builder withUserId(String userId) {
            musicPlaylist.updateUserId(userId);
            return this;
        }

        public Builder withSongs(Song songs) {
            musicPlaylist.updateSongs(songs);
            return this;
        }

        public MusicPlaylist build() {
            validState(musicPlaylist != null, "MusicPlaylist must not be null");
            musicPlaylist.checkInvariants();
            MusicPlaylist result = musicPlaylist;
            musicPlaylist = null;
            return result;
        }
    }
}
