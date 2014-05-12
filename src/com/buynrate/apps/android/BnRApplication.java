package com.buynrate.apps.android;

import android.app.Application;
import android.util.Log;

public class BnRApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d("BnRApplicaton", "Applicatoni Start!");
	}
	
}
