package visao;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controle.EnderecoDAO;
import modelo.Endereco;

public class TelaEndereco extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRua;
	private JTextField txtCep;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaEndereco frame = new TelaEndereco();
					// maximiza a janela
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaEndereco() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 123);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JLabel lblNewLabel = new JLabel("Rua");
		contentPane.add(lblNewLabel);

		txtRua = new JTextField();
		contentPane.add(txtRua);
		txtRua.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Cep");
		contentPane.add(lblNewLabel_1);

		txtCep = new JTextField();
		contentPane.add(txtCep);
		txtCep.setColumns(10);

		JButton btnCadEndereco = new JButton("Cadastrar");
		btnCadEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String cep = txtCep.getText();
				String rua = txtRua.getText();

				if (cep.isEmpty() || rua.isEmpty()) {
					// Janela de erro
				} else {
					Endereco end = new Endereco();
					end.setCep(cep);
					end.setRua(rua);

					EnderecoDAO dao = EnderecoDAO.getInstancia();
					int retorno = dao.inserirEndereco(end);

					if (retorno == 0) {
						// Janela de erro
					} else {
						// Janela de sucesso
					}

				}
			}
		});
		contentPane.add(btnCadEndereco);
	}

}
