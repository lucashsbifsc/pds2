package controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao { // Connection

	private static final String USERNAME = "root";
	private static final String SENHA = "aluno";
	private static final String BD = "nome_banco";
	private Connection con; // jdbc
	private static Conexao instancia; // singleton
	
	private Conexao() {} // construtor privado
	
	/**
	 * Metodo singleton
	 * 
	 * @return conexao
	 */
	public static Conexao getInstancia() {
		if(instancia == null) {
			instancia = new Conexao();
		}
		
		return instancia;
	}
	
	/**
	 * Metodo que faz a conexao com o MySQL
	 * 
	 * @return con
	 */
	public Connection conectar() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/"+ BD + "?serverTimezone=UTC", USERNAME, SENHA);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}

	/**
	 * Fecha a conexao com o MySQL
	 * 
	 * @return true ou false; dependendo do resultado
	 */
	public boolean fecharConexao() {
		try {
			con.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
