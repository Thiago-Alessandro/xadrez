import java.util.ArrayList;

public class Rei extends Peca{

    private boolean primeiroMov;

    public Rei(String cor, Posicao posicao){
        super("R", cor, posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {

        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        for (Posicao posicao:
                posicoesTabuleiro) {
            int indice = posicoesTabuleiro.indexOf(posicao);
            if(indice == posicaoNoTabuleiro - 9 ||
                    indice == posicaoNoTabuleiro - 8 ||
                    indice == posicaoNoTabuleiro - 7 ||
                    indice == posicaoNoTabuleiro - 1 ||
                    indice == posicaoNoTabuleiro + 1 ||
                    indice == posicaoNoTabuleiro + 7 ||
                    indice == posicaoNoTabuleiro + 8 ||
                    indice == posicaoNoTabuleiro + 9){

                if(validarExtremidade(posicaoNoTabuleiro + 1) && //verificaextremidade da direita
                !(indice == posicaoNoTabuleiro -7 ||
                        indice == posicaoNoTabuleiro + 1 ||
                        indice == posicaoNoTabuleiro + 9 )){

                    verificaPeca(posicao, possiveisMovimentos);
                }

                if(validarExtremidade(posicaoNoTabuleiro) && //verifica extremidade da esquerda
                        !(indice == posicaoNoTabuleiro + 7 ||
                                indice == posicaoNoTabuleiro - 1 ||
                                indice == posicaoNoTabuleiro - 9 )){

                    verificaPeca(posicao, possiveisMovimentos);
                }

            }
        }
        return possiveisMovimentos;
    }

    @Override
    public String toString() {
        return "R" +  this.getCor().substring(0,1);
    }
}
