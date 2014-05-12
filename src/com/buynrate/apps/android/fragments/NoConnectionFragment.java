package com.buynrate.apps.android.fragments;

import com.buynrate.apps.android.activities.CalificarTiendaActivity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;

public class NoConnectionFragment extends Fragment implements OnClickListener {

	public static enum FragmentType {CALIFICAR_TIENDA};

	private FragmentType type;
	
	public void setFragmentType(FragmentType type) {
		this.type = type;
	}

	@Override
	public void onClick(View v) {
		switch (type) {
		case CALIFICAR_TIENDA:
			((CalificarTiendaActivity) getActivity()).load();
			break;
		}
	}
}
