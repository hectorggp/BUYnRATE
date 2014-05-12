package com.buynrate.apps.android.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buynrate.apps.android.R;
import com.buynrate.apps.android.objects.Tienda;


public class CalificarTiendaFragment extends Fragment {

	private Tienda tienda;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.calificar_postear_fragment, container);
	}
	
	public void setData(Tienda tienda) {
		this.tienda = tienda;
	}
	
}
