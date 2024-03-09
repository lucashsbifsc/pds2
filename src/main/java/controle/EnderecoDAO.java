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
	
	
	/**
	 * Tem que possuir a chave primária (ID, CPF, CEP, etc.)
	 * 
	 * Atualiza um registro existente no banco d dados.
	 * 
	 * O objeto passado como parâmetro já deve possuir os NOVOS valores
	 * porém deve possuir a chave primária do registro que se deseja atualizar.
	 */
	public int atualizarEndereco(Endereco end) {
		// Comando SQL a ser executado
		String SQL = "UPDATE enderecos SET rua = ? WHERE cep = ?";
		
		// Instancia a conexão
		Conexao con = Conexao.getInstancia();
		// Cria uma "ponte de conexão" com o banco de dados
		Connection conBD = con.conectar();
		// Define o retorno como 0 (falso)
		int retorno = 0;
		
		try {
			// Cria um objeto Java para receber os valores do SQL
			PreparedStatement ps = conBD.prepareStatement(SQL);
			
			// Pega os valores e coloca em cada interrogação do comando SQL como parâmetro
			ps.setString(1, end.getRua());
			ps.setString(2, end.getCep());
			
			// Executa o comando SQL
			retorno = ps.executeUpdate();
			
		} catch (SQLException e) {
			// Envia um erro caso o try seja executado com falhas
			e.printStackTrace();
		} finally {
			// Fecha a conexão com o bando de dados // É uma boa prática
			con.fecharConexao();
		}
		
		return retorno; // (retorno == 0 ? false : true) // IF Ternário (Operador Condicional Ternário)
	}

	public boolean removerEndereco(Endereco end) {
		String SQL = "DELETE FROM enderecos WHERE cep = ?";
		
		Conexao con = Conexao.getInstancia();
		Connection conBD = con.conectar();
		
		int retorno = 0;
		
		try {
			PreparedStatement ps = conBD.prepareStatement(SQL);
			
			ps.setString(1, end.getCep());
			
			retorno = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			con.fecharConexao();
		}
		
		
		return (retorno == 0 ? false : true);
	}

	public Endereco buscarEnderecoPorCep(int cep) {
		// TODO Auto-generated method stub
		return null;
	}

}
