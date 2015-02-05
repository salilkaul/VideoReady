package demovideoready.sampleapp.com.demovideoready;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ig on 29/1/15.
 */
public class TabsViewPagerFragmentActivity  extends FragmentActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener,ItemFragment.OnFragmentInteractionListener_1,ItemFragment2.OnFragmentInteractionListener_2 {
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabsViewPagerFragmentActivity.TabInfo>();
    private PagerAdapter mPagerAdapter ;



    @Override
    public void onFragmentInteraction_1(String id) {

    }

    @Override
    public void onFragmentInteraction_2(String id) {

    }

    private class TabInfo{
        String tag ;
        Class<?> clazz ;
        private Bundle args ;

        TabInfo(String str,Class<?> clasone,Bundle bun){
            this.tag =str ;
            this.clazz=clasone;
            this.args=bun;
        }


    }
 class TabFactory implements TabHost.TabContentFactory{
     private Context privateContext ;
     public TabFactory(Context ctx){
            privateContext=ctx ;
     }

     public View createTabContent(String tag) {
         View v = new View(privateContext);
         v.setMinimumWidth(0);
         v.setMinimumHeight(0);
         return v;
     }


 }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout
        setContentView(R.layout.tabs_viewpager_layout);
        // Initialise the TabHost
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
    }

    private void intialiseViewPager(){
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(Fragment.instantiate(this, ItemFragment.class.getName()));
        fragments.add(Fragment.instantiate(this,ItemFragment2.class.getName()));
        fragments.add(Fragment.instantiate(this,ItemFragment3.class.getName()));
         this.mPagerAdapter =  new PagerAdapter(super.getSupportFragmentManager(), fragments);
        this.mViewPager = (ViewPager)super.findViewById(R.id.viewpager);
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    private void initialiseTabHost(Bundle bun){
        mTabHost=(TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo=null;
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("Tab 1"), ( tabInfo = new TabInfo("Tab1", ItemFragment.class, bun)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("Tab 2"), ( tabInfo = new TabInfo("Tab2", ItemFragment2.class, bun)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabsViewPagerFragmentActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("Tab 3"), ( tabInfo = new TabInfo("Tab3", ItemFragment3.class, bun)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        //this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    private static void AddTab(TabsViewPagerFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }
    public void onTabChanged(String tag) {
        //TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        // TODO Auto-generated method stub
        this.mTabHost.setCurrentTab(position);
    }

    /* (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        // TODO Auto-generated method stub

    }
}