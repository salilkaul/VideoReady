package demovideoready.sampleapp.com.demovideoready;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import datamodels.videodetails;

/**
 * Created by ig on 24/12/14.
 */
public class VideoListAdapter implements ListAdapter {
    private List<videodetails>  listofitems;
    private Activity activityctx;


    public VideoListAdapter(List<videodetails> input,Activity act){
        listofitems=input;
        activityctx=act  ;
    }

    static class ViewHolder{
        TextView tv;
        ImageView iv ;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return listofitems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView =convertView ;
        if(rowView==null){
            LayoutInflater li =activityctx.getLayoutInflater();
            rowView=li.inflate(R.layout.row_view,parent,false);
            ViewHolder holder = new ViewHolder();
           // holder.tv=(TextView)rowView.findViewById(R.id.textView_row);
            holder.iv=(ImageView)rowView.findViewById(R.id.imageView);
            rowView.setTag(holder);
        }
        // add lazy loading....//

        ViewHolder holder =(ViewHolder)rowView.getTag();
        holder.tv.setText(listofitems.get(position).getVideoTag());
        return  rowView ;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}



