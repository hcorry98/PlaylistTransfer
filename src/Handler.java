import com.neovisionaries.i18n.CountryCode;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

import java.io.IOException;
import java.util.ArrayList;

public class Handler {

    private static final String clientId = "42b29b6874f64b219b77b1b159b303d5";
    private static final String clientSecret = "4d2ca71e55d94f0cb91ef766114e16fc";

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();

    public Handler() {
        clientCredentials();
    }

    public Track searchTracks(Song song) {
        String simplifiedTitle = song.getTitle().replaceAll("\\([^)]*\\)", "");
        simplifiedTitle = simplifiedTitle.replaceAll("&#38;", "&").trim();

        String simplifiedArtist = song.getArtist().replaceAll("\\([^)]*\\)", "");
        simplifiedArtist = simplifiedArtist.replaceAll(" &#38;", ", ").trim();

        String simplifiedAlbum = song.getAlbum().replaceAll("\\([^)]*\\)", "");
        simplifiedAlbum = simplifiedAlbum.split(" - Single")[0];
        simplifiedAlbum = simplifiedAlbum.split(" - EP")[0];
        simplifiedAlbum = simplifiedAlbum.replaceAll("&#38;", "&").trim();

        String q = simplifiedTitle + " " + simplifiedArtist;
        SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(q).market(CountryCode.US).build();

        try {
            Paging<Track> trackPaging = searchTracksRequest.execute();

            for (Track t : trackPaging.getItems()) {
                ArrayList<String> artists = new ArrayList<>();
                for (ArtistSimplified artist : t.getArtists()) {
                    artists.add(artist.getName());
                }
                if (artists.contains(song.getArtist().split(" &#38")[0])) {
                    String trackAlbumSimplified = t.getAlbum().getName().replaceAll("\\([^)]*\\)", "").trim();
                    if (trackAlbumSimplified.equalsIgnoreCase(simplifiedAlbum) || trackAlbumSimplified.contains(simplifiedAlbum) || simplifiedAlbum.contains(trackAlbumSimplified)) {
                        return t;
                    }
                }
            }

            for (Track t : trackPaging.getItems()) {
                ArrayList<String> artists = new ArrayList<>();
                for (ArtistSimplified artist : t.getArtists()) {
                    artists.add(artist.getName());
                }
                if (artists.contains(song.getArtist().split(" &#38")[0])) {
                    //System.out.println("Found with Different Album | " + song.getTitle() + " - " + song.getArtist() + " - " + simplifiedAlbum + " | " + t.getAlbum().getName());
                    return t;
                }
            }

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }

    private static void clientCredentials() {
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());

            System.out.println("Expires in: " + clientCredentials.getExpiresIn());
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
