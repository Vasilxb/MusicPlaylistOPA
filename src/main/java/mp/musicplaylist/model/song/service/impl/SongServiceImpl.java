package mp.musicplaylist.model.song.service.impl;

import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.repository.SongRepository;
import mp.musicplaylist.model.song.service.SongService;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;

    public SongServiceImpl(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public Song findSongById(SongId songId) {
        return songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found with id: " + songId));
    }

    @Override
    public Song createSong(Title title, Artist artist, Album album, Year year) {
        Song song = new Song(title, artist, album, year);
        return songRepository.save(song);
    }

    @Override
    public Song updateSong(SongId songId, Title title, Artist artist, Album album, Year year) {
        Song song = findSongById(songId);
        song.updateTitle(title);
        song.updateArtist(artist);
        song.updateAlbum(album);
        song.updateYear(year);
        return songRepository.save(song);
    }

    @Override
    public void deleteSong(SongId songId) {
        songRepository.deleteById(songId);
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }
}
