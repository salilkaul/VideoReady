package demovideoready.lazyimageloading;

import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by ig on 2/1/15.
 */
public class Utils {
    public static void CopyStream(InputStream is,OutputStream os){
        final int buffer_size=1024 ;
        byte[] byte_array = new byte[buffer_size];
        try {
            for (; ; ) {
                int count = is.read(byte_array, 0, buffer_size);
                if (count==-1)
                    break;
                os.write(byte_array,0,count);
            }
        }catch (Exception ex){
            Log.i("Exception",ex.getMessage());
        }
        }
}
