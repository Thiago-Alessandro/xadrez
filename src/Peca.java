import java.util.ArrayList;

public abstract class Peca {

    private final String cor;
    private Posicao posicao;

    String simbolo;

    public Peca(String nome, String cor, Posicao posicao){
        this.cor = cor;
        this.posicao = posicao;
        this.simbolo = nome;
    }

    public Peca mover(Tabuleiro tabuleiro, Posicao posicao){ //posicao é aonde vai (botar no peao)

        Peca pecaCapturada;

        int posNoTabuleiro = tabuleiro.getPosicoes().indexOf(this.posicao);

        Boolean enPassant = false;

        if(this instanceof Peao){
            if(cor.equals("Preto")) {
                if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 7 ||
                        (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 7 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro - 1).getPeca() instanceof Peao)) {
                    enPassant = true;
                } else if ( tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 9 ||
                        (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 9 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro + 1).getPeca() instanceof Peao)) {
                    enPassant = true;
                }
            } else if (cor.equals("Branco")){
                if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 7 ||
                       ( tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 7 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro + 1).getPeca() instanceof Peao)) {
                    enPassant = true;
                } else if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 9 ||
                       ( tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 9 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro - 1).getPeca() instanceof Peao)) {
                    enPassant = true;
                    if(tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 9){
                        System.out.println("1");
                    } if( tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 9 ){
                        System.out.println("2");
                    } if( posicao.getPeca() == null){
                        System.out.println("3");
                    } if(tabuleiro.getPosicoes().get(posNoTabuleiro - 1).getPeca() instanceof Peao){
                        System.out.println("4");
                    }
                }
            }
        }



        if(this instanceof Rei && ((Rei) this).getPrimeiroMov())  {
            //se a opcao for um rei
            int numRoque = 0;
            if(tabuleiro.getPosicoes().get(posNoTabuleiro + 2) == posicao ){
                numRoque = 1; //roque curto
            } else if (tabuleiro.getPosicoes().get(posNoTabuleiro - 2) == posicao ){
                numRoque = 2; //roque longo
            }
            if(numRoque == 1 || numRoque == 2){
                ((Rei) this).fazerRoque(tabuleiro, posicao, numRoque);
                return null; //se fizer o roque retorna a funcao
            }
        }

        ArrayList<Posicao> possiveisPosicoes = possiveisMovimentos(tabuleiro);

//        System.out.println("aaa");
//        System.out.println(posicao);
//        System.out.println(tabuleiro.getPosicoes().indexOf(posicao));
//        System.out.println(this.posicao);
//        System.out.println(tabuleiro.getPosicoes().indexOf(this.posicao));
//        System.out.println(tabuleiro);
//        System.out.println("aaaaa");

        //recupera a peca capturada
        pecaCapturada = posicao.getPeca();
        //atribuindo a peca para a nova posicao (no tableiro)
        posicao.setPeca(this);
        //removendo a peca da posicao antesrior
        this.posicao.setPeca(null);
        //trocando a posicao atual da peca
        this.posicao = posicao;

        //System.out.println(tabuleiro);



        //setando primeiro moveimento como falso
        if (this instanceof Torre) {
            ((Torre) this).setPrimeiroMov(false);
        }
        if (this instanceof Peao) {
            ((Peao) this).setPrimeiroMov(false);
        }
        if (this instanceof Rei) {
            ((Rei) this).setPrimeiroMov(false);
        }

        //en passant (remove o peão adversario)
        //OBS: o peao aliado já moveu por isso calculo invertido
        if(this instanceof Peao && cor.equals("Branco")){
           // System.out.println(tabuleiro);
            Peca peaoRemover = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 8).getPeca();
//            System.out.println(tabuleiro.getPosicoes().indexOf(posicao) - 8);
//            System.out.println(tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 8));
//            System.out.println(posicao);
//            System.out.println(tabuleiro.getPosicoes().indexOf(posicao));

            //System.out.println(posNoTabuleiro);
