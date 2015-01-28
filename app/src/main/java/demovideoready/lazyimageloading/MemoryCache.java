package demovideoready.lazyimageloading;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ig on 2/1/15.
 */
public class MemoryCache{
    private static final String TAG="MemoryCache";
    private Map<String, Bitmap> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, Bitmap>(10, 1.5f, true));


    private long size=0;
    private long limit =1000000;
    public MemoryCache(){
        setLimit(Runtime.getRuntime().maxMemory() / 4);
    }
    public void setLimit(long new_limit){
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to " + limit / 1024. / 1024. + "MB");
    }
    public Bitmap get(String str){
        Bitmap retval;
        try {
            if (!cache.containsKey(str))
                return null;
            retval =cache.get(str);
        }catch(Exception ex){
            ex.printStackTrace();
            retval=null;
        }
        return retval;
    }

    public void put(String key,Bitmap value){
        try {
            if (cache.containsKey(key))
                size -= getSizeInBytes(value);
            cache.put(key, value);
            size += getSizeInBytes(value);
            checkSize();
        }catch (Exception ex){

        }
        }

    public void clear(){
        try{
            cache.clear();
            size=0;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    long getSizeInBytes(Bitmap bm){
        if(bm==null)
            return 0;
        return bm.getRowBytes()*bm.getHeight();
    }

    private void checkSize(){
        if(size>limit){
            Iterator<Map.Entry<String,Bitmap>> entryIterator=cache.entrySet().iterator();
            while(entryIterator.hasNext()){
                Map.Entry<String,Bitmap> entry =(Map.Entry<String,Bitmap>)entryIterator.next();
                size-=getSizeInBytes(entry.getValue());
                entryIterator.remove();
                if(size<=limit)
                    break ;
            }
            Log.i(TAG, "Clean cache. New size "+cache.size());
        }
    }
}


