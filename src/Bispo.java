import java.util.ArrayList;

public class  Bispo extends Peca{

    public Bispo(String cor, Posicao posicao){
        super("B",cor, posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        for(int i = (validarExtremidade(posicaoNoTabuleiro) ? 64 : posicaoNoTabuleiro + 7);
            i < posicoesTabuleiro.size(); i+=7){

            if (verificaPeca(posicoesTabuleiro.get(i), possiveisMovimentos)||validarExtremidade(i)) {
                break;
            }
        }

        for(int i = (validarExtremidade(posicaoNoTabuleiro + 1) ? -1 : posicaoNoTabuleiro - 7);
            i >= 0; i-=7){

            if(verificaPeca(posicoesTabuleiro.get(i), possiveisMovimentos) || validarExtremidade(i + 1) ){
                break;
            }
        }

        for(int i = (validarExtremidade(posicaoNoTabuleiro + 1) ? 64 : posicaoNoTabuleiro + 9);
            i < posicoesTabuleiro.size(); i+=9){

            if(verificaPeca(posicoesTabuleiro.get(i), possiveisMovimentos) || validarExtremidade(i + 1)){
                break;
            }
        }

        for(int i = (validarExtremidade(posicaoNoTabuleiro) ? -1 : posicaoNoTabuleiro - 9);
            i >= 0; i-=9){

            if(verificaPeca(posicoesTabuleiro.get(i), possiveisMovimentos) || validarExtremidade(i)){
                break;
            }
        }
        return possiveisMovimentos;
    }

    @Override
    public String toString() {
        return "B" + this.getCor().charAt(0);
    }
}
