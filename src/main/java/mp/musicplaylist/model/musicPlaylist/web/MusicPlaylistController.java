package mp.musicplaylist.model.musicPlaylist.web;

import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.musicPlaylist.MusicPlaylist;
import mp.musicplaylist.model.musicPlaylist.ids.MusicPlaylistId;
import mp.musicplaylist.model.musicPlaylist.service.MusicPlaylistService;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/music-playlists")
public class MusicPlaylistController {
    private final MusicPlaylistService musicPlaylistService;
    private final SongService songService;

    public MusicPlaylistController(MusicPlaylistService musicPlaylistService, SongService songService) {
        this.musicPlaylistService = musicPlaylistService;
        this.songService = songService;
    }

    @GetMapping
    public String showMusicPlaylists(@RequestParam(value = "musicPlaylistName", required = false) String musicPlaylistName, Model model) {
        List<MusicPlaylist> musicPlaylists;
        if (musicPlaylistName == null || musicPlaylistName.isBlank()) {
            musicPlaylists = musicPlaylistService.listMusicPlaylists();
        } else {
            musicPlaylists = musicPlaylistService.findMusicPlaylistByName(musicPlaylistName);
        }
        model.addAttribute("musicPlaylists", musicPlaylists);
        model.addAttribute("songs", songService.listSongs());
        model.addAttribute("musicPlaylistName", musicPlaylistName);
        return "musicPlaylists";
    }

    @GetMapping("/create")
    public String showCreateMusicPlaylistForm(Model model) {
        model.addAttribute("songs", songService.listSongs());
        return "createMusicPlaylist";
    }

    @GetMapping("/{id}/edit")
    public String showEditMusicPlaylistForm(@PathVariable("id") String id, Model model) {
        MusicPlaylist playlist = musicPlaylistService.findMusicPlaylistById(new MusicPlaylistId(id));
        model.addAttribute("playlist", playlist);
        model.addAttribute("songs", songService.listSongs());
        return "editMusicPlaylist";
    }

    @PostMapping("/{id}/update")
    public String updateMusicPlaylist(@PathVariable("id") String id,
                                    @RequestParam String playlistName,
                                    @RequestParam String description,
                                    @RequestParam String userId,
                                    @RequestParam String songId) {
        musicPlaylistService.updateMusicPlaylist(
                new MusicPlaylistId(id),
                new PlaylistName(playlistName),
                new Description(description),
                userId,
                new SongId(songId)
        );
        return "redirect:/music-playlists";
    }

    @PostMapping("/save")
    public String saveMusicPlaylist(@RequestParam String playlistName,
                                   @RequestParam String description,
                                   @RequestParam String userId,
                                   @RequestParam String songId) {
        musicPlaylistService.createMusicPlaylist(
                new PlaylistName(playlistName),
                new Description(description),
                userId,
                new SongId(songId)
        );
        return "redirect:/music-playlists";
    }

    @PostMapping("/{id}/delete")
    public String deleteMusicPlaylist(@PathVariable("id") String id) {
        musicPlaylistService.deleteMusicPlaylist(new MusicPlaylistId(id));
        return "redirect:/music-playlists";
    }
}
