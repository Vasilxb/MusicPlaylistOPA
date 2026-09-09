package mp.musicplaylist.model.musicPlaylist.service.impl;

import jakarta.persistence.EntityNotFoundException;
import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.musicPlaylist.MusicPlaylist;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import mp.musicplaylist.model.musicPlaylist.repository.MusicPlaylistRepository;
import mp.musicplaylist.model.musicPlaylist.service.MusicPlaylistService;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicPlaylistServiceImpl implements MusicPlaylistService {
    private final MusicPlaylistRepository musicPlaylistRepository;
    private final SongService songService;

    public MusicPlaylistServiceImpl(MusicPlaylistRepository musicPlaylistRepository, SongService songService) {
        this.musicPlaylistRepository = musicPlaylistRepository;
        this.songService = songService;
    }

    @Override
    public MusicPlaylist findMusicPlaylistById(MusicPlaylistId musicPlaylistId) {
        return musicPlaylistRepository.findById(musicPlaylistId).orElseThrow(() -> new EntityNotFoundException("MusicPlaylist not found with id: " + musicPlaylistId));
    }

    @Override
    public List<MusicPlaylist> findMusicPlaylistByName(String musicPlaylistName) {
        String needle = musicPlaylistName == null ? "" : musicPlaylistName.trim();
        if (needle.isEmpty()) {
            return musicPlaylistRepository.findAll();
        }
        return musicPlaylistRepository.findAll().stream()
                .filter(playlist -> playlist.PlaylistName() != null
                        && playlist.PlaylistName().value() != null
                        && playlist.PlaylistName().value().toLowerCase().contains(needle.toLowerCase()))
                .toList();
    }

    @Override
    public MusicPlaylist createMusicPlaylist(PlaylistName playlistName, Description description, String userId, SongId songId) {
        Song song = songService.findSongById(songId);
        MusicPlaylist musicPlaylist = new MusicPlaylist.Builder(playlistName, description, userId, song)
                .withPlaylistName(playlistName)
                .withDescription(description)
                .withUserId(userId)
                .withSongs(song)
                .build();
        return musicPlaylistRepository.save(musicPlaylist);
    }

    @Override
    public MusicPlaylist updateMusicPlaylist(MusicPlaylistId musicPlaylistId, PlaylistName playlistName, Description description, String userId, SongId songId) {
        MusicPlaylist musicPlaylist = findMusicPlaylistById(musicPlaylistId);
        Song song = songService.findSongById(songId);
        musicPlaylist.updatePlaylistName(playlistName);
        musicPlaylist.updateDescription(description);
        musicPlaylist.updateUserId(userId);
        musicPlaylist.updateSongs(song);
        return musicPlaylistRepository.save(musicPlaylist);
    }

    @Override
    public void deleteMusicPlaylist(MusicPlaylistId musicPlaylistId) {
        musicPlaylistRepository.deleteById(musicPlaylistId);
    }


    @Override
    public List<MusicPlaylist> listMusicPlaylists() {
        return musicPlaylistRepository.findAll();
    }

}
