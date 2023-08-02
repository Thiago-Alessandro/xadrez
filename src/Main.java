import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Jogador j1 = new Jogador("Romario", "123");
        Jogador j2 = new Jogador("Roberto", "456");

        Tabuleiro tabuleiro = new Tabuleiro();

        //System.out.println(tabuleiro);

        j1.setCor("Branco", tabuleiro);
        j2.setCor("Preto",tabuleiro);
        Jogador jogadorAtual = j1;
        do {
            imprimirTabuleiro(tabuleiro);

            //escolha da peca
            // -- System.out.println(j1.getPecas());

            Peca pecaSelecionada = null;
            boolean podeSeleconar;
            do {
                podeSeleconar = true;

                System.out.println("\nSelecione o indice da peca que quer movimentar: ");
                int indiceEscolhaPeca = sc.nextInt() - 1;
                if(indiceEscolhaPeca > 0 && indiceEscolhaPeca < 65) {
                    pecaSelecionada = tabuleiro.getPosicoes().get(indiceEscolhaPeca).getPeca();
                    if (!jogadorAtual.getPecas().contains(pecaSelecionada)) {
                        podeSeleconar = false;
                        System.out.println("\nVocê só pode selecionar suas peças");
                    }
                    System.out.println(pecaSelecionada); // da p tirar isso
                } else {
                    System.out.println("\nInsira um indice de 1 à 64");
                    podeSeleconar = false;
                }
            }while(!podeSeleconar);
            // -- Peca peca = j1.getPecas().get(escolhaPeca);
            // -- System.out.println(peca);

            //escolha da posicao p movimentar

                ArrayList<Posicao> posicoes = pecaSelecionada.possiveisMovimentos(tabuleiro);
                String posicoesValidas = "\n";
                int index = 1;
                for(Posicao posicao : posicoes){
                    posicoesValidas += index + " - " + posicao + " " +  tabuleiro.getPosicoes().indexOf(posicao)+1 + "\n";//talvez+1
                    index++;
                }
                posicoesValidas += "0 - voltar"; //tem que fazer validar e voltar de fato

            int escolhaPosicao;
            Posicao posicao;
            do{
                System.out.println("\nPossiveis Movimentos: ");
                System.out.println(posicoesValidas);
                escolhaPosicao = sc.nextInt();
                if(escolhaPosicao<0 || escolhaPosicao > posicoes.size()){
                    System.out.println("\nEscolha uma posição válida!");
                }
                posicao = posicoes.get(escolhaPosicao-1);
            }while(escolhaPosicao<0 || escolhaPosicao > posicoes.size());


            //movimentacao da peca escolhida p posicao desejada
            j1.moverPeca(pecaSelecionada, posicao, tabuleiro, j2);
            System.out.println(validarVitoria(j2));
        }while(!validarVitoria(j1) && !validarVitoria(j2));

    }
    private static boolean validarVitoria(Jogador adversario){
        for(Peca peca : adversario.getPecas()){
            if(peca instanceof Rei){
                return false;
            }
        }
        return true;
    }

    private static void imprimirTabuleiro(Tabuleiro tabuleiro){

        int index = 1;

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
    }

}
