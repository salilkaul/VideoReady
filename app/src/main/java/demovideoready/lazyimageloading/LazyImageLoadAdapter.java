package demovideoready.lazyimageloading;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import demovideoready.sampleapp.com.demovideoready.MainActivity;
import demovideoready.sampleapp.com.demovideoready.R;

/**
 * Created by ig on 2/1/15.
 */
public class LazyImageLoadAdapter extends BaseAdapter {
    private Activity activity;
    private String[] data ;
    private LayoutInflater li =null ;
    public ImageLoader imageLoader ;

    public LazyImageLoadAdapter(Activity a,String[] input){
        this.activity=a;
        this.data=input;
        li=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(a.getBaseContext());
    }

    @Override
    public int getCount(){
        return data.length;
    }

    @Override
    public Object getItem(int position){
        return data[position];
    }

    @Override
    public long getItemId(int position){
        return position ;
    }
    public static class ViewHolder{
        TextView text;
        ImageView iv;
    }


    @Override
    public View getView(int position ,View convertView,ViewGroup parent){
        View rowView =convertView;
        ViewHolder holder;
        if(rowView==null){
            rowView=li.inflate(R.layout.row_view,null);
            holder = new ViewHolder();
            //holder.text=(TextView)rowView.findViewById(R.id.textView_row);
            holder.iv=(ImageView)rowView.findViewById(R.id.imageView);
            rowView.setTag(holder);

        }else{
            holder=(ViewHolder)rowView.getTag();
        }
        //holder.text.setText(data[position]);
        //DisplayImage function from ImageLoader Class
        imageLoader.DisplayImage(data[position], holder.iv);

        /******** Set Item Click Listner for LayoutInflater for each row ***********/
        //OnItemClickListner being added in the main activity
        //rowView.setOnClickListener(new OnItemClickListener(position));
        return rowView;
    }

      //Not being used currently just added to for later modification
    /*private class OnItemClickListener implements View.OnClickListener{
            private int position ;
            public OnItemClickListener(int i){
                position=i;
            }
          public void onClick(View vi){
              MainActivity sct =(MainActivity)activity;
              sct.onItemClick(position);
          }

      }*/

}
