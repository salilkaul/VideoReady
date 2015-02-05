package demovideoready.sampleapp.com.demovideoready;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.util.List;

import datamodels.videodetails;


public class MainActivity extends ActionBarActivity implements CreateFragment.OnFragmentInteractionListener ,videoImageFragment.OnFragmentInteractionListener_Video ,VideoFragment.OnFragmentInteractionListener_videoFragment,videoImageFragment_cricket.OnFragmentInteractionListener_cricket,ItemFragment.OnFragmentInteractionListener_1 ,ItemFragment2.OnFragmentInteractionListener_2,ItemFragment3.OnFragmentInteractionListener_3{
    List<videodetails> listofviddetails ;

    String[] testArray ;
    //navigation drawer activities
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    String[] imgarray ;
    String[] imgarray_cricket ;
    String[] videoids_cricket ;
    private ActionBarDrawerToggle actionBarDrawerToggle ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                //Do some stuff
            Log.i("Orientation Detection  Code","ORIENTATION_LANDSCAPE");
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //Do some stuff
            Log.i("Orientation Detection  Code","ORIENTATION_POTRAIT");
        }
        setContentView(R.layout.activity_main);
        videoids_cricket =getResources().getStringArray(R.array.cricketvideos);
        imgarray_cricket= new String[videoids_cricket.length];
        testArray = getResources().getStringArray(R.array.listofvid);
        final String[] finaltestArray = testArray;
        String[] channelList_array =getResources().getStringArray(R.array.channellist);
        imgarray=new String[]{"http://res.cloudinary.com/videoready/image/upload/c_fill,g_north,w_488,h_280/vr-img-image1420014201129.jpg","http://res.cloudinary.com/videoready/image/upload/c_fill,g_north,w_488,h_280/vr-img-image1420014199372.jpg","http://res.cloudinary.com/videoready/image/upload/c_fill,g_north,w_235,h_130/vr-img-image1420014190876.jpg","http://res.cloudinary.com/videoready/image/upload/c_fill,g_north,w_235,h_130/vr-img-image1420020243055.jpg"};


       /* for(int i=0;i<finaltestArray.length;i++){
            try {
                imgarray[i] = "http://img.youtube.com/vi/"+PlayVideoActivity.getYouTubeID(finaltestArray[i])+"/0.jpg";
            }catch (MalformedURLException mfuex){
                mfuex.printStackTrace();;
            }
            }*/
        for(int i=0;i<videoids_cricket.length;i++){
            try {
                imgarray_cricket[i] = "http://img.youtube.com/vi/"+PlayVideoActivity.getYouTubeID(videoids_cricket[i])+"/0.jpg";
            }catch (MalformedURLException mfuex){
                mfuex.printStackTrace();;
            }
        }

        Log.i("Value of image URL's",imgarray.toString());
        Log.i("Value of image URL's cricket",imgarray_cricket.toString());

        videoImageFragment vf = new videoImageFragment();
        Bundle bun = new Bundle();
        bun.putStringArray("IMAGEURLS",imgarray);
        vf.setArguments(bun);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction() .add(R.id.content_frame, vf) .commit();
        }

        Toast.makeText(this,"Inside main activity",Toast.LENGTH_SHORT).show();
        //Hooking up the navigation drawer//
        mNavigationDrawerItemTitles=getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[5];
        drawerItem[0] = new ObjectDrawerItem("List of Cricket Videos");
        drawerItem[1] = new ObjectDrawerItem("Dummy Screen");
        drawerItem[2] = new ObjectDrawerItem("List of Fame Videos");
        drawerItem[3] = new ObjectDrawerItem("Channels");
        drawerItem[4] = new ObjectDrawerItem("Settings");
        DrawerCustomAdapter navigation_adapter = new DrawerCustomAdapter(this, R.layout.navigation_listview, drawerItem);
        mDrawerList.setAdapter(navigation_adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
               R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("DemoVideoReady");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        //on app start show the naviation drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.openDrawer(mDrawerList);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    //Clarity about menu
    /*@Override
     public boolean onPrepareOptionsMenu(Menu menu){
        boolean draweropen =mDrawerLayout.isDrawerOpen(mDrawerList);

    }*/

    void selectItem(int position){
        Fragment fragment =null;
        switch (position){
            case 0:
                fragment = new videoImageFragment_cricket();
                Bundle bun1 = new Bundle();
                bun1.putStringArray("IMAGEURLS",imgarray_cricket);
                fragment.setArguments(bun1);
                break;
            case 1:
                fragment = new CreateFragment();
                break;
            case 2:
                fragment = new videoImageFragment();
                Bundle bun = new Bundle();
                bun.putStringArray("IMAGEURLS",imgarray);
                fragment.setArguments(bun);
                break;
            case 3:
                //Channel Listing
                fragment =ChannelFragment.newInstance("Test of channels","Test of channels");
                break ;
            case 4:
                //settings screen

                break ;
            default:
                break;
        }
        if(fragment!=null){
            FragmentManager fragmentManager =getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            getSupportActionBar().setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            Log.i("Issue in creating fragment","No fragment created");
        }

    }




    @Override
    protected  void onDestroy(){
        super.onDestroy();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction_Video(int id) {
        //Properly use the youtubefragment player
        String videoURL =testArray[id];
        Intent startYouTubeView = new Intent(MainActivity.this,testyoutubeplayer.class);
        startYouTubeView.putExtra("URL",videoURL);
        startActivity(startYouTubeView);

    }
    @Override
    public void onFragmentInteraction_videoFragment(Uri uri) {

    }

    @Override
    public void onFragmentInteraction_cricket(int id) {
        String videoURL =videoids_cricket[id];
        Intent startYouTubeView = new Intent(MainActivity.this,testyoutubeplayer.class);
        startYouTubeView.putExtra("URL",videoURL);
        startActivity(startYouTubeView);
    }

    @Override
    public void onFragmentInteraction_1(String id) {

    }

    @Override
    public void onFragmentInteraction_2(String id) {

    }

    @Override
    public void onFragmentInteraction_3(String id) {

    }
}

