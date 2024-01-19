import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Song {
    private String songId;
    private String title;
    private String artist;
    private double duration;

    public Song(String songId, String title, String artist, double duration) {
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    public String getSongId() {
        return songId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public double getDuration() {
        return duration;
    }
}

class Playlist {
    private String playlistId;
    private String name;
    private List<Song> songs;

    public Playlist(String playlistId, String name) {
        this.playlistId = playlistId;
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void addSong(Song song) {
        songs.add(song);
        System.out.println("Song '" + song.getTitle() + "' added to playlist '" + name + "'.");
    }
}

class User {
    private String userId;
    private String username;
    private List<Playlist> playlists;

    public User(String userId, String username) {
        this.userId = userId;
        this.username = username;
        this.playlists = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void createPlaylist(String playlistId, String playlistName) {
        Playlist playlist = new Playlist(playlistId, playlistName);
        playlists.add(playlist);
        System.out.println("Playlist '" + playlist.getName() + "' created by user '" + username + "'.");
    }

    public void recommendSong(Song song) {
        System.out.println("Song '" + song.getTitle() + "' recommended to user '" + username + "'.");
    }
}

public class Main {
    public static void main(String[] args) {
        Song song1 = new Song("S001", "Shape of You", "Ed Sheeran", 3.54);
        Song song2 = new Song("S002", "Believer", "Imagine Dragons", 3.23);
        Song song3 = new Song("S003", "Dance Monkey", "Tones and I", 4.20);

        User user1 = new User("U001", "janak");
        User user2 = new User("U002", "gamait");

        user1.createPlaylist("P001", "Favorites");
        user2.createPlaylist("P002", "Workout");

        user1.getPlaylists().get(0).addSong(song1);
        user1.getPlaylists().get(0).addSong(song2);
        user2.getPlaylists().get(0).addSong(song3);

        user1.recommendSong(song3);
        user2.recommendSong(song1);
    }
}
