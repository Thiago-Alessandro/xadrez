import java.util.ArrayList;

public class Peao  extends Peca{

    private boolean primeiroMov = true;

    public Peao(String cor, Posicao posicao){
        super("P", cor, posicao);
    }

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {


        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        if(this.getCor().equals("Preto")) {//se a minha peca for preta
            if (posicoesTabuleiro.get(posicaoNoTabuleiro+8).getPeca()==null){//se a frente estiver nulo
                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro + 8));

                if (this.primeiroMov) {//se for o primeiro movimento do peao
                    if (posicoesTabuleiro.get(posicaoNoTabuleiro + 16).getPeca() == null) {
                        possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro + 16));
                    }
                }
            }
            //verifica diagonais
            if(posicoesTabuleiro.get(posicaoNoTabuleiro + 9).getPeca() != null
                    && posicoesTabuleiro.get(posicaoNoTabuleiro + 9).getPeca().getCor().equals("Branco")
                    && !validarExtremidade(posicaoNoTabuleiro + 1)){

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro+9));

            }
            if(posicoesTabuleiro.get(posicaoNoTabuleiro + 7).getPeca()!= null
                    && posicoesTabuleiro.get(posicaoNoTabuleiro + 7).getPeca().getCor().equals("Branco")
                    &&  !validarExtremidade(posicaoNoTabuleiro)){

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro+7));

            }
        }else if(this.getCor().equals("Branco")){

            if (posicoesTabuleiro.get(posicaoNoTabuleiro-8).getPeca()==null) {
                possiveisMovimentos.add(tabuleiro.getPosicoes().get(posicaoNoTabuleiro - 8));

                if (this.primeiroMov) {
                    if (posicoesTabuleiro.get(posicaoNoTabuleiro - 16).getPeca() == null) {
                        possiveisMovimentos.add(tabuleiro.getPosicoes().get(posicaoNoTabuleiro - 16));
                    }
                }
            }
            if(posicoesTabuleiro.get(posicaoNoTabuleiro - 9).getPeca() != null
                    && posicoesTabuleiro.get(posicaoNoTabuleiro - 9).getPeca().getCor().equals("Preto")
                    && !validarExtremidade(posicaoNoTabuleiro)){

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro+9));

            }
            if(posicoesTabuleiro.get(posicaoNoTabuleiro - 7).getPeca()!=null &&
                    posicoesTabuleiro.get(posicaoNoTabuleiro - 7).getPeca().getCor().equals("Preto")
                    && !validarExtremidade(posicaoNoTabuleiro + 1)){

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro+7));

            }
        }
        return possiveisMovimentos;
    }

    public void setPrimeiroMov(boolean primeiroMov) {
        this.primeiroMov = primeiroMov;
    }

    @Override
    public String toString() {
        return "P" + this.getCor().substring(0,1);
    }
}
