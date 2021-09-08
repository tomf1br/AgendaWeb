package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	// Parametros de conexao
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.44.83:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "dba";
	private String password = "Senac@123";

	/* Conexao */
	private Connection conectar() {
		Connection con = null; // "porta da geladeira fechada"
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	// teste de conexao
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
			// abrir conexao
			Connection con = conectar();
			// preparar a query(comando sql) substituindo os parametros(?,?,?)
			// pelo conteudo armazenado nas variaveis javabeans
			PreparedStatement pst = con.prepareStatement(create);
			// pst.setString (passo 8 - slide 21)
			// javabeans.getNome (passo 7 - slide 21)
			pst.setString(1, javabeans.getNome());
			pst.setString(2, javabeans.getFone());
			pst.setString(3, javabeans.getEmail());
			// executar a query (passo 9 - slide 21)
			pst.executeUpdate();
			// encerrar a conexao
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
	// Metodo para selecionar um contato
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			// pst.setString(passo 5 - slide 23) | contato.getIdcon() (Passo 4 - slide 23)
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery(); // passo 6 - slide 23
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

	// Metodo para alterar o contato
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(update);
			//Passos 16 e 17 do slide 23
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			//Passo 18 - executar o update no banco
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/* CRUD DELETE */
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate(); // passo 7 do slide 24
			con.close();
		} catch (Exception e) {
			System.out.println(e);
			
		}
	}

}
