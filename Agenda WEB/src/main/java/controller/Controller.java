package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

// Recebimento de requisicoes
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Criar objetos para acessar os metodos publicos das classes Javabeans e DAO
	JavaBeans javabeans = new JavaBeans();
	DAO dao = new DAO();

	public Controller() {
		super();
	}

	/**
	 * Metodo principal do Servlet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Teste de conexao com o banco de dados
		dao.testarConexao();
		// Encaminhamento das requisicoes
		String action = request.getServletPath(); // armazena a requisicao atual
		System.out.println("Requisição: " + action); // apoio ao entendimento e verificacao de erros
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		}
	}

	// Adicionar contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento dos dados do formulario
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));

		// Receber os dados do form e armazenar temporariamente nas variaveis javabeans
		// javabeans.setNome (Passo 5 - slide 21)
		// request.getParameter("nome") (Passo 4 - slide 21)
		javabeans.setNome(request.getParameter("nome"));
		javabeans.setFone(request.getParameter("fone"));
		javabeans.setEmail(request.getParameter("email"));

		// executar o metodo inserirContato (DAO) passando javabeans
		dao.inserirContato(javabeans); // Passo 6 do slide 21

		// redirecionar para a pagina agenda.jsp (passo 10 do slide 21)
		response.sendRedirect("main");
	}

	// Listar todos os contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// executar o metodo listarContatos() DAO - Passo 2 - Slide 22
		// O objeto lista é um vetor que recebe o retorno(JavaBeans) do metodo
		// listarContatos()
		// lista - Passo 6 do slide 22
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// teste de recebimento
		/*
		 * for (int i = 0; i < lista.size(); i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail()); }
		 */
		// Despachar a lista de contatos(vetor) para o documento agenda.jsp - Passo 7 -
		// slide 22
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	// Editar um contato
	// passo 1 - Selecionar o contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Passo 1 do slide 23
		String idcon = request.getParameter("idcon");
		// teste de recebimento do parametro
		System.out.println(idcon);
		// Setar a variavel idcon (JavaBeans) - Passo 2 do slide 23
		javabeans.setIdcon(idcon);
		// Executar o metodo seleconarContato (DAO) - Passo 3 do slide 23
		dao.selecionarContato(javabeans);
		// teste de recebimento
		// System.out.println(javabeans.getIdcon());
		// System.out.println(javabeans.getNome());
		// System.out.println(javabeans.getFone());
		// System.out.println(javabeans.getEmail());

		// Passo 10 - slide 23 "Despachar" os dados das variaveis JavaBeans para
		// editar.jsp
		request.setAttribute("idcon", javabeans.getIdcon());
		request.setAttribute("nome", javabeans.getNome());
		request.setAttribute("fone", javabeans.getFone());
		request.setAttribute("email", javabeans.getEmail());
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	// Passo 2 - Editar o contato
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Passos 13 e 14 do slide 23 (receber os dados do formulario e setar JavaBeans)
		javabeans.setIdcon(request.getParameter("idcon"));
		javabeans.setNome(request.getParameter("nome"));
		javabeans.setFone(request.getParameter("fone"));
		javabeans.setEmail(request.getParameter("email"));
		// Passo 15 - slide 23 (executar o metodo alterarContato)
		dao.alterarContato(javabeans);
		// Passo 19 -redirecionar para o agenda.jsp atualizando a lista
		response.sendRedirect("main");
	}
	//Remover Contato
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Passo 3 do slide 24 (setar o idcon em JavaBeans)
		javabeans.setIdcon(request.getParameter("idcon"));
		//Passo 4 slide 24 - executar o metodo deletarContato passando Idcon
		dao.deletarContato(javabeans);
		//passo 8 do slide 24
		response.sendRedirect("main");
	}
}
