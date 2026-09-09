package mp.musicplaylist.model.musicPlaylist.service;

import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.musicPlaylist.MusicPlaylist;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.ids.SongId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MusicPlaylistService {
    MusicPlaylist createMusicPlaylist(PlaylistName playlistName, Description description, String userId, SongId songId);
    MusicPlaylist updateMusicPlaylist(MusicPlaylistId musicPlaylistId, PlaylistName playlistName, Description description, String userId, SongId songId);
    void deleteMusicPlaylist(MusicPlaylistId musicPlaylistId);
    MusicPlaylist findMusicPlaylistById(MusicPlaylistId musicPlaylistId);
    List<MusicPlaylist> findMusicPlaylistByName(String musicPlaylistName);
    List<MusicPlaylist> listMusicPlaylists();
}
