package demovideoready.sampleapp.com.demovideoready;

/**
 * Created by ig on 28/1/15.
 */

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
public class Search extends Activity{

    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;
    private static YouTube youtube;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        youtube = new YouTube.Builder(new NetHttpTransport(),  new JacksonFactory(), new HttpRequestInitializer() {
            public void initialize(HttpRequest request) throws IOException {
            }
        }).setApplicationName("CloudSaveApp").build();
         new getData().execute();

    }

    private class getData extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            android.os.Debug.waitForDebugger();
            doSearch();
            return null;
        }

        public void doSearch() {

            try {
                // This object is used to make YouTube Data API requests. The last
                // argument is required, but since we don't need anything
                // initialized when the HttpRequest is initialized, we override
                // the interface and provide a no-op function.
                youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                    public void initialize(HttpRequest request) throws IOException {
                    }
                }).setApplicationName("BestSongsPlaylistsYoutube").build();

                Log.d("searchFunction", "a");
                // Define the API request for retrieving search results.
                YouTube.Search.List search = youtube.search().list("id,snippet");

                // Set your developer key from the Google Developers Console for
                // non-authenticated requests. See:
                // https://console.developers.google.com/
                search.setKey(DeveloperKey.DEVELOPER_KEY);
                search.setQ("laughing dog");

                // Restrict the search results to only include videos. See:
                // https://developers.google.com/youtube/v3/docs/search/list#type
                search.setType("video");

                // To increase efficiency, only retrieve the fields that the
                // application uses.
                search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
                search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

                Log.d("searchFunction", "b");
                // Call the API and print results.
                SearchListResponse searchResponse = search.execute();
                Log.d("searchFunction", "c");
                List<SearchResult> searchResultList = searchResponse.getItems();
                if (searchResultList != null) {
                    //prettyPrint(searchResultList.iterator(), queryTerm);
                    Log.d("searchFunction","video: "+searchResultList.get(0).getId().getVideoId());
                }
            } catch (GoogleJsonResponseException e) {
                Log.e("searchFunction","There was a service error: " + e.getDetails().getCode() + " : "
                        + e.getDetails().getMessage());
            } catch (IOException e) {
                Log.e("searchFunction","IOException: " + e.getCause() + " : " + e.getMessage() +" : "+ e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

    }
}
