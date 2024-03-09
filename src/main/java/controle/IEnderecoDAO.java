package controle;

import java.util.ArrayList;

import modelo.Endereco;

public interface IEnderecoDAO {

	public int inserirEndereco(Endereco end);

	public ArrayList<Endereco> listarEnderecos();

	public int atualizarEndereco(Endereco end);

	public boolean removerEndereco(Endereco end);

	public Endereco buscarEnderecoPorCep(int cep);

}
