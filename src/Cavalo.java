import java.util.ArrayList;

public class Cavalo extends Peca{

    public Cavalo(String cor, Posicao posicao){
        super("C", cor,posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {

        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        if(!validarExtremidade(posicaoNoTabuleiro)) { //valida lateral esquerda

            if(posicaoNoTabuleiro + 15 < 64) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro + 15), possiveisMovimentos);
            }
            if(posicaoNoTabuleiro - 17 > 0) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro - 17), possiveisMovimentos);
            }
        }
        if(!validarExtremidade(posicaoNoTabuleiro -1) && !validarExtremidade(posicaoNoTabuleiro)){
            //valida coluna ao lado da lateral esquerda (e lateral esquerda)
            if(posicaoNoTabuleiro + 6 < 64) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro + 6), possiveisMovimentos);
            }
            if(posicaoNoTabuleiro - 10 > 0) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro - 10), possiveisMovimentos);
            }
        }
        if(!validarExtremidade(posicaoNoTabuleiro + 1)){//valida lateral direita

            if(posicaoNoTabuleiro - 15 > 0) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro - 15), possiveisMovimentos);
            }
            if(posicaoNoTabuleiro + 17 < 64) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro + 17), possiveisMovimentos);
            }
        }
        if(!validarExtremidade(posicaoNoTabuleiro + 2) && !validarExtremidade(posicaoNoTabuleiro + 1)){
            //valida posicao ao lado lateral direita (e a lateral direita)

            if(posicaoNoTabuleiro - 6 > 0) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro - 6), possiveisMovimentos);
            }
            if(posicaoNoTabuleiro + 10 < 64) {
                verificaPeca(posicoesTabuleiro.get(posicaoNoTabuleiro + 10), possiveisMovimentos);
            }
        }
        return possiveisMovimentos;
    }

    @Override
    public String toString() {
        return "C" +  this.getCor().substring(0,1);
    }
}
