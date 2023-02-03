import java.util.ArrayList;

/**
 * After the file has been parsed, the encoder
 * creates a list of playlists containing all playlists
 * so far.
 */
public class Encoder {

    private final ArrayList<Playlist> playlists = new ArrayList<>();

    /**
     * Constructor
     */
    public Encoder() {
    }

    /**
     * Creates a parser for the playlist, parses the file,
     * then adds each song from the parsed list to a playlist.
     * Lastly, adds this new playlist to the collection of playlists.
     *
     * @param filePath  the path to the xml file for the plyalist
     */
    public void encodePlaylist(String filePath) {
        String name = parsePlaylistName(filePath);
        Playlist p = new Playlist(name);

        Parser parser = new Parser(filePath);
        ArrayList<String[]> elements = parser.parseFile();
        for (String[] attrs : elements) {
            p.addSong(new Song(attrs[0], attrs[1], attrs[2]));
        }
        
        playlists.add(p);
    }

    /**
     * Parses the playlist's name from the filePath.
     *
     * @param filePath  the path to the xml file for the playlist
     * @return          the name of the playlist
     */
    private String parsePlaylistName(String filePath) {
        String[] path = filePath.split("/");
        String fileName = path[path.length - 1];
        return fileName.split("\\.")[0];
    }

    /**
     * Getter for playlists.
     *
     * @return  playlists    the collection of playlists
     */
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * ToString for the collection of playlists.
     *
     * @return str  formatted string of playlists' number, name, and songs
     */
    public String toString() {
        StringBuilder str = new StringBuilder();
        int i = 1;
        for (Playlist p : playlists) {
            str.append("PLAYLIST(").append(i++).append("): ");
            str.append(p.toString()).append("\n\n");
        }
        return str.toString();
    }
}
