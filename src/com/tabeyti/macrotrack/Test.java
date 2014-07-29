package com.tabeyti.macrotrack;

import com.tabeyti.macrotrack.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Test extends Fragment
{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) 
	{
 
        View rootView = inflater.inflate(R.layout.test, container, false);
         
        return rootView;
    }
	
}
