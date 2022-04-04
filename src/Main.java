import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        String playlistsFolder = "Playlists";
        ArrayList<String> files = listFilesForFolder(new File(playlistsFolder));
        for (String file : files) {
            encoder.encodePlaylist(file);
        }

        SpotifyHandler handler = new SpotifyHandler();
        AuthToken authToken = handler.authorize();

        ArrayList<Playlist> playlists = encoder.getPlaylists();
        ArrayList<ArrayList<String>> playlistURIs = new ArrayList<>();
        ArrayList<Playlist> playlistsNotFound = new ArrayList<>();
        int nf = 0;
        for (Playlist p : playlists) {
            ArrayList<String> URIs = new ArrayList<>();
            Playlist notFound = new Playlist();

            int i = 0;
            for (Song s : p.getSongs()) {
                String uri = handler.search(s, authToken);
                s.setURI(uri);
                if (!uri.equals("NOT FOUND")) {
                    URIs.add(uri);
                }
                else {
                    notFound.addSong(s);
                    System.out.println(s);
                    nf++;
                }
                i++;
                if (i >= 10) {
                    i = 0;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            playlistURIs.add(URIs);
            playlistsNotFound.add(notFound);
            System.out.println();
            System.out.println(nf);
            nf = 0;
            System.out.println("\n");

        }

    }

    private static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else if (fileEntry.getName().split("\\.")[1].equalsIgnoreCase("xml")) {
                files.add(folder.getName() + "/" + fileEntry.getName());
            }
        }
        return files;
    }
}
