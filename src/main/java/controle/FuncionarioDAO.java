package controle;

import modelo.Funcionario;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioDAO implements IFuncionarioDAO {
    private static FuncionarioDAO instancia;

    private FuncionarioDAO() {}

    public static FuncionarioDAO getInstance() {
        if (instancia == null) {
            instancia = new FuncionarioDAO();
        }
        return instancia;
    }

    public int inserirFuncionario(Funcionario end) {
        // Comando que será executado no SQL
        String SQL = "INSERT INTO funcionarios (cpf, nome_completo, data_nascimento, cargo) VALUES (?, ?, ?, ?)";

        // Abre a conexão e cria uma espécie de "ponte de conexão" com o MySQL
        Conexao con = Conexao.pegaConexao();
        Connection conBD = con.conectarConexao();

        try {
            // Cria um objeto JDBC para receber os valores do SQL
            PreparedStatement ps = conBD.prepareStatement(SQL);

            // Pega os valores e coloca em cada interrogação do comando SQL como parâmetro
            ps.setLong(1, end.getCpf());
            ps.setString(2, end.getNomeCompleto());
            ps.setDate(3, end.getDataNascimento());
            ps.setString(4, end.getCargo());

            // Executa o comando SQL
            return ps.executeUpdate();
        } catch (SQLException e) {
            // Envia um erro caso o try seja executado com falhas
            e.printStackTrace();
        } finally {
            // Fecha a conexão com o bando de dados // É uma boa prática
            con.fecharConexao();
        }

        return 0;
    }

    public ArrayList<Funcionario> listarFuncionarios() {
        // ArrayList para armazenar os resultados do SELECT
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();

        String SQL = "SELECT * FROM funcionarios";

        Conexao con = Conexao.pegaConexao();
        Connection conBD = con.conectarConexao();

        try {
            PreparedStatement ps = conBD.prepareStatement(SQL);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Cria objeto Java
                Funcionario end = new Funcionario();

                // Pega os valores de cada coluna do registro
                Long cpf = rs.getLong("cpf");
                String nomeCompleto = rs.getString("nome_completo");
                Date dataNascimento = rs.getDate("data_nascimento");
                String cargo = rs.getString("cargo");

                // Seta os valores no objeto java
                end.setCpf(cpf);
                end.setNomeCompleto(nomeCompleto);
                end.setDataNascimento(dataNascimento);
                end.setCargo(cargo);

                // Adiciona objeto no ArrayList
                funcionarios.add(end);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.fecharConexao();
        }

        return funcionarios;
    }

    public boolean atualizarFuncionario(Funcionario end) {
        String SQL = "UPDATE funcionarios SET nome_completo = ?, data_nascimento = ?, cargo = ?  WHERE cpf = ?";

        Conexao con = Conexao.pegaConexao();
        Connection conBD = con.conectarConexao();

        // Define o retorno como 0 (falso)
        int retorno = 0;

        try {
            PreparedStatement ps = conBD.prepareStatement(SQL);

            ps.setString(1, end.getNomeCompleto());
            ps.setDate(2, end.getDataNascimento());
            ps.setString(3, end.getCargo());
            ps.setLong(4, end.getCpf());

            // Executa o comando SQL
            retorno = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.fecharConexao();
        }

        return (retorno == 0 ? false : true); // (retorno == 0 ? false : true) // IF Ternário (Operador Condicional Ternário)
    }

    public boolean removerFuncionario(Funcionario end) {
        String SQL = "DELETE FROM funcionarios WHERE cpf = ?";

        Conexao con = Conexao.pegaConexao();
        Connection conBD = con.conectarConexao();

        int retorno = 0;

        try {
            PreparedStatement ps = conBD.prepareStatement(SQL);

            ps.setLong(1, end.getCpf());

            retorno = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.fecharConexao();
        }

        return (retorno == 0 ? false : true);
    }

    public Funcionario buscarFuncionarioPeloCpf(int cpf) {
        String SQL = "SELECT FROM funcionarios WHERE cpf = ?";

        Conexao con = Conexao.pegaConexao();
        Connection conBD = con.conectarConexao();

        Funcionario end = new Funcionario();
        try {
            PreparedStatement ps = conBD.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();

            ps.setLong(1, end.getCpf());

            while (rs.next()) {
                end.setCpf(rs.getLong("cpf"));
                end.setNomeCompleto(rs.getString("nome_completo"));
                end.setDataNascimento(rs.getDate("data_nascimento"));
                end.setCargo(rs.getString("cargo"));
            }

            return end;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            con.fecharConexao();
        }

        return end;
    }
}
