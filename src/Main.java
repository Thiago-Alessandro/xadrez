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
                if(pecaSelecionada instanceof Rei){
                    Jogador finalJogadorAtual = jogadorAtual;
                    posicoes.removeIf(posicao -> verificaPosicaoSendoAtacada(tabuleiro, posicao, finalJogadorAtual));
                }
                //aqui aaajhh nao ta certo ainda (rei entre dois peoes afastado 1 posicao)

                for (Posicao posicao : posicoes) {
                    posicoesValidas += index + " - " + posicao + " " + (tabuleiro.getPosicoes().indexOf(posicao) + 1) + "\n";//talvez+1
                    index++;
                }

//                if(verificaRoqueCurto(tabuleiro, pecaSelecionada, jogadorAtual)){
//                    posicoesValidas += index + " - " +
//                }
                //colocar dentro da verificacao do rei e terminar verificacao (talvez retornar posicao na funcao)

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

            //promove o peao
            if(pecaSelecionada instanceof Peao) {
                if (tabuleiro.getPosicoes().indexOf(posicaoEscolhida) > 52 && tabuleiro.getPosicoes().indexOf(posicaoEscolhida) < 64 ||
                    tabuleiro.getPosicoes().indexOf(posicaoEscolhida) >= 0 && tabuleiro.getPosicoes().indexOf(posicaoEscolhida) < 8) {
                    Peca novaPeca = null;
                    do {
                        System.out.println("""
                                Deseja promover seu peão para:
                                1 - Rainha
                                2 - Cavalo
                                3 - Torre
                                4 - Bispo
                                """);
                        int opcao = sc.nextInt();
                        switch (opcao) {
                            case 1 -> novaPeca = new Rainha(pecaSelecionada.getCor(), posicaoEscolhida);
                            case 2 -> novaPeca = new Cavalo(pecaSelecionada.getCor(), posicaoEscolhida);
                            case 3 -> novaPeca = new Torre(pecaSelecionada.getCor(), posicaoEscolhida);
                            case 4 -> novaPeca = new Bispo(pecaSelecionada.getCor(), posicaoEscolhida);
                            default -> System.out.println("Escolha uma opção válida");
                        }
                    } while (novaPeca==null);
                    posicaoEscolhida.setPeca(novaPeca);
                    ArrayList<Peca> novasPecas = jogadorAtual.getPecas();
                    novasPecas.remove(pecaSelecionada);
                    novasPecas.add(novaPeca);
                    jogadorAtual.setPecas(novasPecas);
                }
            }

            //verifica se uma determinada posicao esta sendo atacada
//            if(verificaPosicaoSendoAtacada(tabuleiro, jogadorAtual.getRei().getPosicao(), jogadorAtual)){
//                System.out.println("Seu rei esta sendo atacado");
//            }


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

    private static boolean verificaPosicaoSendoAtacada(Tabuleiro tabuleiro, Posicao posicaoAVerificar, Jogador jogadorAtual){
        boolean testandoPeca = false;
        if(posicaoAVerificar.getPeca() == null){
            posicaoAVerificar.setPeca(new Peao(jogadorAtual.getCor(), posicaoAVerificar));
            testandoPeca = true;
        }

        for(Posicao posicaoTabuleiro : tabuleiro.getPosicoes()){                                                //passa por todas posicoes
            Peca pecaInimiga = posicaoTabuleiro.getPeca();


            if(pecaInimiga!=null && !(pecaInimiga.getCor().equals(jogadorAtual.getCor()) )) {

                for (Posicao posicaoAtacando : pecaInimiga.possiveisMovimentos(tabuleiro)){                //verifica os movimentos da peca inimiga

                    if(posicaoAtacando == posicaoAVerificar){                                               //se a posicao escolhida for um movimento possivel da inimiga

                        if (testandoPeca){
                            posicaoAVerificar.setPeca(null);
                        }
                        return true;                                                                        //retorna que a posição esta sendo atacada
                    }
                }
            }
        }
        if (testandoPeca){
            posicaoAVerificar.setPeca(null);
        }
        return false;
    }

    public static boolean verificaRoqueCurto(Tabuleiro tabuleiro, Peca peca, Jogador jogadorAtual){

        int indicePosicao = tabuleiro.getPosicoes().indexOf(peca.getPosicao() );
        if(((Rei) peca).getPrimeiroMov()){

            if( tabuleiro.getPosicoes().get( indicePosicao + 1 ).getPeca() != null &&
                    tabuleiro.getPosicoes().get( indicePosicao + 2 ).getPeca() != null &&
                    tabuleiro.getPosicoes().get( indicePosicao + 3 ).getPeca() != null &&
                    !verificaPosicaoSendoAtacada( tabuleiro, tabuleiro.getPosicoes().get( indicePosicao + 1 ), jogadorAtual) &&
                    !verificaPosicaoSendoAtacada( tabuleiro, tabuleiro.getPosicoes().get( indicePosicao + 2 ), jogadorAtual) &&
                    ((Torre) tabuleiro.getPosicoes().get(indicePosicao + 3).getPeca()).getPrimeiroMov() ){
                return true; //falta o longo (outra funcao)
            }
        }
        return false;
    }

}
