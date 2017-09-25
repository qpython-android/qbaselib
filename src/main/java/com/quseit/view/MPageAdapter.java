//package com.quseit.view;
//
//import java.util.ArrayList;
//
//import android.content.Context;
//import android.os.Parcelable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.View;
//
//
//import com.viewpagerindicator.TitleProvider;
//import com.quseit.config.CONF;
//
//public class MPageAdapter extends PagerAdapter
//    implements TitleProvider
//{
//	private static final String TAG = "MLocalAdapter";
//
//
//    @SuppressWarnings("unused")
//	private final Context context;
//    private ArrayList<View> views;
//    private final String[] titles;
//
//    public MPageAdapter( Context context, ArrayList<View> views,String[] titles )
//    {
//        this.context = context;
//        this.views = views;
//        this.titles = titles;
//    }
//
//    @Override
//    public String getTitle( int position )
//    {
//        return titles[ position ];
//    }
//
//    @Override
//    public int getCount()
//    {
//        return titles.length;
//    }
//
//    @Override
//    public Object instantiateItem( View pager, int position )
//    {
//    	if (CONF.DEBUG) Log.d(TAG, "instantiateItem");
//
//    	//LayoutInflater inflater = LayoutInflater.from(activity);
//
//        //DownloaderListView pagedView = (DownloaderListView) inflater.inflate(R.layout.md_paged,null);
//        //pagedView.render(position);
//    	View pagedView = (View)this.views.get(position);
//	    ((ViewPager)pager).addView(pagedView,0);
//	    return pagedView;
//    }
//
//    @Override
//    public void destroyItem( View pager, int position, Object view )
//    {
//    	if (CONF.DEBUG) Log.d(TAG, "descroyItem");
//        ((ViewPager)pager).removeView( (View)view );
//    }
//
//    @Override
//    public boolean isViewFromObject( View view, Object object )
//    {
//        return view.equals( object );
//    }
//
//    @Override
//    public void finishUpdate( View view ) {}
//
//    @Override
//    public void restoreState( Parcelable p, ClassLoader c ) {}
//
//    @Override
//    public Parcelable saveState() {
//        return null;
//    }
//
//    @Override
//    public void startUpdate( View view ) {}
//}