//            System.out.println(tabuleiro.getPosicoes().indexOf(posicao) + 7);
//            System.out.println(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 9);
           // System.out.println(posicao);
            //System.out.println(tabuleiro.getPosicoes().indexOf(posicao));
            System.out.println(posNoTabuleiro);
            if(enPassant){
                if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 7){

                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                    //((Peao) this).ultimaJogadaEnPassant = true;
                } else if (posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 7 ){

                    Posicao posicaoRecolocar = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 1);
                    posicaoRecolocar.setPeca(new Peao("Preto", posicaoRecolocar));
                    //teria que resetar as pecas no jogador mas vou fazer isso no main

                }else if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 9){
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                    //((Peao) this).ultimaJogadaEnPassant = true;
                } else if (posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 9 ){
                    Posicao posicaoRecolocar = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 1);
                    posicaoRecolocar.setPeca(new Peao("Preto", posicaoRecolocar));
                }
            }
        } else if (this instanceof Peao && cor.equals("Preto") ) {

            Peca peaoRemover = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 8).getPeca();

            if(enPassant){
                if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 7){
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                   // ((Peao) this).ultimaJogadaEnPassant = true;
                } else if (posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 7 ){
                    Posicao posicaoRecolocar = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 1);
                    posicaoRecolocar.setPeca(new Peao("Branco", posicaoRecolocar));
                    //teria que resetar as pecas no jogador mas vou fazer isso no main
                }else if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 9){
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                   // ((Peao) this).ultimaJogadaEnPassant = true;
                } else if (posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 9 ){
                    Posicao posicaoRecolocar = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 1);
                    posicaoRecolocar.setPeca(new Peao("Branco", posicaoRecolocar));
                }
            }
        }
        return pecaCapturada;
    }



    public Peca desmover(Tabuleiro tabuleiro, Posicao posicao, Peca pecaCapturada){ //posicao é aonde vai (botar no peao)

        int posNoTabuleiro = tabuleiro.getPosicoes().indexOf(this.posicao);

        if(this instanceof Rei && ((Rei) this).getPrimeiroMov())  { //dando problema no roque quando reCusa e tenta dnv
            //se a opcao for um rei
            int numRoque = 0;
            if(tabuleiro.getPosicoes().get(posNoTabuleiro + 2) == posicao ){
                numRoque = 1; //roque curto
            } else if (tabuleiro.getPosicoes().get(posNoTabuleiro - 2) == posicao ){
                numRoque = 2; //roque longo
            }
            if(numRoque == 1 || numRoque == 2){
                ((Rei) this).fazerRoque(tabuleiro, posicao, numRoque);
                return null; //se fizer o roque retorna a funcao
            }
        }
//        ArrayList<Posicao> possiveisPosicoes;
//        if(this instanceof Peao && this.cor == "Preto" && this.posicao == tabuleiro.getPosicoes().get(35)){
//            possiveisPosicoes= possiveisMovimentos(tabuleiro);
//
//        }
//        possiveisPosicoes= possiveisMovimentos(tabuleiro);

        //atribuindo a peca para a nova posicao (no tableiro)
        posicao.setPeca(this);
        //removendo a peca da posicao antesrior
        this.posicao.setPeca(null);
        //trocando a posicao atual da peca
        this.posicao = posicao;

        if(pecaCapturada != null){
            pecaCapturada.getPosicao().setPeca(pecaCapturada);
        }



        //setando primeiro moveimento como falso (cpa mesmo problema do roque) (ver no roque[setar a torre do roque como true])
        if (this instanceof Torre) {
            ((Torre) this).setPrimeiroMov(true);
        }
        if (this instanceof Peao) {
            ((Peao) this).setPrimeiroMov(true);
        }
        if (this instanceof Rei) {
            ((Rei) this).setPrimeiroMov(true);
        }

        return pecaCapturada;
    }







    public boolean verificaPeca(Posicao posicao, ArrayList<Posicao> possiveisMovimentos) {

        if(posicao.getPeca() == null) {

            possiveisMovimentos.add(posicao);
            return false;
        }
        if(!posicao.getPeca().getCor().equals(this.getCor())){

            possiveisMovimentos.add(posicao);
        }
        return true;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
        if(this instanceof Peao && this.getCor().equals("Branco")){
//            System.out.println("setando posicao");
//            System.out.println(posicao);
        }
    }

    public boolean validarExtremidade(int posicaoNoTabuleiro){//verifica se ele esta no canto (ou perto depende doq passa por parametro)

        return posicaoNoTabuleiro % 8 == 0;
    }

    public abstract ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro);

    //private icone char

    public Posicao getPosicao() {
        return posicao;
    }

    public String getCor() {
        return cor;
    }

    @Override
    public String toString() {
        return "Peca: " +
                "cor: " + cor; //+ '\'' +
               // ", posicao=" + posicao +;
    }


    public boolean verificaPosicaoSendoAtacada(Tabuleiro tabuleiro, Posicao posicaoAVerificar, Jogador jogadorAtual){
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

}
