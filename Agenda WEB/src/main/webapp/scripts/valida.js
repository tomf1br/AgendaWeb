/**
 * Validacao de campos obrigatorios
 *	Confirmacao de exclusao
 * @author Jerry Ferreira
 */

function validar() {
	//Passo 2 do slide 21
	let nome = frmContato.nome.value
	let fone = frmContato.fone.value
	//alert(nome + ' ' + fone)
	if (nome === '') {
		alert('Preencha o nome do contato')
		frmContato.nome.focus()
		return false
	} else if (fone === '') {
		alert('Preencha o telefone do contato')
		frmContato.fone.focus()
		return false
	} else {
		//a linha abiaxo envia os dados do formulario para camada controller 
		document.forms['frmContato'].submit() //Passo 3 slide 21
	}
}

function confirmar(idcon) { //passo 1 do slide 24
	//confirm gera um alerta com uma caixa de opções
	let resposta = confirm('Confirma a exclusão deste contato?')
	if(resposta === true){
	//a linha abaixo envia ao controller a requisicao delete + idcon	
		window.location.href = 'delete?idcon=' + idcon //passo 2 do slide 24
	}
}