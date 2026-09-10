package mp.musicplaylist.model.song.web;

import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.ids.SongId;
import mp.musicplaylist.model.song.service.SongService;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public String showSongs(Model model) {
        List<Song> songs = songService.listSongs();
        model.addAttribute("songs", songs);
        return "songs";
    }

    @GetMapping("/create")
    public String showCreateSongForm() {
        return "createSong";
    }

    @GetMapping("/{id}/edit")
    public String showEditSongForm(@PathVariable("id") String id, Model model) {
        Song song = songService.findSongById(new SongId(id));
        model.addAttribute("song", song);
        return "editSong";
    }

    @PostMapping("/save")
    public String saveSong(@RequestParam String title,
                           @RequestParam String artist,
                           @RequestParam String album,
                           @RequestParam String year,
                           Model model) {
        try {
            songService.createSong(new Title(title), new Artist(artist), new Album(album), new Year(year));
            return "redirect:/songs";
        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("title", title);
            model.addAttribute("artist", artist);
            model.addAttribute("album", album);
            model.addAttribute("year", year);
            return "createSong";
        }
    }

    @PostMapping("/{id}/update")
    public String updateSong(@PathVariable("id") String id,
                             @RequestParam String title,
                             @RequestParam String artist,
                             @RequestParam String album,
                             @RequestParam String year) {
        songService.updateSong(new SongId(id), new Title(title), new Artist(artist), new Album(album), new Year(year));
        return "redirect:/songs";
    }

    @PostMapping("/{id}/delete")
    public String deleteSong(@PathVariable("id") String id) {
        songService.deleteSong(new SongId(id));
        return "redirect:/songs";
    }
}
