package controle;

import modelo.Funcionario;

import java.util.ArrayList;

public interface IFuncionarioDAO {
    public int inserirFuncionario(Funcionario end);

    public ArrayList<Funcionario> listarFuncionarios();

    public boolean atualizarFuncionario(Funcionario end);

    public boolean removerFuncionario(Funcionario end);

    public Funcionario buscarFuncionarioPeloCpf(int cpf);
}
