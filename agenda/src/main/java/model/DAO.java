package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class DAO {
	/** Módulo de conexão **/
	// Parâmetros de conexão

	/** The driver. */
	private String driver = "com.mysql.cj.jdbc.Driver";

	/** The url. */
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";

	/** The user. */
	private String user = "root";

	/** The password. */
	private String password = "abc@123";

	// Método de conexão

	private Connection conectar() {
		Connection con = null;
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/** CRUD CREATE **/
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome, fone, email) values (?,?,?)";
		try {
			// abrir conexão
			Connection con = conectar();
			// preparar a query para execução no BD
			PreparedStatement pst = con.prepareStatement(create);
			// Substituir os parametros (?) pelo conteudo das variaveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executar a query
			pst.executeUpdate();
			// Encerrar a conexão com o Banco
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/** CRUD READ **/
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome;";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();

			//laço abaixo será executado enquanto houver contatos
			while (rs.next()) {

				//variaveis de apoio que recebem os dados do banco

				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				//populando o ArrayList

				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/** CRUD UPDATE **/
	
	// Selecionar contato
	
	public void selecionarContato(JavaBeans contato) {
		String read2 = "SELECT * FROM contatos WHERE idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				//Setar as variaveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	// Editar o contato
	
	public void alterarContato(JavaBeans contato) {
		String create = "UPDATE contatos SET nome=?, fone=?, email=? WHERE idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());		
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CRUD DELETE **/
	public void deletarContato(JavaBeans contato) {
		String delete = "DELETE FROM contatos WHERE idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
