import java.util.*;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Jogador j1 = new Jogador("Romario", "123");
        Jogador j2 = new Jogador("Roberto", "456");

        Tabuleiro tabuleiro = new Tabuleiro();

        j1.setCor("Branco", tabuleiro);
        j2.setCor("Preto",tabuleiro);
        Jogador jogadorAtual = j1;

        do {
            System.out.println("\n\t\t\t" + jogadorAtual.getNome() + " é a sua vez!\n");

            Peca pecaSelecionada = null;

            ArrayList<Posicao> posicoes;

            int indicePosicao;
            Posicao posicaoEscolhida;
            do {

                imprimirTabuleiro(tabuleiro, jogadorAtual);

                //Seleção da peçaa movimentar

                boolean podeSelecionar;
                do {
                    podeSelecionar = true;

                    System.out.println("\nSelecione o indice da peca que quer movimentar: ");
                    int indiceEscolhaPeca = sc.nextInt() - 1;
                    if (indiceEscolhaPeca >= 0 && indiceEscolhaPeca < 64) {

                        pecaSelecionada = tabuleiro.getPosicoes().get(indiceEscolhaPeca).getPeca();
                        if (!jogadorAtual.getPecas().contains(pecaSelecionada)) {
                            podeSelecionar = false;
                            System.out.println("\nVocê só pode selecionar suas peças");
                        }
                        System.out.println(pecaSelecionada); // da p tirar isso
                    } else {
                        System.out.println("\nInsira um indice de 1 à 64");
                        podeSelecionar = false;
                    }
                } while (!podeSelecionar);

                //escolha da posicao p movimentar

                posicoes = pecaSelecionada.possiveisMovimentos(tabuleiro);
                String posicoesValidas = "\n";
                int index = 1;

                for (Posicao posicao : posicoes) {
                    posicoesValidas += index + " - " + posicao + " " + (tabuleiro.getPosicoes().indexOf(posicao) + 1) + "\n";//talvez+1
                    index++;
                }
                posicoesValidas += "0 - voltar";

                do {
                    System.out.println("\nPossiveis Movimentos: ");
                    System.out.println(posicoesValidas);
                    indicePosicao = sc.nextInt();

                    if (indicePosicao < 0 || indicePosicao > posicoes.size()) {
                        System.out.println("\nEscolha uma posição válida!");
                    } else if (indicePosicao == 0){
                        pecaSelecionada = null;
                    }
                } while (indicePosicao < 0 || indicePosicao > posicoes.size());

            }while(pecaSelecionada==null);

            posicaoEscolhida = posicoes.get(indicePosicao-1);

            //movimentacao da peca escolhida p posicao desejada
            if(jogadorAtual == j1){
                jogadorAtual.moverPeca(pecaSelecionada, posicaoEscolhida, tabuleiro, j2);
            } else {
                jogadorAtual.moverPeca(pecaSelecionada, posicaoEscolhida, tabuleiro, j1);
            }

            System.out.println(validarVitoria(j2));

            if(jogadorAtual == j1){
                jogadorAtual = j2;
            } else {
                jogadorAtual = j1;
            }

        }while(!validarVitoria(j1) && !validarVitoria(j2));

        if(validarVitoria(j2)){
            System.out.println("Parabéns " + j1.getNome());
        } else {
            System.out.println("Parabéns " + j2.getNome());
        }
    }

    private static boolean validarVitoria(Jogador adversario){
        for(Peca peca : adversario.getPecas()){
            if(peca instanceof Rei){
                return false;
            }
        }
        return true;
    }

    private static void imprimirTabuleiro(Tabuleiro tabuleiro, Jogador jogadorAtual){

        int index = 1;

        if(jogadorAtual.getCor().equals("Branco")) {
            for(Posicao posicao: tabuleiro.getPosicoes()){
                if(posicao.getPeca() != null) {
                    System.out.print(" | " + posicao.getPeca());
                } else {
                    System.out.print(" | " + "  ");
                }
                if(index %8==0) {
                    System.out.print(" | \n");
                }
                index++;
            }
        } else {
            ArrayList<Posicao> tabuleiroInvertido = tabuleiro.getPosicoes();
            Collections.reverse(tabuleiroInvertido);

            for(Posicao posicao: tabuleiroInvertido){
                if(posicao.getPeca() != null) {
                    System.out.print(" | " + posicao.getPeca());
                } else {
                    System.out.print(" | " + "  ");
                }
                if(index %8==0) {
                    System.out.print(" | \n");
                }
                index++;
            }
            Collections.reverse(tabuleiroInvertido);
        }

    }

}
