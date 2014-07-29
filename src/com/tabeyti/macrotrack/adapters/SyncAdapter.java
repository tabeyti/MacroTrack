package com.tabeyti.macrotrack.adapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

public class SyncAdapter extends AbstractThreadedSyncAdapter
{
	ContentResolver mContentResolver;
	
	/**
	 * Sets up the sync adapter.
	 * @param context
	 * @param autoInitialize
	 */
	public SyncAdapter(Context context, boolean autoInitialize)
	{
		super(context, autoInitialize);
		// TODO Auto-generated constructor stub
	}
	
	
	public SyncAdapter(Context context,
			boolean autoInitialize,
			boolean allowParallelSyncs)
	{
		super(context, autoInitialize, allowParallelSyncs);
		mContentResolver = context.getContentResolver();
	}
	
	@Override
	public void onPerformSync(
			Account account,
			Bundle extras,
			String authority,
			ContentProviderClient provider,
			SyncResult syncResult)
	{
		// connect to server
		
		// download data
		
		// handle conflicts
		
		// clean up
	}

} // end class SyncAdapter
