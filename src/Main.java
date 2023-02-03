import se.michaelthelin.spotify.model_objects.specification.Track;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        String playlistsFolder = "Playlists";
        ArrayList<String> files = listFilesForFolder(new File(playlistsFolder));
        for (String file : files) {
            encoder.encodePlaylist(file);
        }

        Handler handler = new Handler();
        ArrayList<ArrayList<Track>> allTracks = searchForSongs(encoder, handler);

        ArrayList<ArrayList<String>> allURIs = retrieveURIs(allTracks);

        try {
            writeFile("My Mix.txt", allURIs.get(0));
            writeFile("Christmas.txt", allURIs.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(String fileName, ArrayList<String> uris) throws IOException {
        String str = compileURIs(uris);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(str);

        writer.close();
    }

    private static String compileURIs(ArrayList<String> uris) {
        StringBuilder str = new StringBuilder();
        int i = 0;
        for (String uri : uris) {
            str.append(uri);
            str.append(",");
            i++;
            if (i >= 20) {
                str.delete(str.length() - 1, str.length());
                str.append("\n\n");
                i = 0;
            }
        }
        str.delete(str.length() - 1, str.length());
        return str.toString();
    }

    private static ArrayList<ArrayList<String>> retrieveURIs(ArrayList<ArrayList<Track>> allTracks) {
        ArrayList<ArrayList<String>> allURIs = new ArrayList<>();
        for (ArrayList<Track> tracks : allTracks) {
            ArrayList<String> URIs = new ArrayList<>();
            for (Track track : tracks) {
                URIs.add(track.getUri());
            }
            allURIs.add(URIs);
        }
        return allURIs;
    }

    private static ArrayList<ArrayList<Track>> searchForSongs(Encoder encoder, Handler handler) {
        ArrayList<Playlist> playlists = encoder.getPlaylists();
        ArrayList<ArrayList<Track>> allTracks = new ArrayList<>();
        ArrayList<Playlist> missingSongs = new ArrayList<>();
        for (Playlist playlist : playlists) {
            ArrayList<Track> tracks = new ArrayList<>();
            Playlist notFound = new Playlist("Not Found");
            for (Song song : playlist.getSongs()) {
                Track track = handler.searchTracks(song);
                if (track != null) {
                    tracks.add(track);
                    //System.out.println(track.getName() + " - " + track.getArtists()[0].getName());
                }
                else {
                    notFound.addSong(song);
                    System.out.println("NOT FOUND - " + song.getTitle() + " | " + song.getArtist());

                }
            }
            allTracks.add(tracks);
            missingSongs.add(notFound);
            System.out.println(notFound.getSongs().size());
            System.out.println("\n");
        }
        System.out.println(missingSongs.size());

        return allTracks;
    }

    private static ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else if (fileEntry.getName().split("\\.")[1].equalsIgnoreCase("xml")) {
                files.add(folder.getName() + "/" + fileEntry.getName());
            }
        }
        return files;
    }
}
