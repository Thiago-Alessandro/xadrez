import java.util.ArrayList;

public class Rainha extends Peca{

    public Rainha(String cor, Posicao posicao){
        super("D", cor, posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {

        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        //movimentar diagonais
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

        //movimentos horizontais e verticais
        for(int i = posicaoNoTabuleiro + 8;
            i < posicoesTabuleiro.size();
            i += 8){

            if (verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos)||validarExtremidade(i)) {
                break;
            }
        }

        for(int i = posicaoNoTabuleiro - 8;
            i >= 0;
            i -= 8){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos) || validarExtremidade(i + 1) ){
                break;
            }
        }

        for(int i = (validarExtremidade(posicaoNoTabuleiro + 1) ? 64 : posicaoNoTabuleiro + 1);
            i < posicoesTabuleiro.size();
            i ++){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos) || validarExtremidade(i + 1)){
                break;
            }
        }

        for(int i = (validarExtremidade(posicaoNoTabuleiro) ? -1 : posicaoNoTabuleiro - 9);
            i >= 0;
            i --){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos) || validarExtremidade(i)){
                break;
            }
        }
        return possiveisMovimentos;
    }

    @Override
    public String toString() {
        return "D" +  this.getCor().substring(0,1);
    }
}
