<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="model.JavaBeans"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="utf-8">
<title>Agenda de contatos</title>
<link rel="icon" href="imagens/favicon.png">
<link rel="stylesheet" href="style.css">
</head>
<body>
	<h1>Agenda de contatos</h1>
	<!-- Passo 1 -->
	<a href="novo.html" class="Botao1">Novo contato</a>
	<a href="" class="Botao2">Relatório</a>
	<table id="tabela">
		<!-- estatico -->
		<thead>
			<tr>
				<th>Id</th>
				<th>Nome</th>
				<th>Fone</th>
				<th>E-mail</th>
				<th>Opções</th>
			</tr>
		</thead>
		<!-- dinamico -->
		<tbody>
			<%
				@SuppressWarnings("unchecked")
				// a linha abaixo recebe a lista da camada controller armazenando no vetor
				ArrayList<JavaBeans> lista = (ArrayList<JavaBeans>) request.getAttribute("contatos");
				for (int i=0; i<lista.size();i++){
			%>
			<tr>
				<td><%=lista.get(i).getIdcon()%></td>
				<td><%=lista.get(i).getNome()%></td>
				<td><%=lista.get(i).getFone()%></td>
				<td><%=lista.get(i).getEmail()%></td>
				<!-- na linha abaixo o ?(parametro) envia um conteudo ao servlet  -->
				<td><a href="select?idcon=<%=lista.get(i).getIdcon()%>" class="Botao1">Editar</a><a href="javascript:confirmar(<%=lista.get(i).getIdcon()%>)" class="Botao2">Excluir</a></td>	
							
			</tr>							
			<%
				}
			%>			
		</tbody>
	</table>
	<script src="scripts/valida.js"></script>
</body>
</html>