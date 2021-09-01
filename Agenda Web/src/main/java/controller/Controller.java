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

//Recebimento de requisicoes 
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	JavaBeans javabeans = new JavaBeans();
	DAO dao = new DAO();

	public Controller() {
		super();

	}

	// Metodo principal do Servlet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Teste de conexao com o banco de dados
		dao.testarConexao();
		// encaminhamento das requisicoes
		String action = request.getServletPath(); // armazena a requisicao atual
		System.out.println("Requisição: " + action); // apoio ao entendimento e verificacao de erros
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		}
	}

	// Adicionar contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// teste de recebimento do dados do formulario
		// System.out.println(request.getParameter("nome"));
		// System.out.println(request.getParameter("fone"));
		// System.out.println(request.getParameter("email"));

		// receber os dados do formulario e armazenar temporariamente nas variaveis
		// javabeans
		// javabeans,setNome (passo 5 do slide 21)
		// request.getParameter("nome") (Passo 4 do slide 21)
		javabeans.setNome(request.getParameter("nome"));
		javabeans.setFone(request.getParameter("fone"));
		javabeans.setEmail(request.getParameter("email"));

		// executar o metodo inserir contato da classe DAO passando JavaBeans
		dao.inserirContato(javabeans); // passo 6 do slide 21

		// redirecionar para a pagina agenda.jsp
		response.sendRedirect("main");

	}

	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// executar o metodo listarContatos() DAO - Passo 2 - Slide 22
		// O objeto lista é um vetor que recebe o retorno do metodo listarContatos()
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

	// editar um contato
	// selecionar um contato
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Passo 1 do slide 23
		String idcon = request.getParameter("idcon");
		// teste de recebimento do parametro
		System.out.println(idcon);
		// setar a variavel idcon JavaBeans - Passo 2 do slide 23
		javabeans.setIdcon(idcon);
		// executar o metodo selecionarContato (DAO) - Passo 3 do slide 23
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
}
