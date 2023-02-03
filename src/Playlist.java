import java.util.ArrayList;

/**
 * The class that represents a single playlist,
 * having a name and a collection of songs.
 */
public class Playlist {

    private final String playlistName;
    private ArrayList<Song> songs;

    /**
     * Default constructor
     */
    public Playlist() {
        playlistName = "";
        songs = new ArrayList<>();
    }

    /**
     * Constructor with just a name and no songs
     *
     * @param name  the name of the playlist
     */
    public Playlist(String name) {
        playlistName = name;
        songs = new ArrayList<>();
    }

    /**
     * Constructor with just a list of songs and no name
     *
     * @param s     the list of songs
     */
    public Playlist(ArrayList<Song> s) {
        playlistName = "";
        songs = s;
    }

    /**
     * The preferred constructor with both a name and list of songs
     *
     * @param name  the name of the playlist
     * @param s     the list of songs
     */
    public Playlist(String name, ArrayList<Song> s) {
        playlistName = name;
        songs = s;
    }

    /**
     * Adds a song to the playlist.
     *
     * @param s     the song to be added
     */
    public void addSong(Song s) {
        songs.add(s);
    }

    /**
     * ToString for the playlist
     *
     * @return str  a formatted string of the playlist's name,
     *              the number of songs, and the songs' info
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(playlistName).append("\n");
        str.append("NUMBER OF SONGS: ").append(getCount()).append("\n");
        for (Song song : songs) {
            str.append(song.toString()).append("\n");
        }
        return str.toString();
    }

    /**
     * Getter for number of songs in the playlist
     *
     * @return      number of songs in the playlist
     */
    public int getCount() {
        return songs.size();
    }

    /**
     * Getter for the songs in the playlist
     *
     * @return      the list of songs in the playlist
     */
    public ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Setter for the songs in the playlist. Replaces
     * the current list of songs.
     *
     * @param s     the new list of songs
     */
    public void setSongs(ArrayList<Song> s) {
        songs = s;
    }

}
