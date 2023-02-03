import java.io.*;
import java.util.ArrayList;

/**
 * Parses a playlist's xml file from Apple Music
 * into a list of an array of strings containing
 * the song's title, artist, and album.
 */
public class Parser {

    private final String filePath;

    /**
     * Constructor requires a filePath
     *
     * @param fPath the path to the xml file for the playlist
     */
    public Parser(String fPath) {
        filePath = fPath;
    }

    /**
     * Accesses the file at filePath, reads in each line, calls parseLine
     * to reformat the line, then separates the line into an array of
     * strings, containing the song's title, artist, and album.
     * <p>
     * Continues doing this for each song (each line), adding each song
     * to the list "elements". After all songs have been parsed from the
     * xml file, it returns the list.
     *
     * @return  elements    a list of all the songs, each song represented by an
     *                      array of strings with its title, artist, and album
     */
    public ArrayList<String[]> parseFile() {
        ArrayList<String[]> elements = new ArrayList<>();
        String[] track = {null, null, null};

        File file = new File(filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String result = parseLine(line);
                if (result != null) {
                    String key = result.split("/")[0];
                    switch (key) {
                        case "Name" -> track[0] = result.split("/")[1];
                        case "Artist" -> track[1] = result.split("/")[1];
                        case "Album" -> track[2] = result.split("/")[1];
                    }
                }

                if (track[0] != null && track[1] != null && track[2] != null) {
                    elements.add(track.clone());
                    track[0] = null;
                    track[1] = null;
                    track[2] = null;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return elements;
    }

    /**
     * Is passed an original line from the xml document
     * and reformats the string to be used later.
     *
     * @param  l    the original line that needs to be parsed
     * @return      a formatted string from the original line
     */
    public String parseLine(String l) {
        String[] line = l.split(">");
        if (line.length == 4) {
            String key = line[1].split("<")[0];
            switch (key) {
                case "Name" -> {
                    return "Name/" + line[3].split("<")[0];
                }
                case "Artist" -> {
                    return "Artist/" + line[3].split("<")[0];
                }
                case "Album" -> {
                    return "Album/" + line[3].split("<")[0];
                }
            }
        }

        return null;
    }

}
