package com.buynrate.apps.android.activities;

import java.sql.SQLException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buynrate.apps.android.R;
import com.buynrate.apps.android.utils.Conexion;

public class MainActivity extends ActionBarActivity {

	Conexion conexion;
	TextView mall;
	TextView local;
	TextView tipo;
	TextView comentario;
	RatingBar servicio;
	RatingBar producto;
	RatingBar precio;
	RatingBar instalaciones;
	RatingBar variedad;
	private Button btnSubmit;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
    }

//    public void addListenerOnButton(View v) {
//    	mall = (TextView)findViewById(R.id.mall); 
//    	servicio = (RatingBar) findViewById(R.id.servicio);
//    	btnSubmit = (Button) findViewById(R.id.btnSubmit);
//     
//    	       
//    			int Servicio= (int)servicio.getRating();
//    			Toast.makeText(MainActivity.this,
//    				String.valueOf(Servicio)+" "+mall.getText().toString(),
//    					Toast.LENGTH_SHORT).show();
//     
//    		}
     

     
      
    
    
    public void Actualizar_Review(View v){
    	try{
            //asigno el control a la variable global
            mall = (TextView)findViewById(R.id.mall);
            local = (TextView)findViewById(R.id.local);
            tipo = (TextView)findViewById(R.id.tipo);
            comentario = (TextView)findViewById(R.id.comentario);
    	
        servicio = (RatingBar) findViewById(R.id.servicio);
        producto = (RatingBar) findViewById(R.id.producto);
        precio = (RatingBar) findViewById(R.id.precio);
        instalaciones = (RatingBar) findViewById(R.id.instalaciones);
        variedad = (RatingBar) findViewById(R.id.variedad);
        
    		
    		
        int Servicio= (int)servicio.getRating();
        int Producto= (int)producto.getRating();
        int Precio= (int)precio.getRating();
        int Instalaciones= (int)instalaciones.getRating();
        int Variedad= (int)variedad.getRating();
        
  
    	btnSubmit = (Button) findViewById(R.id.btnSubmit);
    	
	
    	
    	try {
    		Toast.makeText(MainActivity.this,
    				mall.getText().toString()+" "+local.getText().toString()+" "+tipo.getText().toString()+" "+comentario.getText().toString()+" "+ Servicio+" "+Producto+" "+Precio+" "+Instalaciones+" "+Variedad ,
    					Toast.LENGTH_LONG).show();
    		//me tira null no se porque
			conexion.review(mall.getText().toString(), local.getText().toString(), mall.getText().toString(), tipo.getText().toString(), "user5", Servicio, Producto, Precio, Instalaciones, Variedad, comentario.getText().toString());
		} catch (InstantiationException e) {
			Toast.makeText(MainActivity.this,
    				"ERROR DE INSTANCIA",
    					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Toast.makeText(MainActivity.this,
    				"ERROR DE ACCESO",
    					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			Toast.makeText(MainActivity.this,
    				"ERROR DE CLASE",
    					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (SQLException e) {
			Toast.makeText(MainActivity.this,
    				"ERROR DE SQL",
    					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}}catch(Exception e){
			Toast.makeText(MainActivity.this,
    				e.toString(),
    					Toast.LENGTH_SHORT).show();
			
		}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
        
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
        	super.onActivityCreated(savedInstanceState);
        	
            getActivity().findViewById(R.id.btnSubmit).setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View arg0) {
    				String id = ((EditText) getActivity().findViewById(R.id.mall)).getText().toString();
    				Intent intent = new Intent(getActivity(), CalificarTiendaActivity.class);
    				intent.putExtra("id_tienda", Long.valueOf(id));
    				startActivity(intent);
    			}
    		});
        }
    }

}
