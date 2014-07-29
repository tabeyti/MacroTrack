/**
 * Make each field in dayentry dialog selected when focus is gained for easy editing.
 * Update all data when input is put units, just just when focus is lost
 * 
 */

package com.tabeyti.macrotrack;

import android.accounts.Account;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.tabeyti.macrotrack.adapters.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private Account mAccount;
        
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.tabeyti.macrotrack.provider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "tabeyti.com";
    // The account name
    public static final String ACCOUNT = "default_account";

    public static MainActivity instance = null;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		instance = this;
		
		viewPager = (ViewPager)findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// retrieves server data
		FoodListManager.requestFoodList(this);
		
		// add tabs
		actionBar.addTab(actionBar.newTab().setText("Summary").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Day Entry").setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText("Food List").setTabListener(this));
		//actionBar.addTab(actionBar.newTab().setText("Plots").setTabListener(this));
		Tab tab = actionBar.newTab();
		tab.setText("Ham");
		//tab.setIcon(R.drawable.icon);
		actionBar.addTab(tab);
		//actionBar.addTab(actionBar.newTab().setIcon(R.drawable.icon));
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int pos)
			{
				actionBar.setSelectedNavigationItem(pos);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		 viewPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
	
	public static void error(String message)
	{
		Toast tst = Toast.makeText(instance.getApplicationContext(),  message,  Toast.LENGTH_LONG);
		tst.show();
		
	} // end Error()


}
