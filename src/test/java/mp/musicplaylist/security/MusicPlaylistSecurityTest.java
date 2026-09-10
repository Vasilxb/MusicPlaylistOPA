package mp.musicplaylist.security;

import mp.musicplaylist.model.musicPlaylist.MusicPlaylist;
import mp.musicplaylist.model.musicPlaylist.service.MusicPlaylistService;
import mp.musicplaylist.model.song.Song;
import mp.musicplaylist.model.song.service.SongService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MusicPlaylistSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MusicPlaylistService musicPlaylistService;

    @Autowired
    private SongService songService;


    // 1. Authenticated user can open create playlist page

    @Test
    void authenticatedUser_canOpenCreatePlaylistForm() throws Exception {

        mockMvc.perform(
                        get("/music-playlists/create")
                                .with(user("user-42").roles("USER"))
                )
                .andExpect(status().isOk());
    }


    // 2. Authenticated user can create a playlist

    @Test
    void authenticatedUser_canCreatePlaylist() throws Exception {

        List<Song> songs = songService.listSongs();

        mockMvc.perform(
                        post("/music-playlists/save")
                                .with(user("user-42").roles("USER"))
                                .with(csrf())
                                .param("playlistName", "Test Playlist")
                                .param("description", "Test playlist description")
                                .param(
                                        "songId",
                                        songs.get(0).getId().toString()
                                )
                )
                .andExpect(status().is3xxRedirection());
    }


    // 3. Playlist owner can open edit page

    @Test
    void playlistOwner_canOpenEditPage() throws Exception {

        MusicPlaylist playlist =
                musicPlaylistService
                        .findMusicPlaylistByName("Chill Night Drive")
                        .get(0);

        mockMvc.perform(
                        get("/music-playlists/"
                                + playlist.getId()
                                + "/edit")
                                .with(user("user-42").roles("USER"))
                )
                .andExpect(status().isOk());
    }


    // 4. Different user cannot update another user's playlist

    @Test
    void differentUser_cannotUpdatePlaylist() throws Exception {

        MusicPlaylist playlist =
                musicPlaylistService
                        .findMusicPlaylistByName("Chill Night Drive")
                        .get(0);

        List<Song> songs = songService.listSongs();

        mockMvc.perform(
                        post("/music-playlists/"
                                + playlist.getId()
                                + "/update")
                                .with(user("someone-else").roles("USER"))
                                .with(csrf())
                                .param("playlistName", "Hacked Playlist")
                                .param("description", "This should not work")
                                .param(
                                        "songId",
                                        songs.get(0).getId().toString()
                                )
                )
                .andExpect(status().isForbidden());
    }


    // 5. Different user cannot delete another user's playlist

    @Test
    void differentUser_cannotDeletePlaylist() throws Exception {

        MusicPlaylist playlist =
                musicPlaylistService
                        .findMusicPlaylistByName("Chill Night Drive")
                        .get(0);

        mockMvc.perform(
                        post("/music-playlists/"
                                + playlist.getId()
                                + "/delete")
                                .with(user("someone-else").roles("USER"))
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }


    // 6. Anonymous user cannot open create page

    @Test
    void anonymousUser_cannotOpenCreatePlaylistForm() throws Exception {

        mockMvc.perform(
                        get("/music-playlists/create")
                )
                .andExpect(status().is3xxRedirection());
    }


    // 7. Anonymous user cannot create playlist

    @Test
    void anonymousUser_cannotCreatePlaylist() throws Exception {

        List<Song> songs = songService.listSongs();

        mockMvc.perform(
                        post("/music-playlists/save")
                                .with(csrf())
                                .param("playlistName", "Anonymous Playlist")
                                .param("description", "Should not be created")
                                .param(
                                        "songId",
                                        songs.get(0).getId().toString()
                                )
                )
                .andExpect(status().is3xxRedirection());
    }


    // 8. Authenticated user cannot use wrong HTTP method

    @Test
    void authenticatedUser_cannotUseWrongHttpMethod() throws Exception {

        MusicPlaylist playlist =
                musicPlaylistService
                        .findMusicPlaylistByName("Chill Night Drive")
                        .get(0);

        mockMvc.perform(
                        get("/music-playlists/"
                                + playlist.getId()
                                + "/delete")
                                .with(user("user-42").roles("USER"))
                )
                .andExpect(status().isForbidden());
    }
}
