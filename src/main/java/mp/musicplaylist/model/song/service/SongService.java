package mp.musicplaylist.model.song.service;

import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SongService {
     Song createSong(Title title, Artist artist, Album album, Year year);
     Song updateSong(SongId songId, Title title, Artist artist, Album album, Year year);
     void deleteSong(SongId songId);
     Song findSongById(SongId song);
     List<Song> listSongs();
}
