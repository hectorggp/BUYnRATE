package com.buynrate.apps.android;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import android.util.Log;

public class Conexion {
	private java.sql.Connection con;
    private java.sql.Statement stmt;
    public ResultSet resultado;
    
    String driver = "com.mysql.jdbc.Driver";
    
    String url = "jdbc:mysql://sql4.freesqldatabase.com:3306";
    String user = "sql434210";
    String pass = "qN3%sC7*";
    
    String bd =  "sql434210";
    
    /*CONEXION*/
    public void conectar() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	Class.forName(driver).newInstance();
		con = DriverManager.getConnection( url, user, pass );
        stmt = (Statement) con.createStatement();
    }
    public void desconectar() throws SQLException{
        stmt.close();
        con.close();
    }
    
    /*public String[] obtenerLista( String consulta ) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
    	ResultSet resultado = ejecutarConsulta( consulta );
    	ArrayList<String> aLista = new ArrayList<String>();
    	int contador = 0;
    	while( resultado.next() ){
    		contador++;
    		aLista.add( resultado.getString( 0 ) );
    	}
    	String[] lista = (String[]) aLista.toArray();//new String[ contador ];
    	resultado.close();
    	return lista;
    }
    public ResultSet ejecutarConsulta( String consulta ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	conectar();
    	desconectar();
    	return (ResultSet) stmt.executeQuery( consulta );
    }*/
    
    /*MANEJO DE LA INFORMACION*/
    
    public String ejecutarConsultaString( String consulta, int columnas ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//abre la conexion
    	conectar();
    	//ejecuta consulta
    	ResultSet filas = (ResultSet) stmt.executeQuery( consulta );
    	String resultado = "No hay informacion";
    	int contador = 0;
    	//lee la consulta
    	while ( filas.next() ){
    		if( contador == 0 )
    			resultado = "";
    		for( int i=1; i<=columnas; i++ ){
    			resultado = resultado + filas.getString( i ) + ( i!=columnas ? "\t\t": "" );
    		}
    		resultado = resultado + "\n";
    		contador++;
    	}
    	System.out.println( "obtenidos: " + contador );
    	//cierra la conexion
    	filas.close();
    	desconectar();
    	return resultado;
    } 
    
    //crear una tienda METODO QUE USA LUIS
    public void crearTienda( String nombre, String local, String mall, String tipo ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//inserta valores en tablas de las cuales depende
    	insertarOpcion( mall, "Mall" );
    	insertarOpcion( tipo, "Tipo" );
    	//obtiene los valores de los registros de opcion
    	String idMall = String.valueOf( getIdOptionTable( mall, "Mall" ) );
    	String idTipo = String.valueOf( getIdOptionTable( tipo, "Tipo" ) );
    	System.out.printf( "Mall: %s, %s\t\tTipo: %s %s\n", idMall, mall, idTipo, tipo );
    	//obtiene el siguiente id
    	String idTienda = String.valueOf( getNextId( "Tienda" ) );
    	//inserta
    	String insertar = String.format( "INSERT INTO %s.Tienda ( idTienda, nombre, local, Mall_idMall, Tipo_idTipo ) VALUES ( %s, '%s', '%s', %s, %s );", bd, idTienda, nombre, local, idMall, idTipo );
    	ejecutar( insertar, 1 );
    }
    
  //crea un nuevo usuario y lo devuelve
    public String crearUsuario() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//obtiene el numero de usuario
    	String idUsuario = String.valueOf( getNextId( "Usuario" ) );
    	//obtiene el usuario
    	String usuario = "user" + idUsuario;
    	//inserta el usuario
    	insertarOpcion( usuario, "Usuario" );
    	Log.d( null, "Usuario creado" );
    	return usuario;
    }
    
    //crear/actualizar un review: ( infoTienda, infoUsuario, infoReview )
    public void review( String nombreTienda, String local, String mall, String tipo, String usuario, int servicio, int producto, int precioCalidad, int instalaciones, int variedad, String comentario ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//obtiene los id de las tablas opcion del mall
    	String idMall = String.valueOf( getIdOptionTable( mall, "Mall" ) );
    	String idTipo = String.valueOf( getIdOptionTable( tipo, "Tipo" ) );
    	//obtiene el id de la tienda
    	String idTienda = String.valueOf( getIdTienda( nombreTienda, local, idMall, idTipo ) );
    	//obtiene el id del usuario
    	String idUsuario = String.valueOf( getIdOptionTable( usuario, "Usuario" ) );
    	
    	//verifica si existe el review
    	String consulta = String.format( "SELECT COUNT(*) FROM %s.Review WHERE Tienda_idTienda = %s AND Tienda_Tipo_idTipo = %s AND Usuario_idUsuario = %s;", bd, idTienda, idTipo, idUsuario );
    	LinkedList< String[] > lDatos = ejecutarConsulta( consulta, 1 );
    	String datos[] = lDatos.get( 0 );
    	int cantidad = Integer.parseInt( datos[0] );
    	System.out.println( "cantidad de review = " + cantidad );
    	String operacion = "";
    	if ( cantidad == 0 ){
    		//inserta
    		operacion = String.format( "INSERT INTO %s.Review ( servicio, producto, precioCalidad, instalaciones, variedad, comentario, Tienda_idTienda, Tienda_Tipo_idTipo, Usuario_idUsuario ) VALUES ( %d, %d, %d, %d, %d, '%s', %s, %s, %s );", bd, servicio, producto, precioCalidad, instalaciones, variedad, comentario, idTienda, idTipo, idUsuario );
    		System.out.println( "INSERTANDO review" );
    	}else{
    		//actualiza
    		operacion = String.format( "UPDATE %s.Review SET servicio=%d, producto=%d, precioCalidad=%d, instalaciones=%d, variedad=%d, comentario='%s' WHERE Tienda_idTienda=%s AND Tienda_Tipo_idTipo=%s AND Usuario_idUsuario = %s;", bd, servicio, producto, precioCalidad, instalaciones, variedad, comentario, idTienda, idTipo, idUsuario );
    		System.out.println( "ACTUALIZANDO review" );
    	}
    	System.out.println( operacion );
    	ejecutar( operacion, 1 );
    }
    
    //obtener id de una tienda: -1 = no encontrado
    private int getIdTienda( String nombreTienda, String local, String idMall, String idTipo ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	String consulta = String.format( "SELECT idTienda FROM %s.Tienda WHERE nombre LIKE '%s' AND local LIKE '%s' AND Mall_idMall = %s AND Tipo_idTipo = %s;", bd, nombreTienda, local, idMall, idTipo );
    	LinkedList< String[] > lDatos = ejecutarConsulta( consulta, 1 );
    	if( lDatos.size() > 0 ){
    		String datos[] = lDatos.get( 0 );
    		return Integer.parseInt( datos[0] );
    	}else
    		return -1;
    }
    
    //obtener id de tablas de opcion: -1 = no encontrado
    private int getIdOptionTable( String valor, String tabla ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	String consulta = String.format( "SELECT id%s FROM %s.%s WHERE %s LIKE '%s';", tabla, bd, tabla, tabla, valor );
    	LinkedList< String[] > lDatos = ejecutarConsulta( consulta, 1 );
    	System.out.printf( "%s\nretorno: %d valores\n", consulta, lDatos.size() );
    	if( lDatos.size() > 0 ){
    		String datos[] = lDatos.get( 0 );
    		return Integer.parseInt( datos[0] );
    	}else
    		return -1;
    }
    
    //inserta en tablas de opcion: -1 = ya existe, 0 = correcto
    public int insertarOpcion( String valor, String tabla ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//verifica si no existe
    	String consulta = String.format( "SELECT id%s FROM %s.%s WHERE %s LIKE '%s';", tabla, bd, tabla, tabla, valor );
    	LinkedList< String[] > lDatos = ejecutarConsulta( consulta, 1 );
    	if ( lDatos.size() == 0 ){
    		//lo inserta
        	String id = String.valueOf( getNextId( tabla ) );
        	String insertar = String.format( "INSERT INTO %s.%s ( id%s, %s ) VALUES ( %s, '%s' );", bd, tabla, tabla, tabla, id, valor );
        	ejecutar( insertar, 1 );
        	return 0;
    	}else
    		return -1;
    }
    
    //obtiene el siguiente ID de una tabla
    private int getNextId( String tabla ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	String consulta = String.format( "SELECT id%s FROM %s.%s ORDER BY id%s DESC LIMIT 1;", tabla, bd, tabla, tabla );
    	LinkedList< String[] > lDatos = ejecutarConsulta( consulta, 1 );
    	if( lDatos.size() > 0 ){
    		String datos[] = lDatos.get( 0 );
    		return Integer.parseInt( datos[0] ) + 1;
    	}else
    		return 1;
    }
    
    //ejecuta una consulta y devuelve una lista con los datos en array
    public LinkedList< String[] > ejecutarConsulta( String consulta, int columnas ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
    	//abre la conexion
    	conectar();
    	//crea contenedores de informacion
    	String datos[];
    	LinkedList< String[] > lDatos = new LinkedList< String[] >();
    	//ejecuta consulta
    	ResultSet filas = (ResultSet) stmt.executeQuery( consulta );
    	int contador = 0;
    	//lee la consulta
    	while ( filas.next() ){
    		datos = new String[ columnas ];
    		for( int i=1; i<=columnas; i++ ){
    			datos[ i-1 ] = filas.getString( i );
    		}
    		//agrega a la lista
    		lDatos.add( datos );
    		contador++;
    	}
    	System.out.println( consulta + "\n" + "obtenidos: " + contador );
    	//cierra la conexion
    	filas.close();
    	desconectar();
    	return lDatos;
    }
    
    //instruccion: sentencia sql modo: 1->insert, delete, update, 2->select
    public String ejecutar( String consulta, Integer modo ){
        String regs ="";
        try{
        	//conexion
        	conectar();
            //modo=1 -> insert,update,delete; modo=2 -> select
            if (modo == 1){
            	System.out.println( "ejecutando una operacion" );
            	stmt.executeUpdate( consulta );
            }else{
            	System.out.println( "ejecutando consulta" );
            	resultado = (ResultSet) stmt.executeQuery(consulta);
            }
            while (resultado != null && resultado.next()){
            	//regs = regs + "col1: " + resultado.getString(1) + " col2: "+ (resultado.getString(2)) + "\n";
            	regs = regs + "col1: " + resultado.getString(1)+ "\n";
            }
            try{
                if( resultado != null )
                    resultado.close();
                desconectar();
            }catch (SQLException e ){
                e.printStackTrace();
                return "error al cerrar conexion";
        	}
        }catch ( Exception ex ){
        	ex.printStackTrace();
            return "error general";
        }
        return regs;
    }
}