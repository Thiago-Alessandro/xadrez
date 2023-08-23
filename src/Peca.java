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

        boolean enPassant = false;

        if(this instanceof Peao){
            if(cor.equals("Preto")) {
                if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 7 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro - 1).getPeca() instanceof Peao) {
                    enPassant = true;
                } else if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro + 9 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro + 1).getPeca() instanceof Peao) {
                    enPassant = true;
                }
            } else if (cor.equals("Branco")){
                if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 7 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro + 1).getPeca() instanceof Peao) {
                    enPassant = true;
                } else if (tabuleiro.getPosicoes().indexOf(posicao) == posNoTabuleiro - 9 && posicao.getPeca() == null && tabuleiro.getPosicoes().get(posNoTabuleiro - 1).getPeca() instanceof Peao) {
                    enPassant = true;
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

        //recupera a peca capturada
        pecaCapturada = posicao.getPeca();
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

        //en passant
        //OBS: o peao aliado já moveu por isso calculo invertido
        if(this instanceof Peao && cor.equals("Branco")){
            Peca peaoRemover = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 8).getPeca();

            if(enPassant){
                pecaCapturada = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 8).getPeca();
                if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 7){

                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                }

                else if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) + 9){
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                }
                pecaCapturada.setPosicao(tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) + 8));
            }
        } else if (this instanceof Peao && cor.equals("Preto") ) {

            Peca peaoRemover = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 8).getPeca();

            if(enPassant){

                pecaCapturada = tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 8).getPeca();

                if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 7){
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                }
                else if(posNoTabuleiro == tabuleiro.getPosicoes().indexOf(posicao) - 9) {
                    peaoRemover.getPosicao().setPeca(null);
                    peaoRemover.setPosicao(null);
                }
                pecaCapturada.setPosicao(tabuleiro.getPosicoes().get(tabuleiro.getPosicoes().indexOf(posicao) - 8));
            }
        }
        return pecaCapturada;
    }


    public void desmover(Posicao posicao, Peca pecaCapturada){ //posicao é aonde vai

        //atribuindo a peca para a nova posicao (no tableiro)
        posicao.setPeca(this);
        //removendo a peca da posicao antesrior
        this.posicao.setPeca(null);
        //trocando a posicao atual da peca
        this.posicao = posicao;

        if(pecaCapturada != null && pecaCapturada.getPosicao() != null){
            pecaCapturada.getPosicao().setPeca(pecaCapturada);
        }

        if (this instanceof Torre) {
            ((Torre) this).setPrimeiroMov(true);
        }
        if (this instanceof Peao) {
            ((Peao) this).setPrimeiroMov(true);
        }
        if (this instanceof Rei) {
            ((Rei) this).setPrimeiroMov(true);
        }
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

    public Posicao getPosicao() {
        return posicao;
    }

    public String getCor() {
        return cor;
    }

    @Override
    public String toString() {
        return "Peca: " +
                "cor: " + cor;
    }

}
