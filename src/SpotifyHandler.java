import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpotifyHandler {

    private static final String CLIENT_ID = "42b29b6874f64b219b77b1b159b303d5";
    private static final String CLIENT_SECRET = "4d2ca71e55d94f0cb91ef766114e16fc";

    public SpotifyHandler() {

    }

    public String search(Song song, AuthToken authToken) {
        try {
            String url_search = "api.spotify.com/v1/search?q=";
            String track = "track: " + song.getTitle();
            String artist = "artist: " + song.getArtist();
            String album = "album: " + song.getAlbum().split(" - ")[0];
            url_search += track + ", " + artist + ", " + album;
            url_search += "&type=track&market=US";

            url_search = url_search.replaceAll("\\(", "");
            url_search = url_search.replaceAll("\\)", "");
            url_search = url_search.replaceAll(":", "%3A");
            url_search = url_search.replaceAll(" ", "%20");
            url_search = url_search.replaceAll(",", "%2C");
            url_search = url_search.replaceAll("&#38;", "&");
            url_search = url_search.replaceAll("’", "%E2%80%99");
            url_search = "https://" + url_search;

            URL url = new URL(url_search);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            String authStr = authToken.getTokenType() + " " + authToken.getAccessToken();
            conn.setRequestProperty("Authorization", authStr);

            conn.connect();
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getInputStream().readAllBytes());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                if (responseLine.contains("spotify:track:")) {
                    String uri = responseLine.split(":", 2)[1].trim();
                    uri = uri.replaceAll("\"", "");
                    return uri;
                }
            }

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NOT FOUND";
    }

    public AuthToken authorize() {
        try {
            String url_auth = "https://accounts.spotify.com/api/token";
            URL url = new URL(url_auth);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestMethod("POST");
            String clientCred = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedClientCred = Base64.getEncoder().encodeToString(clientCred.getBytes());
            conn.setRequestProperty("Authorization", String.format("Basic %s", encodedClientCred));
            conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=client_credentials";
            byte[] out = body.getBytes(StandardCharsets.UTF_8);
            int length = out.length;
            conn.setFixedLengthStreamingMode(length);

            conn.connect();
            try(OutputStream os = conn.getOutputStream()) {
                os.write(out);
            }

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            InputStream result = conn.getInputStream();
            String jsonResult = new String(result.readAllBytes());
            Gson gson = new Gson();
            AuthToken authToken = gson.fromJson(jsonResult, AuthToken.class);

            conn.disconnect();

            return authToken;
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
