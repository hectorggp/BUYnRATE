package com.buynrate.apps.android.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.buynrate.apps.android.R;
import com.buynrate.apps.android.fragments.CalificarTiendaFragment;
import com.buynrate.apps.android.fragments.NoConnectionFragment;
import com.buynrate.apps.android.fragments.NoConnectionFragment.FragmentType;
import com.buynrate.apps.android.objects.Tienda;
import com.buynrate.apps.android.utils.Conexion;

public class CalificarTiendaActivity extends FragmentActivity {

	private long idtienda;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.fragment_charging);
	}
	
	public void load() {
		idtienda = getIntent().getLongExtra("id_tienda", -1);
		if (idtienda >= 0) {
			// cargar datos
			new CargarDatosTienda().execute();
		}
	}
	
	private class	CargarDatosTienda extends AsyncTask<Void, Void, Tienda> {

		@Override
		protected Tienda doInBackground(Void... params) {
			Conexion con = new Conexion();
			String res = con.ejecutar("SELECT * FROM Tienda Where idTienda = " + idtienda, 2);
			if (res != null)
				return new Tienda(res);
			return null;
		}
	
		@Override
		protected void onPostExecute(Tienda result) {
			Fragment fragment ;
			if (result != null) {
				fragment = new CalificarTiendaFragment();
				((CalificarTiendaFragment) fragment).setData(result);
				getActionBar().setTitle(result.getNombre());
			} else {
				fragment = new NoConnectionFragment();
				((NoConnectionFragment) fragment).setFragmentType(FragmentType.CALIFICAR_TIENDA);
			}
			
			// cambiar contenido
			getSupportFragmentManager()
			.beginTransaction()
			.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
			.replace(R.id.frame_content, fragment)
			.commit();
		}
	}
}
