import java.util.ArrayList;

public class Playlist {

    private final String playlistName;
    private ArrayList<Song> songs;

    public Playlist() {
        playlistName = "";
        songs = new ArrayList<>();
    }

    public Playlist(String name) {
        playlistName = name;
        songs = new ArrayList<>();
    }

    public Playlist(ArrayList<Song> s) {
        playlistName = "";
        songs = s;
    }

    public Playlist(String name, ArrayList<Song> s) {
        playlistName = name;
        songs = s;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(playlistName).append("\n");
        str.append("NUMBER OF SONGS: ").append(getCount()).append("\n");
        for (Song song : songs) {
            str.append(song.toString()).append("\n");
        }
        return str.toString();
    }

    public int getCount() {
        return songs.size();
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> s) {
        songs = s;
    }

}
