import java.util.*;

public class Main {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Jogador j1 = new Jogador("Romario");
        Jogador j2 = new Jogador("Roberto");

        Tabuleiro tabuleiro = new Tabuleiro();

        j1.setCor("Branco", tabuleiro);
        j2.setCor("Preto",tabuleiro);
        Jogador jogadorAtual = j1;

        do {
            j1.setCor("Branco", tabuleiro);//atualiza as pecas dos jogadores a cada turno;
            j2.setCor("Preto",tabuleiro);
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
                    } else {
                        System.out.println("\nInsira um indice de 1 à 64");
                        podeSelecionar = false;
                    }
                } while (!podeSelecionar);

                //escolha da posicao p movimentar

                posicoes = pecaSelecionada.possiveisMovimentos(tabuleiro);
                StringBuilder posicoesValidas = new StringBuilder("\n");
                int index = 1;
                if(pecaSelecionada instanceof Rei){
                    Jogador finalJogadorAtual = jogadorAtual;
                    posicoes.removeIf(posicao -> verificaPosicaoSendoAtacada(tabuleiro, posicao, finalJogadorAtual));
                    if(((Rei) pecaSelecionada).getPrimeiroMov()) {
                        if (verificaRoqueCurto(tabuleiro, pecaSelecionada, jogadorAtual)) {
                            posicoes.add(tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(pecaSelecionada.getPosicao()) + 2));
                        }
                        if (verificaRoqueLongo(tabuleiro, pecaSelecionada, jogadorAtual)) {
                            posicoes.add(tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(pecaSelecionada.getPosicao()) - 2));
                        }
                    }
                }
                posicoes.removeAll(getMovimentosInvalidos(pecaSelecionada, tabuleiro, jogadorAtual));

                for (Posicao posicao : posicoes) {
                    posicoesValidas.append(index).append(" - ").append(posicao).append(" ").append(tabuleiro.getPosicoes().indexOf(posicao) + 1).append("\n");//talvez+1
                    index++;
                }

                posicoesValidas.append("0 - voltar");

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
                        tabuleiro.getPosicoes().contains(posicaoEscolhida) && tabuleiro.getPosicoes().indexOf(posicaoEscolhida) < 8) {
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

            if(jogadorAtual == j1){
                jogadorAtual = j2;
            } else {
                jogadorAtual = j1;
            }

        }while(!verificaChequeMate(tabuleiro, j1.getRei(), j1) && !verificaChequeMate(tabuleiro, j2.getRei(), j2));

        if(verificaChequeMate(tabuleiro, j2.getRei(), j2)){
            System.out.println("Parabéns " + j1.getNome());
        } else {
            System.out.println("Parabéns " + j2.getNome());
        }
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
        ArrayList<Posicao> posicoes = tabuleiro.getPosicoes();
        if(((Rei) peca).getPrimeiroMov()) {
            if (posicoes.get(indicePosicao + 1).getPeca() == null) {
                if (posicoes.get(indicePosicao + 2).getPeca() == null) {
                    if (posicoes.get(indicePosicao + 3).getPeca() != null) { //pos da torre
                        return !verificaPosicaoSendoAtacada(tabuleiro, posicoes.get(indicePosicao), jogadorAtual) &&
                                !verificaPosicaoSendoAtacada(tabuleiro, posicoes.get(indicePosicao + 1), jogadorAtual) &&
                                !verificaPosicaoSendoAtacada(tabuleiro, tabuleiro.getPosicoes().get(indicePosicao + 2), jogadorAtual) &&
                                posicoes.get(indicePosicao + 3).getPeca() instanceof Torre &&
                                ((Torre) posicoes.get(indicePosicao + 3).getPeca()).getPrimeiroMov();
                    }
                }
            }
        }
        return false;
    }

    public static boolean verificaRoqueLongo(Tabuleiro tabuleiro, Peca peca, Jogador jogadorAtual){

        int indicePosicao = tabuleiro.getPosicoes().indexOf(peca.getPosicao() );
        ArrayList<Posicao> posicoes = tabuleiro.getPosicoes();
        if(((Rei) peca).getPrimeiroMov()) {
            if (posicoes.get(indicePosicao - 1).getPeca() == null) {
                if (posicoes.get(indicePosicao - 2).getPeca() == null) {
                    if (posicoes.get(indicePosicao - 3).getPeca() == null) {
                        if (posicoes.get(indicePosicao - 4).getPeca() != null) { //pos da torre
                            return !verificaPosicaoSendoAtacada(tabuleiro, posicoes.get(indicePosicao), jogadorAtual) &&
                                    !verificaPosicaoSendoAtacada(tabuleiro, posicoes.get(indicePosicao - 1), jogadorAtual) &&
                                    !verificaPosicaoSendoAtacada(tabuleiro, tabuleiro.getPosicoes().get(indicePosicao - 2), jogadorAtual) &&
                                    !verificaPosicaoSendoAtacada(tabuleiro, tabuleiro.getPosicoes().get(indicePosicao - 3), jogadorAtual) &&
                                    posicoes.get(indicePosicao - 4).getPeca() instanceof Torre &&
                                    ((Torre) posicoes.get(indicePosicao - 4).getPeca()).getPrimeiroMov(); //falta o longo (outra funcao)
                        }
                    }
                }
            }
        }
        return false;
    }

    public static ArrayList<Posicao> getMovimentosInvalidos(Peca peca, Tabuleiro tabuleiro, Jogador jogadorAtual){
        ArrayList<Posicao> movimentos = peca.possiveisMovimentos(tabuleiro);
        int posicaoOriginal = tabuleiro.getPosicoes().indexOf(peca.getPosicao());
        Peca pecaCapturada;
        ArrayList<Posicao> possicoesARemover = new ArrayList<>();
        for(Posicao posicao : movimentos){

            pecaCapturada = peca.mover(tabuleiro, posicao);

            if(verificaPosicaoSendoAtacada(tabuleiro, jogadorAtual.getRei().getPosicao(), jogadorAtual)){
                possicoesARemover.add(posicao);
            }
            peca.desmover(tabuleiro.getPosicoes().get(posicaoOriginal), pecaCapturada);
        }
        return possicoesARemover;
    }

    private static boolean verificaChequeMate(Tabuleiro tabuleiro, Peca rei, Jogador jogadorAtual){

        if(verificaPosicaoSendoAtacada(tabuleiro, rei.getPosicao(), jogadorAtual)){
            for(Peca peca : jogadorAtual.getPecas()){
                ArrayList<Posicao> possiveisMovimentos = peca.possiveisMovimentos(tabuleiro);
                possiveisMovimentos.removeIf(posicao -> verificaPosicaoSendoAtacada(tabuleiro, posicao, jogadorAtual));
                possiveisMovimentos.removeAll(getMovimentosInvalidos(peca, tabuleiro, jogadorAtual));
                if(!(possiveisMovimentos.size() > 0)){
                    return true;
                }
            }
        }
        return false;
    }

}
