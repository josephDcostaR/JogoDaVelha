import java.util.Scanner;
import java.util.Random;

public class App {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // Inicializar o tabuleiro
        char[][] tabuleiro = inicializarTabuleiro();
        char caractereJogador = obterCaractereUsuario(teclado);
        char caractereComputador = obterCaractereComputador(caractereJogador);
        boolean vezDoJogador = sortearValorBooleano();

        exibirMensagemBoasVindas(vezDoJogador);

        while (true) {
            limparTela();
            exibirTabuleiro(tabuleiro);

            if (teveGanhador(tabuleiro, caractereJogador)) {
                System.out.println("Você venceu! Parabéns!");
                break;
            } else if (teveGanhador(tabuleiro, caractereComputador)) {
                System.out.println("O computador venceu. Tente novamente!");
                break;
            } else if (teveEmpate(tabuleiro)) {
                System.out.println("Empate! Jogo terminado.");
                break;
            }

            if (vezDoJogador) {
                processarVezUsuario(tabuleiro, caractereJogador, teclado);
            } else {
                processarVezComputador(tabuleiro, caractereComputador, caractereJogador);
            }
            vezDoJogador = !vezDoJogador;
        }

        teclado.close();
    }

    // Inicializar tabuleiro vazio
    static char[][] inicializarTabuleiro() {
        return new char[][] {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
        };
    }

    // Obter caractere do usuário (X ou O)
    static char obterCaractereUsuario(Scanner teclado) {
        System.out.println("Escolha seu símbolo (X ou O): ");
        char simbolo = teclado.next().toUpperCase().charAt(0);
        while (simbolo != 'X' && simbolo != 'O') {
            System.out.println("Entrada inválida. Escolha X ou O: ");
            simbolo = teclado.next().toUpperCase().charAt(0);
        }
        return simbolo;
    }

    // Definir símbolo do computador
    static char obterCaractereComputador(char caractereJogador) {
        return (caractereJogador == 'X') ? 'O' : 'X';
    }

    // Sortear quem começa jogando
    static boolean sortearValorBooleano() {
        return new Random().nextBoolean();
    }

    // Exibir mensagem inicial
    static void exibirMensagemBoasVindas(boolean vezDoJogador) {
        if (vezDoJogador) {
            System.out.println("Você começa!");
        } else {
            System.out.println("O computador começa!");
        }
    }

    // Limpar tela (simulado)
    static void limparTela() {
        System.out.println("\n\n\n\n\n");
    }

    // Exibir o tabuleiro
    static void exibirTabuleiro(char[][] tabuleiro) {
        System.out.println("  1   2   3");
        for (int i = 0; i < tabuleiro.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < tabuleiro[i].length; j++) {
                System.out.print(tabuleiro[i][j]);
                if (j < tabuleiro[i].length - 1) System.out.print(" | ");
            }
            System.out.println();
            if (i < tabuleiro.length - 1) System.out.println("  ---------");
        }
    }

    // Processar vez do jogador
    static void processarVezUsuario(char[][] tabuleiro, char caractereJogador, Scanner teclado) {
        int linha, coluna;
        while (true) {
            System.out.println("Digite sua jogada (linha e coluna, ex: 1 2): ");
            linha = teclado.nextInt() - 1;
            coluna = teclado.nextInt() - 1;
            teclado.nextLine();

            if (linha >= 0 && linha < 3 && coluna >= 0 && coluna < 3 && tabuleiro[linha][coluna] == ' ') {
                tabuleiro[linha][coluna] = caractereJogador;
                break;
            } else {
                System.out.println("Jogada inválida. Tente novamente.");
            }
        }
    }

    // Processar vez do computador
    static void processarVezComputador(char[][] tabuleiro, char caractereComputador, char caractereJogador) {
        int[] jogada = obterMelhorJogada(tabuleiro, caractereComputador, caractereJogador);
        tabuleiro[jogada[0]][jogada[1]] = caractereComputador;
        System.out.println("O computador jogou na posição: " + (jogada[0] + 1) + ", " + (jogada[1] + 1));
    }

    // Obter a melhor jogada para o computador
    static int[] obterMelhorJogada(char[][] tabuleiro, char computador, char jogador) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    tabuleiro[i][j] = computador;
                    if (teveGanhador(tabuleiro, computador)) {
                        tabuleiro[i][j] = ' ';
                        return new int[] {i, j};
                    }
                    tabuleiro[i][j] = ' ';
                }
            }
        }

        // Caso não haja jogada vencedora, retorna a primeira posição disponível
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') {
                    return new int[] {i, j};
                }
            }
        }

        return new int[] {-1, -1}; // Nunca deve ocorrer
    }

    // Verificar se há vencedor
    static boolean teveGanhador(char[][] tabuleiro, char jogador) {
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == jogador && tabuleiro[i][1] == jogador && tabuleiro[i][2] == jogador) return true;
            if (tabuleiro[0][i] == jogador && tabuleiro[1][i] == jogador && tabuleiro[2][i] == jogador) return true;
        }
        if (tabuleiro[0][0] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][2] == jogador) return true;
        if (tabuleiro[0][2] == jogador && tabuleiro[1][1] == jogador && tabuleiro[2][0] == jogador) return true;
        return false;
    }

    // Verificar empate
    static boolean teveEmpate(char[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == ' ') return false;
            }
        }
        return true;
    }
}
