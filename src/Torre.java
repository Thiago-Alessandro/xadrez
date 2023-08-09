import java.util.ArrayList;

public class Torre extends Peca{

    private boolean primeiroMov;

    public Torre(String cor, Posicao posicao){
        super("T", cor, posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        //para cima
        for(int i = posicaoNoTabuleiro + 8;
            i < posicoesTabuleiro.size();
            i += 8){

            if (verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos)
//                    ||validarExtremidade(i)
            ) {
                break;
            }
        }

        //para baixo
        for(int i = posicaoNoTabuleiro - 8;
            i >= 0;
            i -= 8){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos)
//                    || validarExtremidade(i + 1)
            ){
                break;
            }
        }

        //direita
        for(int i = (validarExtremidade(posicaoNoTabuleiro + 1) ? 64 : posicaoNoTabuleiro + 1);
            i < posicoesTabuleiro.size();
            i ++){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos) || validarExtremidade(i + 1)){
                break;
            }
        }

        //esquerda
        for(int i = (validarExtremidade(posicaoNoTabuleiro) ? -1 : posicaoNoTabuleiro - 1);//tava -9
            i >= 0;
            i --){

            if(verificaPeca(posicoesTabuleiro.get(i),
                    possiveisMovimentos) || validarExtremidade(i)){
                break;
            }
        }
        return possiveisMovimentos;
    }

    public void setPrimeiroMov(boolean primeiroMov) {
        this.primeiroMov = primeiroMov;
    }

    public boolean getPrimeiroMov(){
        return primeiroMov;
    }

    @Override
    public String toString() {
        return "T" +  this.getCor().charAt(0);
    }
}
