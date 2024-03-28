package com.ifscgaspar.sistemapinkmankart.controle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    /**
     * - NÃO USAR USERNAME E SENHA EM EMPRESA
     * - CONSTANTES EM JAVA
     * - PADRONIZAR MAIÚSCULO
     */
    private static final String USERNAME = "root";
    private static final String SENHA = "aluno";
    private static final String BD = "diagrama_karts";
    private Connection con; // JDBC
    private static Conexao instancia; // SINGLETON

    private Conexao() {} // CONSTRUTOR PRIVADO

    /**
     * Método do singleton
     *
     * @return conexao
     */
    public static Conexao pegaConexao() {
        if(instancia == null) {
            instancia = new Conexao();
        }

        return instancia;
    }

    /**
     * Tenta estabelecer conexão com a base de dados selecionada
     *
     * @return con
     */
    public Connection conectarConexao() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/"+ BD + "?serverTimezone=UTC", USERNAME, SENHA);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return con;
    }

    /**
     * Fecha a conexão com o MySQL
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
