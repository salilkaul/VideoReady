package demovideoready.lazyimageloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler ;

import demovideoready.sampleapp.com.demovideoready.R;

/**
 * Created by ig on 2/1/15.
 */
public class ImageLoader {
    MemoryCache memoryCache = new MemoryCache();
    final int stub_id = R.drawable.ic_launcher;
    FileCache fileCache;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new HashMap<ImageView, String>());
    ExecutorService executorService;
    Handler handler = new Handler();

    public ImageLoader(Context ctx) {
        fileCache = new FileCache(ctx);
        executorService = Executors.newFixedThreadPool(5);
    }

    public void DisplayImage(String url, ImageView imageView) {
        imageViews.put(imageView, url);
        Bitmap bm = memoryCache.get(url);
        if(bm!=null){
            imageView.setImageBitmap(bm);
        }else{
            queuePhoto(url,imageView);
            imageView.setImageResource(stub_id);
        }

    }

    class PhotosLoader implements  Runnable{
        PhotoToLoad photoToLoad;
        PhotosLoader(PhotoToLoad pl){
            this.photoToLoad=pl;
        }
        @Override
        public void run(){
            try{
                if(imageViewReused(photoToLoad))
                    return ;
                Bitmap bm =getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url,bm);
                if(imageViewReused(photoToLoad))
                    return ;
                BitmapDisplayer displayer = new BitmapDisplayer(bm,photoToLoad);
                handler.post(displayer);
         }catch(Throwable th){
                th.printStackTrace();
            }

         }

    }
    private void queuePhoto(String url,ImageView imageView){
        // Store image and url in PhotoToLoad object
        PhotoToLoad p = new PhotoToLoad(url, imageView);

        // pass PhotoToLoad object to PhotosLoader runnable class
        // and submit PhotosLoader runnable to executers to run runnable
        // Submits a PhotosLoader runnable task for execution

        executorService.submit(new PhotosLoader(p));

    }
    private class PhotoToLoad{
        String url;
        ImageView iv;
        PhotoToLoad(String u,ImageView i){
            url=u;
            iv=i;
        }
    }

    private Bitmap getBitmap(String url)
    {
        File f=fileCache.getFile(url);

        //from SD cache
        //CHECK : if trying to decode file which not exist in cache return null
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;

        // Download image file from web
        try {

            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();

            // Constructs a new FileOutputStream that writes to file
            // if file not exist then it will create file
            OutputStream os = new FileOutputStream(f);

            // See Utils class CopyStream method
            // It will each pixel from input stream and
            // write pixels to output stream (file)
            Utils.CopyStream(is, os);

            os.close();
            conn.disconnect();

            //Now file created and going to resize file with defined height
            // Decodes image and scales it to reduce memory consumption
            bitmap = decodeFile(f);

            return bitmap;

        } catch (Throwable ex){
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }
    private Bitmap decodeFile(File f){
        Bitmap bitmap_return=null;
        try{
            BitmapFactory.Options bmpo=  new BitmapFactory.Options();
            bmpo.inJustDecodeBounds=true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis,null,bmpo);
            fis.close();
            final int required_size=85;
            int width_tmp=bmpo.outWidth,height_tmp=bmpo.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<required_size || height_tmp<required_size)
                    break ;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            FileInputStream stream2=new FileInputStream(f);
             bitmap_return=BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap_return;
        }catch (Exception ex){
            ex.printStackTrace();
            bitmap_return=null;
        }
        return bitmap_return;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad){

        String tag =imageViews.get(photoToLoad.iv);
        if(tag==null||!tag.equals(photoToLoad.url) )
            return true;
        return  false ;
    }
    public void clearCache(){
        memoryCache.clear();
        fileCache.clear();
    }
    class BitmapDisplayer implements  Runnable{
        Bitmap bitmap ;
        PhotoToLoad photoToLoad;
        BitmapDisplayer(Bitmap bm ,PhotoToLoad pl){
            bitmap=bm;
            photoToLoad=pl;
        }



    @Override
        public void run(){
        if(imageViewReused(photoToLoad))
            return;
        if(bitmap!=null){
            photoToLoad.iv.setImageBitmap(bitmap);
        }else{
            photoToLoad.iv.setImageResource(stub_id);
        }
    }
    }
}