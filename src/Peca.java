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

    public boolean mover(Tabuleiro tabuleiro, Posicao posicao){
        //recebe por parametro a posicao para onde vai

        int indicePosicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(this.posicao);
        if(this instanceof Rei && ((Rei) this).getPrimeiroMov())  {
            //se a opcao for um rei
            int numRoque = 0;
            if(tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro + 2) == posicao ){
                numRoque = 1; //roque curto
            } else if (tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro - 2) == posicao ){
                numRoque = 2; //roque longo
            }
            if(numRoque == 1 || numRoque == 2){
                ((Rei) this).fazerRoque(tabuleiro, posicao, numRoque);
                return true;    //se fizer o roque retorna a funcao
            }
        }
            ArrayList<Posicao> possiveisPosicoes = possiveisMovimentos(tabuleiro);

            for (Posicao posicaoPossivel : possiveisPosicoes) {

                if (posicaoPossivel == posicao) {
                    //atribuindo a peca para a nova posicao (no tableiro)
                    posicao.setPeca(this);
                    //removendo a peca da posicao antesrior
                    this.posicao.setPeca(null);
                    //trocando a posicao atual da peca
                    this.posicao = posicao;

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
                    return true;
                }
            }
        return false;
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
