package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controle.Conexao;
import modelo.Endereco;

/**
 * DAO = Data access object objeto de acesso a dados
 * 
 * Serve para trocar dados com a tabela Endereco
 *
 */
public class EnderecoDAO implements IEnderecoDAO {

	/*
	 * Variavel padrao Singleton
	 */
	private static EnderecoDAO instancia;

	/*
	 * Construtor privado (padrao Singleton)
	 */
	private EnderecoDAO() {
	}

	/*
	 * Metodo para instanciar (padrao Singleton)
	 */
	public static EnderecoDAO getInstancia() {

		if (instancia == null) {
			instancia = new EnderecoDAO();
		}
		return instancia;
	}

	/**
	 * Utilizar essa forma de INSERT somente quando a chave primaria é
	 * AUTO_INCREMENT no BD
	 * 
	 */
	public int insertEndereco2(Endereco end) {

		String SQL = "INSERT INTO enderecos (rua) VALUES (?)";

		Conexao con = Conexao.getInstancia();
		Connection conBD = con.conectar();

		int chavePrimariaGerada = Integer.MIN_VALUE;

		try {
			PreparedStatement ps = conBD.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, end.getRua());

			ResultSet rs = ps.executeQuery();
			if (rs != null) {
				chavePrimariaGerada = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.fecharConexao();
		}

		return chavePrimariaGerada;

	}

	/**
	 * Usar essa forma de INSERT somente quando a chave primaria NÃO É
	 * auto_increment, ou seja, quando se conhece a chave primaria
	 * 
	 */
	public int inserirEndereco(Endereco end) {

		// Comando SQL a ser executado
		String SQL = "INSERT INTO enderecos (cep, rua) VALUES (?, ?)";

		// Abre a conexao e cria a "ponte de conexao" com MYSQL
		Conexao con = Conexao.getInstancia();
		Connection conBD = con.conectar();

		try {
			PreparedStatement ps = conBD.prepareStatement(SQL);

			ps.setString(1, end.getCep());
			ps.setString(2, end.getRua());

			return ps.executeUpdate(); // executa sem esperar retorno do BD

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Fecha a conexao
			con.fecharConexao();
		}

		return 0;
	}

	public ArrayList<Endereco> listarEnderecos() {

		// Arraylist para armazenar resultado do select
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();

		// Comando SQL a ser executado
		String SQL = "SELECT * FROM enderecos";

		// Cria a "ponte de conexao" com MYSQL
		Conexao con = Conexao.getInstancia();
		Connection conBD = con.conectar();

		try {
			PreparedStatement ps = conBD.prepareStatement(SQL);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				// Criar objeto
				Endereco end = new Endereco();

				// Pega os valores de cada coluna do registro
				String rua = rs.getString("rua");
				String cep = rs.getString("cep");

				// Seta os valores no obj java
				end.setCep(cep);
				end.setRua(rua);

				// Adiciona obj no arraylist
				enderecos.add(end);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.fecharConexao();
		}

		return enderecos;
	}

	public boolean atualizarEndereco(Endereco end) {
		String SQL = "UPDATE";
		
		Conexao con = Conexao.getInstancia();
		Connection conBD = con.conectar();
		
		PreparedStatement ps = conBD.prepareStatement(SQL);
		
		return false;
	}

	public boolean removerEndereco(Endereco end) {
		// TODO Auto-generated method stub
		return false;
	}

	public Endereco buscarEnderecoPorCep(int cep) {
		// TODO Auto-generated method stub
		return null;
	}

}
