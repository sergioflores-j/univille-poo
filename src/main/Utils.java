package main;

import javax.swing.JOptionPane;

public class Utils {
	public static int getMenuOpcoes(String texto, int opcoes[]) {
		int opcao = 0;
		int retorno = -1;
		boolean temOpcao = false;

		do {
			// Solicita uma opção do menu
			opcao = Integer.parseInt(JOptionPane.showInputDialog(texto));
			
			for (int op : opcoes) {
				if (op == opcao)
					temOpcao = true;
			}

			if (temOpcao) {
				retorno = opcao;
			} else {
				// nenhuma das opções contempla a informada
				JOptionPane.showMessageDialog(null, "Opção inválida.");
			}
		} while (retorno == -1);
		
		return retorno;
	}
	
	public static String getListaAlunos(String alunos[]) {
		String message = "";
		
		// Varre a lista de alunos para montar a listagem ao usuário
		for (int i = 0; i < alunos.length; i++) {
			String nome = alunos[i];
			
			// verifica se a posição já está cadastrada
			if (nome != null) {
				// caso seja o primeiro item, cria o cabeçalho da listagem
				if (message == "") {
					message = "--- Lista de alunos --- \nMatricula - Nome\n";
				}
				
				// concatena o aluno e sua matricula (index no array)
				message += (i + 1) + " - " + nome + "\n";
			}
		}
		
		// caso a mensagem não tenha sido alterada, nenhum aluno está cadastrado.
		if (message == "") {
			message = "Nenhum aluno cadastrado.";
		}
		
		return message;
	}
	
	public static int solicitaAula(String aulas[][]) { 
		// Solicita o numero da aula
		int aula = Integer.parseInt(JOptionPane.showInputDialog("Informe o número da aula (Possíveis: 1 - " + aulas.length + "):"));
		// Reatribui o valor de aula para utilizar o index do array
		aula = aula - 1;
		
		// verifica se a aula está cadastrada
		if (aula > aulas.length - 1 || aula < 0) {
			JOptionPane.showMessageDialog(null, "Aula inexistente.");
			aula = -1;
		}
		return aula;
	}
	public static int getNumFaltasAluno(String aulas[][], String alunos[], int aluno) {
		int numFaltas = 0;
		// varre as aulas e "seleciona" o aluno informado para verificar suas faltas
		for (int i = 0; i < aulas.length; i++) {
			String falta = aulas[i][aluno];
			
			// se não estiver vazio é porque o aluno faltou
			if (falta != null) numFaltas++;
		}
		
		return numFaltas;
	}
	
	public static int getPresencaAluno(String aulas[][], String alunos[], int aluno) {
		// busca o numero de faltas do aluno
		int numFaltas = getNumFaltasAluno(aulas, alunos, aluno);
		
		// Calcula a % de presença nas aulas do aluno. Exemplo:
		// x = ((30 - 3) * 100) / 30
		// x = 90 (%) 
		int pctFaltas = Math.round(((aulas.length - numFaltas) * 100) / aulas.length);
		
		return pctFaltas;
	}
	
	public static int getFaltasPossiveis(String aulas[][], String alunos[], int aluno) {
		// busca o numero de faltas do aluno
		int numFaltas = getNumFaltasAluno(aulas, alunos, aluno);
		// pega a quantidade de aulas para atingir 75% (minimo) e arredonda para baixo
		int numFaltasPossiveis = (int) Math.floor(aulas.length - (aulas.length * 0.75));
		
		// Verifica se a pessoa já faltou a quantidade possível de aulas 
		if (numFaltas <= numFaltasPossiveis) {
			 numFaltasPossiveis -= numFaltas;
		} else {
			// caso já tenha faltado o maximo, retorna a 0 nas possíveis
			numFaltasPossiveis = 0;
		}
		return numFaltasPossiveis;
	}
}
