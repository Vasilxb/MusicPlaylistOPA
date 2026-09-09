package mp.musicplaylist.model.musicPlaylist.repository;

import jakarta.persistence.QueryHint;
import mp.musicplaylist.model.common.repository.JpaSpecificationRepository;
import mp.musicplaylist.model.musicPlaylist.MusicPlaylist;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicPlaylistRepository extends JpaSpecificationRepository<MusicPlaylist, MusicPlaylistId> {
    @Query("SELECT m FROM MusicPlaylist m WHERE lower(m.playlistName.value) LIKE lower(concat('%', :musicPlaylistName, '%'))")
    List<MusicPlaylist> findByMusicPlaylistName(@Param("musicPlaylistName") String musicPlaylistName);
}
