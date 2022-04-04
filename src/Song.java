public class Song {

    private String title;
    private String artist;
    private String album;
    private String uri;
    private String id;

    public Song() {
        title = "N/A";
        artist = "N/A";
        album = "N/A";
        uri = "N/A";
        id = "N/A";
    }

    public Song(String t, String ar, String al) {
        title = t;
        artist = ar;
        album = al;
        uri = "N/A";
        id = "N/A";
    }

    public String toString() {
        String str = "TITLE: " + title + "; ";
        str += "ARTIST: " + artist + "; ";
        str += "ALBUM: " + album + "; ";
        str += "URI: " + uri;
        return str;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setURI(String uri) {
        this.uri = uri;
        if (!uri.equals("NOT FOUND")) {
            setID(uri.split(":")[2]);
        }
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getURI() {
        return uri;
    }

    public String getID() {
        return id;
    }
}
