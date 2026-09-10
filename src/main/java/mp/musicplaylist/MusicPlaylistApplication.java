package mp.musicplaylist;

import mp.musicplaylist.model.common.valueObjects.Description;
import mp.musicplaylist.model.common.valueObjects.Title;
import mp.musicplaylist.model.common.valueObjects.Year;
import mp.musicplaylist.model.musicPlaylist.service.MusicPlaylistService;
import mp.musicplaylist.model.musicPlaylist.valueObjects.PlaylistName;
import mp.musicplaylist.model.song.service.SongService;
import mp.musicplaylist.model.song.valueObjects.Album;
import mp.musicplaylist.model.song.valueObjects.Artist;
import mp.musicplaylist.security.user.UserAccount;
import mp.musicplaylist.security.user.UserAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class MusicPlaylistApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicPlaylistApplication.class, args);
    }

    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    CommandLineRunner seedDemoData(SongService songService,
                                   MusicPlaylistService musicPlaylistService,
                                   UserAccountRepository userAccountRepository,
                                   PasswordEncoder passwordEncoder,
                                   @Value("${app.bootstrap-admin.username:admin}") String adminUsername,
                                   @Value("${app.bootstrap-admin.password:admin123}") String adminPassword,
                                   @Value("${app.bootstrap-admin.name:System}") String adminName,
                                   @Value("${app.bootstrap-admin.surname:Administrator}") String adminSurname) {
        return args -> {
            if (!userAccountRepository.existsByUsername(adminUsername)) {
                UserAccount admin = new UserAccount();
                admin.setUsername(adminUsername);
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setName(adminName);
                admin.setSurname(adminSurname);
                admin.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER"));
                userAccountRepository.save(admin);
            }

            if (songService.listSongs().isEmpty()) {
                songService.createSong(new Title("Nightmare"), new Artist("Halsey"), new Album("If I can't have love, I want power"), new Year("2021"));
                songService.createSong(new Title("Crank 2"), new Artist("Slayyyter"), new Album("WMIA"), new Year("2026"));
                songService.createSong(new Title("101"), new Artist("Kim Petras"), new Album("Detour"), new Year("2026"));
            }

            if (musicPlaylistService.listMusicPlaylists().isEmpty()) {
                var songs = songService.listSongs();
                if (!songs.isEmpty()) {
                    musicPlaylistService.createMusicPlaylist(
                            new PlaylistName("Chill Night Drive"),
                            new Description("Synthwave and dreamy vibes for late night drives"),
                            "user-42",
                            songs.get(0).getId()
                    );
                    musicPlaylistService.createMusicPlaylist(
                            new PlaylistName("Classic Hyperpop Hits"),
                            new Description("Timeless songs from iconic hyperpop legends"),
                            "user-42",
                            songs.size() > 1 ? songs.get(1).getId() : songs.get(0).getId()
                    );
                }
            }
        };
    }
}
