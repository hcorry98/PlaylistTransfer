import java.util.ArrayList;

public class Encoder {

    private final ArrayList<Playlist> playlists = new ArrayList<>();

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
        StringBuilder str = new StringBuilder();
        int i = 1;
        for (Playlist p : playlists) {
            str.append("PLAYLIST(").append(i++).append("): ");
            str.append(p.toString()).append("\n\n");
        }
        return str.toString();
    }
}
