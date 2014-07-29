package com.tabeyti.macrotrack.adapters;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service
{
	private static SyncAdapter sSyncAdapter = null;
	private static final Object sSyncAdapterLock = null;
	
	
	@Override
	public void onCreate()
	{
		synchronized (sSyncAdapterLock)
		{
			if (sSyncAdapter ==  null)
				sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
		}
	}	
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return sSyncAdapter.getSyncAdapterBinder();
	}

} // end class SyncService
