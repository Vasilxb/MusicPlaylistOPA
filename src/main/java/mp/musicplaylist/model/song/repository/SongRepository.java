package mp.musicplaylist.model.song.repository;

import mp.musicplaylist.model.common.repository.JpaSpecificationRepository;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.ids.SongId;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaSpecificationRepository<Song, SongId> {
}
