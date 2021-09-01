package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	// Parametros de conexao

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.0.0.199:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "dba";
	private String password = "Senac@123";

	/* Conexao */
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

	public void testarConexao() {
		try {
			Connection con = conectar();
			System.out.println("Conectado: " + con);
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/* CRUD CREATE */
	public void inserirContato(JavaBeans javabeans) {
		String create = "insert into contatos(nome,fone,email) values(?,?,?)";
		try {
			// abrir a conexao
			Connection con = conectar();
			// preparar a query substituindo os parametros (?,?,?) pelo conteudo armazenado
			// nas variaveis javabeans
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, javabeans.getNome());
			pst.setString(2, javabeans.getFone());
			pst.setString(3, javabeans.getEmail());
			// executar a query (passo 9 do slide 21)
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	/* CRUD READ */
	// metodo com retorno criado com a referencia ao vetor dinamico

	public ArrayList<JavaBeans> listarContatos() {
		// a linha abaixo cria um vetor dinamico
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery(); // Passo 3 - slide 22
			while (rs.next()) {
				// recebimento dos dados do banco - Passo 4 - slide 22
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// setar as variáveis JavaBeans - Passo 5 - slide 22
				// a linha abaixo seta o construtor 2 do JavaBeans (vetor)
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return (null);
		}
	}

	/* CRUD UPDATE */
	// metodo para selecionar um contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			// pst.setString(passo 5 - slide 23) | contato.getIdcon() (Passo 4 - slide 23)
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery(); // passo 6 - slide 22
			while (rs.next()) {
				// contato.set(passo 8 - slide 23) | rs.getString() (Passo 7 - slide 23)
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

	/* CRUD DELETE */
}
