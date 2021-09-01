/**
 *Validacao de campos obrigatorios
 *@author Jerry Ferreira
 */

function validar() {
	// passo 2 do slide 21
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
		// a linha abaixo envia os dados do formulario para a camada controller 
		document.forms['frmContato'].submit() //(passo 3 do slide 21)
	}
}