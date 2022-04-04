import java.util.ArrayList;

public class Playlist {

    private String playlistName;
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
        String str = "";
        str += playlistName + "\n";
        str += "NUMBER OF SONGS: " + getCount() + "\n";
        for (Song song : songs) {
            str += song.toString() + "\n";
        }
        return str;
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

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String name) {
        playlistName = name;
    }
}
