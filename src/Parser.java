import java.io.*;
import java.util.ArrayList;

public class Parser {

    private final String filePath;
    private final ArrayList<String[]> elements = new ArrayList<>();

    public Parser(String fPath) {
        filePath = fPath;
    }

    public ArrayList<String[]> parseFile() {
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
