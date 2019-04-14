package main;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		/*
		  Será desenvolvido um sistema de gestão de frequência escolar de uma turma,
		  onde irá contemplar: 
		  - Cadastro de alunos, limite de 20 alunos; 
		  - Cadastro da quantidade de aulas, padrão 30 aulas (possibilidade de alterar o numero); 
		  - Cadastro de presença (controle de frequência) dos alunos nas aulas; 
		  - Listagem das presenças e faltas, cálculo da frequência (em %) e a apresentação de um cálculo de projeção de faltas possíveis (considerando o número de aulas cadastradas para saber quantas faltas são necessárias para atingir o limite minimo de 75% de presença)
		 */

		int opcao = 0;
		// inicializa o array de alunos com tamanho predefinido de 20
		String alunos[] = new String[20];
		// inicializa a matriz de aulas, em que a linha será a numeracao da aula, e a coluna as faltas por aluno
		String aulas[][] = new String[30][20];
		// inicializa a variavel auxiliar que identifica o ultimo cadastro de alunos
		int ultimoAlunoCadastrado = -1;
		// inicializa a variavel auxiliar que identifica o ultimo cadastro de aulas
		int ultimaAulaCadastrada = -1;

		do {
			opcao = Integer.parseInt(JOptionPane.showInputDialog("--- MENU GERAL --- \nDigite uma opção: \n1 - Alunos \n2 - Aulas \n0 - Sair"));

			if (opcao == 1) {
				int opcoes[] = { 1, 2, 0 };
				int opAluno = 0;

				do {
					// Utiliza o método que cria um menu e valida se o usuário digitou uma opção válida. 
					opAluno = Utils.getMenuOpcoes("--- MENU DE ALUNOS --- \nDigite uma opção: \n1 - Cadastrar aluno \n2 - Ver lista de alunos \n0 - Voltar", opcoes);

					// Cadastro de aluno
					if (opAluno == 1) {
						// Valida se o número de alunos cadastrados não será ultrapassado
						if (ultimoAlunoCadastrado == 19) {
							// Informa que o limite de 20 alunos foi atingido.
							JOptionPane.showMessageDialog(null, "Limite de 20 alunos excedido.");
						} else {
							// aumenta 1 da ultima posicao cadastrada
							ultimoAlunoCadastrado++;
							
							// Solicita o nome para o cadastro do aluno
							String nome = JOptionPane.showInputDialog("Digite o nome do aluno " + (ultimoAlunoCadastrado + 1) + ":");
							
							// cadastra o nome informado
							alunos[ultimoAlunoCadastrado] = nome;
						}
					// Listagem dos alunos
					} else if (opAluno == 2) {
						String message = Utils.getListaAlunos(alunos);
						JOptionPane.showMessageDialog(null, message);
					}
				} while (opAluno != 0);
			} else if (opcao == 2) {
				// Solicita uma opção do menu de aulas
				int opcoes[] = { 1, 2, 3, 4, 0 };
				int opAula = 0;
				
				do {
					opAula = Utils.getMenuOpcoes("--- MENU DE AULAS --- \nDigite uma opção: \n1 - Alterar quantidade de aulas \n2 - Atribuir falta \n3 - Listagem de faltas \n4 - Verificar quantidade de faltas possiveis para o aluno \n0 - Voltar", opcoes);
					
					// Cadastro de aulas
					if (opAula == 1) {
						// Solicita a quantidade de aulas
						int numAulas = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de aulas existentes:"));
						
						// Cria uma nova lista com o novo tamanho informado
						String novaListaAulas[][] = new String[numAulas][20];
						
						// reatribui os valores já existentes no array anterior a nova lista.
						for (int i = 0; i < novaListaAulas.length; i++) {
							for (int j = 0; j < novaListaAulas[i].length; j++) {
								novaListaAulas[i][j] = aulas[i][j];
							}
						}
						
						// atribui a nova lista de aulas para a variavel global
						aulas = novaListaAulas;
					} else if (opAula == 2) {
						// Solicita o numero da aula
						int aula = Utils.solicitaAula(aulas);
						
						// verifica se a aula está cadastrada
						if (aula != -1) {
							int aluno = Integer.parseInt(JOptionPane.showInputDialog(Utils.getListaAlunos(alunos) + "\nInforme a matricula do aluno para atribuir a falta:"));
							// Reatribui o valor de aluno para utilizar o index do array
							aluno = aluno - 1;
							if (alunos[aluno] == null) {
								JOptionPane.showMessageDialog(null, "Aluno não cadastrado.");
							} else {
								// se o valor cadastrado já é falta, atribui presença (realizando um "Toggle")
								if (aulas[aula][aluno] == "F") 
									aulas[aula][aluno] = null;
								else 
									aulas[aula][aluno] = "F";
							}
						}
					} else if (opAula == 3) {
						// Solicita o numero da aula
						int aula = Utils.solicitaAula(aulas);
						
						// verifica se a aula está cadastrada
						if (aula != -1) {
							String message = "";
							
							// varre a lista de alunos para a aula
							for (int i = 0; i < aulas[aula].length; i++) {
								String falta = aulas[aula][i];
								String aluno = alunos[i];
								
								// verifica se o aluno da posiçao atual está cadastrado para montar as suas informações
								if (aluno != null) {
									if (message == "") 
										message = "--- Lista de presença da aula " + (aula + 1) + " --- \nMatricula - Nome - Falta - Presença (%) \n";
									
									message += (i + 1) 
											+ " - " 
											+ aluno 
											+ " - "
											+ ((falta != null) ? falta : 'C')
											+ " - " 
											+ Utils.getPresencaAluno(aulas, alunos, i) + "\n";
								}
							}
							
							// caso a mensagem não tenha sido alterada, nenhum aluno está cadastrado.
							if (message == "") {
								message = "Nenhum aluno cadastrado.";
							}
							
							JOptionPane.showMessageDialog(null, message);
						}
					} else if (opAula == 4) {
						int aluno = Integer.parseInt(JOptionPane.showInputDialog(Utils.getListaAlunos(alunos) + "\nInforme a matricula do aluno para verificar a quantidade de faltas possíveis, para manter acima ou igual a 75% de presença:"));
						// Reatribui o valor de aluno para utilizar o index do array
						aluno = aluno - 1;
						if (alunos[aluno] == null) {
							JOptionPane.showMessageDialog(null, "Aluno não cadastrado.");
						} else {
							int possiveis = Utils.getFaltasPossiveis(aulas, alunos, aluno);
							
							JOptionPane.showMessageDialog(null, "Faltas possíveis: " + possiveis);
						}
					}
				} while(opAula != 0);
			} else if (opcao != 0) {
				// nenhuma das opções contempla a informada
				JOptionPane.showMessageDialog(null, "Opção inválida.");
			}
		} while (opcao != 0);
	}
}
