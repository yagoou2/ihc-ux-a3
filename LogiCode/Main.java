import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================
 *  LogiCode - MVP Gamificado de Logica de Programacao
 *  Faculdade UNA de Contagem | IHC e UX - 2026
 *  Prof. Daniel Henrique Matos de Paiva
 *  Equipe: Carlos A., Joao Pedro, Joao Victor,
 *          Jonathan, Lucas E., Yago S.
 * ============================================================
 *
 * ARQUITETURA DO PROJETO:
 *   - Uma unica classe Main com metodos estaticos.
 *   - Sem OOP complexa: apenas metodos, arrays, loops e if/else.
 *   - Progresso guardado em variaveis estaticas (pronto para BD).
 *   - Modulos organizados em metodos separados para clareza.
 *
 * FASES IMPLEMENTADAS:
 *   Fase 1 - Condicionais (5 desafios: if, else, ==, !=, &&)
 *   Fase 2 - Loops      (5 desafios: for, while, break, continue, do-while)
 *   Fase 3 - Dashboard  (relatorio de desempenho do aluno)
 *   Fase 4 - Conquistas + Perfil
 * ============================================================
 */
public class Main {

    // =========================================================
    // CONSTANTES DE COR (ANSI Escape Codes)
    // Funcionam na maioria dos terminais modernos.
    // =========================================================
    static final String RESET  = "\u001B[0m";
    static final String VERDE  = "\u001B[32m";
    static final String VERM   = "\u001B[31m";
    static final String AMAR   = "\u001B[33m";
    static final String CYAN   = "\u001B[36m";
    static final String AZUL   = "\u001B[34m";
    static final String NEGR   = "\u001B[1m";   // Negrito
    static final String ROXO   = "\u001B[35m";

    // =========================================================
    // ESTADO GLOBAL DO JOGADOR
    // Essas variaveis guardam o progresso enquanto o programa
    // esta rodando. Em versoes futuras, serao salvas no SQLite.
    // =========================================================
    static int    xpAtual      = 0;        // Pontos de experiencia acumulados
    static int    streakAtual  = 0;        // Dias consecutivos de estudo (simulado)
    static int    acertosFase1 = 0;        // Quantos desafios corretos na Fase 1
    static int    acertosFase2 = 0;        // Quantos desafios corretos na Fase 2
    static int    tentativasFase1 = 0;     // Total de tentativas na Fase 1
    static int    tentativasFase2 = 0;     // Total de tentativas na Fase 2
    static boolean fase1Completa = false;  // Flag: o aluno ja terminou a Fase 1?
    static boolean fase2Completa = false;  // Flag: o aluno ja terminou a Fase 2?

    // Lista de conquistas desbloqueadas pelo aluno
    static List<String> conquistas = new ArrayList<>();

    // Scanner compartilhado para leitura de input do teclado
    static Scanner scanner = new Scanner(System.in);

    // =========================================================
    // METODO PRINCIPAL - Ponto de entrada do programa
    // =========================================================
    public static void main(String[] args) {

        // Adiciona conquista inicial automaticamente
        conquistas.add("Primeira Conexao");
        streakAtual = 1; // Simula que o aluno esta no 1o dia de estudo

        // Exibe a tela de boas-vindas
        exibirTelaApresentacao();

        boolean rodando = true;

        // Loop principal do jogo: continua ate o aluno sair
        while (rodando) {
            exibirMenuPrincipal();

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o '\n' que sobra apos nextInt()

                switch (opcao) {
                    case 1:
                        iniciarFase1_Condicionais();
                        break;
                    case 2:
                        iniciarFase2_Loops();
                        break;
                    case 3:
                        exibirDashboard();
                        break;
                    case 4:
                        exibirPerfilEConquistas();
                        break;
                    case 5:
                        System.out.println("\n" + CYAN + "LogiCode: Porque o erro deve ser um degrau, nao uma barreira." + RESET);
                        System.out.println("Salvando progresso... Ate logo, programador! 🖥️");
                        rodando = false; // Encerra o loop principal
                        break;
                    default:
                        System.out.println(VERM + "Ops! Opcao invalida. Escolha entre 1 e 5." + RESET);
                }

            } catch (InputMismatchException e) {
                // Captura o erro se o aluno digitar letras no menu numerico
                System.out.println(VERM + "Entrada invalida! Digite apenas numeros para navegar." + RESET);
                scanner.nextLine(); // Limpa o buffer para evitar loop infinito
            }
        }

