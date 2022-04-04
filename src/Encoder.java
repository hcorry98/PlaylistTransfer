import java.util.ArrayList;

public class Encoder {

    private ArrayList<Playlist> playlists = new ArrayList<>();

    public Encoder() {
    }

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

    private String parsePlaylistName(String filePath) {
        String[] path = filePath.split("/");
        String fileName = path[path.length - 1];
        return fileName.split("\\.")[0];
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }
    
    public String toString() {
        String str = null;
        int i = 1;
        for (Playlist p : playlists) {
            str += "PLAYLIST(" + i++ + "): ";
            str += p.toString() + "\n\n";
        }
        return str;
    }
}
