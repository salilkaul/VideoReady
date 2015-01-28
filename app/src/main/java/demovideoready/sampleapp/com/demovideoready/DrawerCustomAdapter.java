package demovideoready.sampleapp.com.demovideoready;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by ig on 8/1/15.
 */
public class DrawerCustomAdapter extends ArrayAdapter<ObjectDrawerItem> {
    Context ctx ;
    int layoutResourceId;
    ObjectDrawerItem data[] = null;

    public DrawerCustomAdapter(Context mContext, int layoutResourceId, ObjectDrawerItem[] data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.ctx = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);
        TextView textViewName = (TextView) listItem.findViewById(R.id.textViewName);
        ObjectDrawerItem folder = data[position];
        textViewName.setText(folder.name);
         return listItem;
    }


}
