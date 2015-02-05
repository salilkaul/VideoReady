package demovideoready.sampleapp.com.demovideoready;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TabHost;
import android.widget.TabWidget;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChannelFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TabHost mtabHost;
    private FrameLayout itemLayout;
    private FrameLayout itemLayout2;
    private FrameLayout itemLayout3;
    private TabWidget mTabWidget;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChannelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChannelFragment newInstance(String param1, String param2) {
        ChannelFragment fragment = new ChannelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ChannelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_channel, container, false);

        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_channel, container, false);
        mtabHost = (TabHost) rootview.findViewById(android.R.id.tabhost);
        itemLayout = (FrameLayout) rootview.findViewById(R.id.ItemFragment);
        itemLayout2 = (FrameLayout) rootview.findViewById(R.id.ItemFragment2);
        itemLayout3 = (FrameLayout) rootview.findViewById(R.id.ItemFragment3);
        mtabHost.setup();
        mtabHost.addTab(mtabHost.newTabSpec("Comedy").setIndicator("Comedy Header").setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                ItemFragment ilf = ItemFragment.newInstance("First Fragment", "First Fragment");
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.ItemFragment, ilf).commit();
                return itemLayout;
            }
        }));

        mtabHost.addTab(mtabHost.newTabSpec("Sports").setIndicator("Sports Header").setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                ItemFragment2 ilf2 = ItemFragment2.newInstance("Second Fragment", "Second Fragment");
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.ItemFragment2, ilf2).commit();
                return itemLayout2;
            }
        }));
        mtabHost.addTab(mtabHost.newTabSpec("Songs").setIndicator("Songs Header").setContent(new TabHost.TabContentFactory() {

            @Override
            public View createTabContent(String tag) {
                ItemFragment3 ilf3 = ItemFragment3.newInstance("Third Fragment", "Third Fragment");
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.ItemFragment3, ilf3).commit();
                return itemLayout3;
            }
        }));


        mTabWidget = (TabWidget) rootview.findViewById(android.R.id.tabs);
        return rootview;

    }


}