        scanner.close(); // Boa pratica: fechar o Scanner ao encerrar
    }

    // =========================================================
    // TELA DE APRESENTACAO
    // Exibida uma unica vez na inicializacao do programa.
    // =========================================================
    public static void exibirTelaApresentacao() {
        limparTela();
        System.out.println(CYAN + NEGR);
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║   >_ LogiCode  |  Gamificacao na Programacao    ║");
        System.out.println("  ║      Faculdade UNA de Contagem | IHC/UX 2026    ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.println("  ║  Prof. Daniel Henrique Matos de Paiva           ║");
        System.out.println("  ║  Equipe: Carlos A., Joao Pedro, Joao Victor,    ║");
        System.out.println("  ║          Jonathan, Lucas E., Yago S.            ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.println("  \"O erro e um degrau, nao uma barreira.\"");
        System.out.println();
        System.out.print("  Pressione [ENTER] para iniciar sua jornada...");
        scanner.nextLine();
    }

    // =========================================================
    // MENU PRINCIPAL
    // Exibido apos cada acao para o aluno escolher o proximo passo.
    // =========================================================
    public static void exibirMenuPrincipal() {
        limparTela();
        System.out.println(NEGR + CYAN);
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║        >_ LOGICODE  -  MENU         ║");
        System.out.printf ("  ║  XP: %-6d  |  Streak: %2dD         ║%n", xpAtual, streakAtual);
        System.out.println("  ╚══════════════════════════════════════╝" + RESET);
        System.out.println();

        // Indicador visual de progresso nas fases
        String s1 = fase1Completa ? VERDE + "[✓]" + RESET : AMAR + "[ ]" + RESET;
        String s2 = fase2Completa ? VERDE + "[✓]" + RESET : AMAR + "[ ]" + RESET;

        System.out.println("  " + s1 + " 1. Fase 1: Condicionais  (if / else)");
        System.out.println("  " + s2 + " 2. Fase 2: Loops         (for / while)");
        System.out.println("       3. Dashboard de Desempenho");
        System.out.println("       4. Perfil e Conquistas");
        System.out.println("       5. Sair");
        System.out.println();
        System.out.print("  > Escolha uma opcao: ");
    }

    // =========================================================
    // CABECALHO DOS DESAFIOS
    // Exibido antes de cada desafio para mostrar o status atual.
    // =========================================================
    public static void exibirCabecalho(String nomeModulo, int desafioAtual, int totalDesafios) {
        System.out.println();
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.printf ("  │  Modulo: %-20s XP: %5d  │%n", nomeModulo, xpAtual);
        System.out.printf ("  │  Streak: %2dD  |  Desafio %d de %d          │%n",
                            streakAtual, desafioAtual, totalDesafios);
        System.out.println("  └─────────────────────────────────────────┘");
    }

    // =========================================================
    // UTILITARIO: "Limpar" o terminal com linhas em branco
    // Simula um clear de tela sem depender de comandos do SO.
    // =========================================================
    public static void limparTela() {
        for (int i = 0; i < 3; i++) {
            System.out.println(); // Adiciona espacamento visual entre secoes
        }
    }

    // =========================================================
    // UTILITARIO: Aguarda o aluno pressionar ENTER para continuar
    // =========================================================
    public static void aguardarEnter() {
        System.out.print("\n  Pressione [ENTER] para continuar...");
        scanner.nextLine();
    }

    // =========================================================
    // UTILITARIO: Logica de verificacao de resposta
    // Recebe a resposta do aluno e a resposta correta,
    // exibe feedback colorido e retorna true/false.
    // =========================================================
    public static boolean verificarResposta(String digitado, String correto, String dica) {
        // Compara ignorando maiusculas/minusculas e espacos extras
        if (digitado.trim().equalsIgnoreCase(correto.trim())) {
            System.out.println("\n  " + VERDE + NEGR + "✔ CORRETO! +50 XP" + RESET);
            System.out.println("  " + AMAR + "Dica: " + dica + RESET);
            return true;
        } else {
            System.out.println("\n  " + VERM + "✘ Fluxo interrompido: '" + digitado + "' nao foi reconhecido." + RESET);
            System.out.println("  " + AMAR + "Lembre-se: o erro e um degrau. Verifique a sintaxe e tente novamente." + RESET);
            return false;
        }
    }

    // =========================================================
    // FASE 1 - MODULO CONDICIONAIS
    // 5 desafios sobre: if, else, ==, !=, &&
    // =========================================================
    public static void iniciarFase1_Condicionais() {
        limparTela();

        // -------------------------------------------------------
        // Estrutura dos desafios: array de arrays de Strings.
        // Cada desafio tem 4 campos:
        //   [0] = titulo do desafio
        //   [1] = enunciado/codigo mostrado ao aluno
        //   [2] = resposta correta (case-insensitive)
        //   [3] = dica pedagogica exibida apos acerto
        // -------------------------------------------------------
        String[][] desafios = {
            {
                "Desafio 1: A Porta do SE",
                "  O codigo abaixo esta incompleto.\n" +
                "  Queremos que ele execute algo SE x for maior que 10.\n\n" +
                "  [   ] (x > 10) {\n" +
                "      System.out.println(\"maior\");\n" +
                "  }\n\n" +
                "  > Qual palavra-chave preenche o [   ]?",
                "if",
                "'if' vem do ingles 'se'. E a porta de entrada para a execucao condicional em Java."
            },
            {
                "Desafio 2: O Plano B (SENAO)",
                "  E se a condicao for falsa? Precisamos de um Plano B!\n\n" +
                "  if (x > 10) {\n" +
                "      System.out.println(\"maior\");\n" +
                "  } [    ] {\n" +
                "      System.out.println(\"menor\");\n" +
                "  }\n\n" +
                "  > Qual palavra-chave preenche o [    ]?",
                "else",
                "'else' significa 'senao'. Ele executa quando o 'if' for falso. Sempre vem depois de um 'if'."
            },
            {
                "Desafio 3: Igualdade Estrita",
                "  Em Java, '=' atribui um valor. Para COMPARAR, usamos outro simbolo.\n\n" +
                "  if (idade [  ] 18) {\n" +
                "      System.out.println(\"Maior de idade!\");\n" +
                "  }\n\n" +
                "  > Qual operador verifica se 'idade' e EXATAMENTE igual a 18?",
                "==",
                "'==' compara dois valores. '=' apenas atribui. Esse e um dos erros mais comuns em Java!"
            },
            {
                "Desafio 4: A Negacao",
                "  O sistema so deve prosseguir se a senha for DIFERENTE de 0000.\n\n" +
                "  if (senha [  ] \"0000\") {\n" +
                "      System.out.println(\"Acesso permitido!\");\n" +
                "  }\n\n" +
                "  > Qual operador significa 'nao igual' / 'diferente de'?",
                "!=",
                "'!' em logica significa negacao. '!=' literalmente lemos como 'nao igual'. O contrario de '=='."
            },
            {
                "Desafio 5: E mais E (AND Logico)",
                "  Para passar de ano, o aluno precisa de nota > 6 E faltas < 10.\n" +
                "  As DUAS condicoes precisam ser verdadeiras ao mesmo tempo.\n\n" +
                "  if (nota > 6 [  ] faltas < 10) {\n" +
                "      System.out.println(\"Aprovado!\");\n" +
                "  }\n\n" +
                "  > Qual operador representa o 'E' logico?",
                "&&",
                "'&&' e o operador 'E' (AND). Ambas as condicoes precisam ser true. '||' seria o 'OU' (OR)."
            }
        };

        // Contadores locais para este modulo
        int acertos = 0;
        int tentativas = 0;

        // Loop por cada desafio do array
        for (int i = 0; i < desafios.length; i++) {
            boolean acertou = false;

            // Loop de tentativas: continua ate acertar
            while (!acertou) {
                limparTela();
                exibirCabecalho("Condicionais", i + 1, desafios.length);

                System.out.println();
                System.out.println("  " + NEGR + desafios[i][0] + RESET);
                System.out.println("  " + "─".repeat(45));
                System.out.println(desafios[i][1]);
                System.out.println();
                System.out.print("  > ");

                String respostaAluno = scanner.nextLine().trim();
                tentativas++;

                acertou = verificarResposta(respostaAluno, desafios[i][2], desafios[i][3]);

                if (acertou) {
                    xpAtual += 50;
                    acertos++;
                }

                aguardarEnter();
            }
        }

        // Atualiza estatisticas globais da fase 1
        acertosFase1    = acertos;
        tentativasFase1 = tentativas;
        fase1Completa   = true;

        // Tela de conclusao da fase
        limparTela();
        System.out.println("  " + VERDE + NEGR);
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║  🎉 FASE 1 CONCLUIDA! Parabens!     ║");
        System.out.println("  ║                                      ║");
        System.out.printf ("  ║  Acertos:    %2d de %d desafios         ║%n", acertos, desafios.length);
        System.out.printf ("  ║  XP Total:   %-6d                  ║%n", xpAtual);
        System.out.println("  ╚══════════════════════════════════════╝" + RESET);

        // Desbloqueia conquista se ainda nao tem
        desbloquearConquista("Mestre do IF/ELSE");

        aguardarEnter();
    }

    // =========================================================
    // FASE 2 - MODULO LOOPS
    // 5 desafios sobre: for, while, do-while, break, continue
    // Pre-requisito sugerido: ter feito a Fase 1.
    // =========================================================
    public static void iniciarFase2_Loops() {
        limparTela();

        // Aviso amigavel se o aluno ainda nao fez a Fase 1
        if (!fase1Completa) {
            System.out.println("  " + AMAR + "╔══════════════════════════════════════════════╗");
            System.out.println("  ║  ⚠ Sugestao: Conclua a Fase 1 antes!       ║");
            System.out.println("  ║  Os loops usam condicionais que voce        ║");
            System.out.println("  ║  aprendeu la. Mas pode continuar aqui!      ║");
            System.out.println("  ╚══════════════════════════════════════════════╝" + RESET);
            System.out.print("\n  Deseja continuar assim mesmo? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (!resp.equals("s")) return; // Volta ao menu se o aluno escolher "n"
        }

        // -------------------------------------------------------
        // Array de desafios da Fase 2 - Loops
        // Mesma estrutura de 4 campos da Fase 1
        // -------------------------------------------------------
        String[][] desafios = {
            {
                "Desafio 1: O Loop Contavel (FOR)",
                "  Queremos exibir os numeros de 0 a 4 no console.\n" +
                "  O loop abaixo esta incompleto:\n\n" +
                "  [  ] (int i = 0; i < 5; i++) {\n" +
                "      System.out.println(i);\n" +
                "  }\n\n" +
                "  > Qual palavra-chave inicia este tipo de loop?",
                "for",
                "'for' e usado quando sabemos o numero de repeticoes. Estrutura: for(inicio; condicao; passo)."
            },
            {
                "Desafio 2: O Loop Condicional (WHILE)",
                "  O loop deve rodar enquanto a senha for errada.\n" +
                "  Nao sabemos quantas tentativas o usuario vai precisar.\n\n" +
                "  [     ] (senhaErrada) {\n" +
                "      pedirSenha();\n" +
                "  }\n\n" +
                "  > Qual palavra-chave preenche o [     ]?",
                "while",
                "'while' repete enquanto a condicao for verdadeira. Ideal quando nao sabemos o numero de repeticoes."
            },
            {
                "Desafio 3: Parar no Meio (BREAK)",
                "  O codigo percorre uma lista procurando o numero 7.\n" +
                "  Quando achar, deve PARAR o loop imediatamente.\n\n" +
                "  for (int i = 0; i < numeros.length; i++) {\n" +
                "      if (numeros[i] == 7) {\n" +
                "          [     ]; // Para o loop aqui!\n" +
                "      }\n" +
                "  }\n\n" +
                "  > Qual comando interrompe o loop imediatamente?",
                "break",
                "'break' e como um botao de parada de emergencia. Sai do loop na hora, sem terminar as iteracoes."
            },
            {
                "Desafio 4: Pular a Vez (CONTINUE)",
                "  Queremos imprimir apenas os numeros PARES de 0 a 9.\n" +
                "  Quando o numero for impar, devemos PULAR aquela repetição.\n\n" +
                "  for (int i = 0; i < 10; i++) {\n" +
                "      if (i % 2 != 0) {\n" +
                "          [        ]; // Pula os impares\n" +
                "      }\n" +
                "      System.out.println(i);\n" +
                "  }\n\n" +
                "  > Qual comando pula para a proxima repetição do loop?",
                "continue",
                "'continue' ignora o restante do codigo naquela repetição e vai direto para a proxima. Diferente de 'break' que encerra tudo."
            },
            {
                "Desafio 5: Executar Pelo Menos Uma Vez (DO-WHILE)",
                "  Queremos pedir a senha ao usuario PELO MENOS UMA VEZ,\n" +
                "  e so repetir SE ela estiver errada.\n\n" +
                "  [  ] {\n" +
                "      senha = pedirSenha();\n" +
                "  } while (senha != correta);\n\n" +
                "  > Qual palavra-chave inicia este tipo especial de loop?",
                "do",
                "'do-while' garante que o bloco execute ao menos uma vez antes de checar a condicao. Perfeito para menus e validacoes!"
            }
        };

        int acertos   = 0;
        int tentativas = 0;

        // Loop pelos 5 desafios da Fase 2
        for (int i = 0; i < desafios.length; i++) {
            boolean acertou = false;

            while (!acertou) {
                limparTela();
                exibirCabecalho("Loops", i + 1, desafios.length);

                System.out.println();
                System.out.println("  " + NEGR + desafios[i][0] + RESET);
                System.out.println("  " + "─".repeat(45));
                System.out.println(desafios[i][1]);
                System.out.println();
                System.out.print("  > ");

                String respostaAluno = scanner.nextLine().trim();
                tentativas++;

                acertou = verificarResposta(respostaAluno, desafios[i][2], desafios[i][3]);

                if (acertou) {
                    xpAtual += 50;
                    acertos++;
                }

                aguardarEnter();
            }
        }

        // Atualiza estatisticas globais da fase 2
        acertosFase2    = acertos;
        tentativasFase2 = tentativas;
        fase2Completa   = true;

        // Bonus de XP por completar as duas fases
        if (fase1Completa && fase2Completa) {
            xpAtual += 100;
            System.out.println("\n  " + ROXO + NEGR + "🌟 BONUS! +100 XP por completar ambas as fases!" + RESET);
        }

        // Tela de conclusao da Fase 2
        limparTela();
        System.out.println("  " + VERDE + NEGR);
        System.out.println("  ╔══════════════════════════════════════╗");
        System.out.println("  ║  🎉 FASE 2 CONCLUIDA! Incrivel!     ║");
        System.out.println("  ║                                      ║");
        System.out.printf ("  ║  Acertos:    %2d de %d desafios         ║%n", acertos, desafios.length);
        System.out.printf ("  ║  XP Total:   %-6d                  ║%n", xpAtual);
        System.out.println("  ╚══════════════════════════════════════╝" + RESET);

        desbloquearConquista("Mestre dos Loops");

        aguardarEnter();
    }

    // =========================================================
    // FASE 3 - DASHBOARD DE DESEMPENHO
    // Exibe um relatorio completo do progresso do aluno.
    // =========================================================
    public static void exibirDashboard() {
        limparTela();

        System.out.println("  " + AZUL + NEGR);
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║           📊 DASHBOARD DE DESEMPENHO        ║");
        System.out.println("  ╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();

        // -------------------------------------------------------
        // Status Geral
        // -------------------------------------------------------
        System.out.println("  " + NEGR + "[ STATUS GERAL ]" + RESET);
        System.out.printf ("  %-20s %s%d%s%n", "XP Total:", AMAR, xpAtual, RESET);
        System.out.printf ("  %-20s %s%dD%s%n", "Streak Atual:", CYAN, streakAtual, RESET);
        System.out.printf ("  %-20s %s%d%s%n", "Conquistas:", VERDE, conquistas.size(), RESET);
        System.out.println();

        // -------------------------------------------------------
        // Status por Fase - mostra uma barra de progresso ASCII
        // -------------------------------------------------------
        System.out.println("  " + NEGR + "[ PROGRESSO POR FASE ]" + RESET);

        // Fase 1
        String statusF1 = fase1Completa ? VERDE + "CONCLUIDA" + RESET : AMAR + "PENDENTE" + RESET;
        System.out.printf("  Fase 1 - Condicionais: %s%n", statusF1);
        if (fase1Completa) {
            System.out.printf("    Acertos: %d/5  |  Tentativas: %d  |  Precisao: %.0f%%%n",
                acertosFase1, tentativasFase1,
                tentativasFase1 > 0 ? (acertosFase1 * 100.0 / tentativasFase1) : 0);
            exibirBarraProgresso(acertosFase1, 5);
        }
        System.out.println();

        // Fase 2
        String statusF2 = fase2Completa ? VERDE + "CONCLUIDA" + RESET : AMAR + "PENDENTE" + RESET;
        System.out.printf("  Fase 2 - Loops:        %s%n", statusF2);
        if (fase2Completa) {
            System.out.printf("    Acertos: %d/5  |  Tentativas: %d  |  Precisao: %.0f%%%n",
                acertosFase2, tentativasFase2,
                tentativasFase2 > 0 ? (acertosFase2 * 100.0 / tentativasFase2) : 0);
            exibirBarraProgresso(acertosFase2, 5);
        }
        System.out.println();

        // -------------------------------------------------------
        // Nivel do aluno calculado a partir do XP
        // -------------------------------------------------------
        System.out.println("  " + NEGR + "[ NIVEL ]" + RESET);
        System.out.println("  " + calcularNivel(xpAtual));
        System.out.println();

        aguardarEnter();
    }

    // =========================================================
    // UTILITARIO: Barra de progresso visual em ASCII
    // Ex: [████████░░] 4/5
    // =========================================================
    public static void exibirBarraProgresso(int atual, int total) {
        int largura = 20; // Tamanho total da barra em caracteres
        int preenchido = (total > 0) ? (atual * largura / total) : 0;

        System.out.print("    [" + VERDE);
        for (int i = 0; i < preenchido; i++) System.out.print("█");
        System.out.print(RESET);
        for (int i = preenchido; i < largura; i++) System.out.print("░");
        System.out.printf("] %d/%d%n", atual, total);
    }

    // =========================================================
    // UTILITARIO: Calcula o nivel do aluno baseado no XP
    // =========================================================
    public static String calcularNivel(int xp) {
        if (xp >= 500) return ROXO + "Nivel 5 - GRANDMASTER PROGRAMMER 🏆" + RESET;
        if (xp >= 350) return AMAR  + "Nivel 4 - Expert Developer ⭐" + RESET;
        if (xp >= 200) return CYAN  + "Nivel 3 - Desenvolvedor Junior 💻" + RESET;
        if (xp >= 100) return VERDE + "Nivel 2 - Estudante Dedicado 📚" + RESET;
        return                 VERM + "Nivel 1 - Iniciante  🌱" + RESET;
    }

    // =========================================================
    // FASE 4 - PERFIL E CONQUISTAS
    // Exibe as conquistas desbloqueadas e o resumo do perfil.
    // =========================================================
    public static void exibirPerfilEConquistas() {
        limparTela();

        System.out.println("  " + ROXO + NEGR);
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║              🏅 PERFIL DO JOGADOR           ║");
        System.out.println("  ╚══════════════════════════════════════════════╝" + RESET);
        System.out.println();
        System.out.printf ("  XP Total: " + AMAR + "%d" + RESET + " | Streak: " + CYAN + "%dD" + RESET + "%n", xpAtual, streakAtual);
        System.out.println("  Nivel: " + calcularNivel(xpAtual));
        System.out.println();

        System.out.println("  " + NEGR + "[ CONQUISTAS DESBLOQUEADAS ]" + RESET);
        System.out.println("  " + "─".repeat(40));

        // Itera e exibe cada conquista na lista
        if (conquistas.isEmpty()) {
            System.out.println("  Nenhuma conquista ainda. Complete os desafios!");
        } else {
            for (String c : conquistas) {
                System.out.println("  " + VERDE + "[✓] " + RESET + c);
            }
        }

        System.out.println();
        System.out.println("  " + NEGR + "[ CONQUISTAS BLOQUEADAS ]" + RESET);
        System.out.println("  " + "─".repeat(40));

        // Mostra conquistas que ainda nao foram desbloqueadas
        exibirConquistaBloqueadaSeNecessario("Mestre do IF/ELSE",  "Complete a Fase 1");
        exibirConquistaBloqueadaSeNecessario("Mestre dos Loops",   "Complete a Fase 2");
        exibirConquistaBloqueadaSeNecessario("Dual Master",        "Complete as Fases 1 e 2");

        aguardarEnter();
    }

    // =========================================================
    // UTILITARIO: Exibe uma conquista como bloqueada (se o
    // aluno ainda nao a desbloqueou)
    // =========================================================
    public static void exibirConquistaBloqueadaSeNecessario(String nome, String como) {
        if (!conquistas.contains(nome)) {
            System.out.println("  " + VERM + "[🔒] " + RESET + nome + " — " + AMAR + como + RESET);
        }
    }

    // =========================================================
    // UTILITARIO: Desbloqueia uma conquista se ainda nao existe
    // e exibe uma mensagem de notificacao para o aluno.
    // =========================================================
    public static void desbloquearConquista(String nome) {
        if (!conquistas.contains(nome)) {
            conquistas.add(nome);
            System.out.println("\n  " + CYAN + NEGR + "🏅 Nova Conquista: " + nome + "!" + RESET);

            // Verifica conquista especial de completar as duas fases
            if (fase1Completa && fase2Completa && !conquistas.contains("Dual Master")) {
                conquistas.add("Dual Master");
                System.out.println("  " + ROXO + NEGR + "🌟 Conquista Especial: Dual Master desbloqueada!" + RESET);
            }
        }
    }
}
