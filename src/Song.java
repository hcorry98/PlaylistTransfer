/**
 * The class representing a single song.
 * Contains a title, artist, album, and
 * a uri and id for Spotify.
 */
public class Song {

    private final String title;
    private String artist;
    private String album;
    private final String uri;
    private String id;

    /**
     * Default constructor
     */
    public Song() {
        title = "N/A";
        artist = "N/A";
        album = "N/A";
        uri = "N/A";
        id = "N/A";
    }

    /**
     * Constructor with the song's title, artist, and album
     *
     * @param t     the song's title
     * @param ar    the song's artist
     * @param al    the song's album
     */
    public Song(String t, String ar, String al) {
        title = t;
        artist = ar;
        album = al;
        uri = "N/A";
        id = "N/A";
    }

    /**
     * ToString for the song
     *
     * @return str  a formatted string containing the song's
     *              title, artist, album, and Spotify uri
     */
    public String toString() {
        String str = "TITLE: " + title + "; ";
        str += "ARTIST: " + artist + "; ";
        str += "ALBUM: " + album + "; ";
        str += "URI: " + uri;
        return str;
    }

    /**
     * Getter for the title of the song
     *
     * @return  title   the song's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for the artist of the song
     *
     * @return  artist   the song's artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setter for the artist of the song
     *
     * @param artist    the song's artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Getter for the album of the song
     *
     * @return  album   the song's album
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Setter for the album of the song
     *
     * @param album    the song's album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Setter for the id of the song
     *
     * @param id    the song's id for Spotify
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Getter for the id of the song
     *
     * @return  id   the song's id for Spotify
     */
    public String getID() {
        return id;
    }
}
