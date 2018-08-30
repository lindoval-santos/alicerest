package org.demoiselle.aliceREST.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.demoiselle.aliceREST.business.ConfigBody;

	public class ConfigDAO {

	private Connection con = null;
	
	private Statement stmt = null;
	private String query = "";

	private void setUpDB(){
  	      if (con != null)
  	    	  return;

	      Statement stmt = null;
	      try {
    		  Class.forName("org.sqlite.JDBC");
          	  //con = DriverManager.getConnection("jdbc:sqlite:file:aux.db?mode=memory&cache=shared");
    		  con = DriverManager.getConnection("jdbc:sqlite:file:alice.db");
	          stmt = con.createStatement();
	          if(stmt.execute("SELECT * FROM Tb_Config")){
	              stmt.close();
	        	  return;
	          }
	      } catch ( Exception e ) {
	          String sql = "CREATE TABLE Tb_Config " +
	   		  	   "(ID INTEGER PRIMARY KEY autoincrement," +
	   		  	   " nome			Varchar     NOT NULL, " + 
	   		  	   " valor          Varchar     NOT NULL)";

				     try {
						stmt.executeUpdate(sql);
						sql =  "INSERT INTO Tb_Config (nome, valor) VALUES('load.context', '/Bots/Alice/config/context.xml');";
						stmt.executeUpdate(sql);
						sql =  "INSERT INTO Tb_Config (nome, valor) VALUES('load.splitters', '/Bots/Alice/config/splitters.xml');";
						stmt.executeUpdate(sql);
						sql =  "INSERT INTO Tb_Config (nome, valor) VALUES('load.substitutions', '/Bots/Alice/config/substitutions.xml');";
						stmt.executeUpdate(sql);
						sql =  "INSERT INTO Tb_Config (nome, valor) VALUES('load.learned', '/Bots/Alice/Learn/Learned.aiml');";
						stmt.executeUpdate(sql);
						sql =  "INSERT INTO Tb_Config (nome, valor) VALUES('load.cerebrobase', '/Bots/Alice/cerebro/');";
						stmt.executeUpdate(sql);
					    stmt.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
	      }
	}
	
	public String insert ( ConfigBody conf){
		setUpDB();
		String result = "";
		try{
			ConfigBody c = load(conf); 
			if((c.getNome()!= null) && (!"".equals(c.getNome()))){
				return update(conf);
			}
			String nome = conf.getNome();
			String valor = conf.getValor();
			stmt = con.createStatement();
			query =  "INSERT INTO Tb_Config (nome, valor) VALUES('" + 
					  nome + "', '" + valor + "');";
			stmt.executeUpdate(query);
		    stmt.close();
		    result = "Configuração '" + nome + "' foi inserida";
		    return result;
		}catch(SQLException se){
			System.err.println( se.getClass().getName() + ": " + se.getMessage() );
			return "Erro";
		}
	}
	
	public String update ( ConfigBody c ){
		setUpDB();
		String result = "";
		try{
			stmt = con.createStatement();
			query =  "UPDATE Tb_Config SET valor = '" + c.getValor() + "' WHERE nome = '" + 
					  c.getNome() + "';";
			stmt.executeUpdate(query);
		    stmt.close();
		    result = "Configuração '" + c.getNome() + "' foi alterada.";
		    return result;
		}catch(SQLException se){
			System.err.println( se.getClass().getName() + ": " + se.getMessage() );
			return "Erro";
		}
	}
	
	public ConfigBody load ( ConfigBody conf){
		setUpDB();
		ConfigBody c = null;
		try{

			String nome = conf.getNome();
			stmt = con.createStatement();
			query =  "SELECT * FROM Tb_Config WHERE nome = '" + 
					  nome + "';";
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				c = new ConfigBody(rs.getString("nome"),rs.getString("valor"));
			}else{
				c = new ConfigBody();
			}
			rs.close();
		    stmt.close();
		}catch(SQLException se){
			System.err.println( se.getClass().getName() + ": " + se.getMessage() );
			return new ConfigBody();
		}
		return c;
	}
	
	public List<ConfigBody> selectAll(){
		setUpDB();
		try{
			stmt = con.createStatement();
			List<ConfigBody> result = new ArrayList<ConfigBody>();
			query =  "SELECT * FROM Tb_Config ORDER BY nome;";
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				ConfigBody c = new ConfigBody(rs.getString("nome"),rs.getString("valor"));
				result.add(c);
			}
		    rs.close();
		    stmt.close();
			return result;

		}catch(SQLException se){
			return null;
		}
	}
	
}
