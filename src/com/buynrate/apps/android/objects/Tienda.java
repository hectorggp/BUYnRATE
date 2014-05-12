package com.buynrate.apps.android.objects;

import android.util.Log;

public class Tienda {

	private String nombre;
	
	public Tienda() {

	}

	public Tienda(String data) {
		Log.d("tienda", data.toString());
	}

	public String getNombre() {
		return nombre;
	}
}
