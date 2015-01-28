package demovideoready.lazyimageloading;

import android.content.Context;

import java.io.File;

/**
 * Created by ig on 2/1/15.
 */
public class FileCache {

     private File dirPath ;
    public FileCache(Context ctx){
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)){
            dirPath = new File( android.os.Environment.getExternalStorageDirectory().getAbsolutePath(),"LazyList");
        }else{
            dirPath=ctx.getCacheDir();

        }
        if(!dirPath.exists()){
            dirPath.mkdirs();
        }
    }


    public File getFile(String url){
        String fileName= String.valueOf(url.hashCode());
        return new File(dirPath,fileName);
    }

    public void clear(){
        File[] files =dirPath.listFiles();
        if(files==null) {
            return ;
        }
        for(File f:files){
            f.delete();
        }
    }
}
