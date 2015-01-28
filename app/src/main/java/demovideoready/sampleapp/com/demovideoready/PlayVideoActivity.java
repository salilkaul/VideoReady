package demovideoready.sampleapp.com.demovideoready;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.VideoView;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class PlayVideoActivity extends Activity {

    VideoView vv ;
    MyMediaController videoController;
    static String videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        MediaPlayer mp_main = new MediaPlayer();

        vv =(VideoView)findViewById(R.id.videoView);
         videoController = new MyMediaController(this, (FrameLayout) findViewById(R.id.controllerAnchor));
       // videoController.setAnchorView(vv);
        vv.setMediaController(videoController);
        videoURL = getIntent().getStringExtra("URL");
        Log.i("The video URL is",""+videoURL);
        MediaPlayer mp =  new MediaPlayer();
        new YourAsyncTask().execute();
    }

    private class YourAsyncTask extends AsyncTask<Void,Void,Void>{
        ProgressDialog pg ;
        String videoURLinsideInBackground;
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            pg = ProgressDialog.show(PlayVideoActivity.this,"","Buffering",true);
            pg.setCancelable(true);
        }

        @Override
        protected  Void doInBackground(Void... params){
            String code=null;
            try {
                code = getYouTubeID(videoURL);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Log.i("video code<<<<<>>>>>>>>>",code);
            videoURLinsideInBackground=getRstpLinks(code);
            Log.i("The video URL is post change",""+videoURLinsideInBackground);
            return null ;
        }
        @Override
        protected  void onPostExecute(Void Result){
            super.onPostExecute(Result);
            Log.i("Inside onPostExecute","Inside onPostExecute>>>>><<<<<<<<<");
            vv.setVideoURI(Uri.parse(videoURLinsideInBackground));
          //  vv.setVideoURI(Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov"));
            //MediaController mc  = new MediaController(PlayVideoActivity.this,)
            vv.requestFocus();
            vv.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    if (pg.isShowing())
                        pg.dismiss();
                        mp.reset();
                    Log.e("Error", "Error" + what);
                    Log.e("Error extra>>>>>>>>>><<<<<<<<<<<", "Error" + extra);
                    return true;
                }
            });
            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    if (pg.isShowing())
                        pg.dismiss();
                    FrameLayout controllerAnchor = (FrameLayout) findViewById(R.id.controllerAnchor);
                    videoController.setAnchorView(controllerAnchor);
                    vv.start();
                }
            });
            vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(!mp.isPlaying()){
                        mp.release();
                    }
                }
            });

        }

    }
    public static String getURLVideoRTSP(String str){
        try{
            String gdy = "http://gdata.youtube.com/feeds/api/videos/";
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String id=getYouTubeID(str);
            URL url=new URL(gdy+id);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            Document doc =db.parse(connection.getInputStream());
            Element el =doc.getDocumentElement();
            NodeList  list =el.getElementsByTagName("media:content");
            String cursor =str;
            for(int i=0;i<list.getLength();i++){
                Node  node=list.item(i);
                if(node!=null){
                    NamedNodeMap nodeMap=node.getAttributes();
                    HashMap<String,String> maps = new HashMap<String,String>();
                    for(int j=0;j<nodeMap.getLength();j++){
                        Attr attr = (Attr) nodeMap.item(j);
                        maps.put(attr.getName(),attr.getValue());
                    }
                    Log.i("the contents of map<<<<<<<<<<<>>>>>>>>>",""+maps);
                    if(maps.containsKey("yt:format")){
                        String f =maps.get("yt:format");
                        if(maps.containsKey("url")){
                            cursor=maps.get("url");

                        }if(f.equals("1")){
                            return  cursor;
                        }
                    }
                }
            return cursor;
            }
        }catch (Exception ex){
            Log.i("Exception in making ",ex.getMessage());
        }
         return str ;
    }
    private String getRstpLinks(String code){
        String[] urls = new String[3];
        String link = "http://gdata.youtube.com/feeds/api/videos/" + code + "?alt=json";
        String json = getJsonString(link); // here you request from the server
        try {
            JSONObject obj = new JSONObject(json);
            String entry = obj.getString("entry");
            JSONObject enObj = new JSONObject(entry);
            String group = enObj.getString("media$group");
            JSONObject grObj = new JSONObject(group);
            String content = grObj.getString("media$content");
            JSONObject cntObj = new JSONObject(group);
            JSONArray array = grObj.getJSONArray("media$content");
            for(int j=0; j<array.length(); j++){
                JSONObject thumbs = array.getJSONObject(j);
                String url = thumbs.getString("url");
                urls[j] = url;
                Log.d("Within the getRstpLinks method<<<<<<<<<>>>>>>>>>>>", url);
                //data.setThumbUrl(thumbUrl);
            }


            Log.v("Within the getRstpLinks method", content);
        } catch (Exception e) {
            Log.e("Within the getRstpLinks method error", e.toString());
            urls[0] = urls[1] = urls[2] = null;
        }
        return urls[2];

    }

    public static String getJsonString(String url){
        Log.e("Request URL", url);
        StringBuilder buffer = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet( url );
        HttpEntity entity = null;
        try {
            HttpResponse response = client.execute(request);

            if( response.getStatusLine().getStatusCode() == 200 ){
                entity = response.getEntity();
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while( (line = br.readLine() )!= null ){
                    buffer.append(line);
                }
                br.close();

            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                entity.consumeContent();
            } catch (Exception e) {
                Log.e("Exception in getting  JSON response", "Exception = " + e.toString() );
            }
        }

        return buffer.toString();
    }

     protected  static String getYouTubeID(String url) throws MalformedURLException{
         String id =null ;
         try{
            String query = new URL(url).getQuery();
             if(query!=null){
                 String[] params=query.split("&");
                 for(String row:params){
                     String[] param1 =row.split("=");
                     if(param1[0].equals("v")){
                         id=param1[1];
                     }
                 }
             }else{
                 if(url.contains("embed")){
                     id=url.substring(url.lastIndexOf("/")+1);

                 }
             }
         }catch(Exception ex){
            Log.i("Exception in getting youtube id",ex.getMessage());
         }
        return id ;
     }
    //added to handle rotation
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", vv.getCurrentPosition());
        vv.pause();
    }
    //added to handle rotation
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       int position = savedInstanceState.getInt("Position");
        vv.seekTo(position);
    }

}
