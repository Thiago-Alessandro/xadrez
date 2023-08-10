import java.util.ArrayList;

public class Rei extends Peca{

    private boolean primeiroMov = true;

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
                    System.out.println("direita " + posicao);
                    verificaPeca(posicao, possiveisMovimentos);
                } else if(validarExtremidade(posicaoNoTabuleiro) && //verifica extremidade da esquerda
                        !(indice == posicaoNoTabuleiro + 7 ||
                                indice == posicaoNoTabuleiro - 1 ||
                                indice == posicaoNoTabuleiro - 9 )){

                    verificaPeca(posicao, possiveisMovimentos);
                } else if (!validarExtremidade(posicaoNoTabuleiro) &&
                           !validarExtremidade(posicaoNoTabuleiro + 1)){
                    verificaPeca(posicao, possiveisMovimentos);
                }


            }
        }
        return possiveisMovimentos;
    }

    public void fazerRoque(Tabuleiro tabuleiro, Posicao posicao, int numRoque){

        // move o rei para a posição desejada (2 casas ao lado)
        posicao.setPeca(this);
        this.getPosicao().setPeca(null);
        this.setPosicao(posicao);
        System.out.println(tabuleiro.getPosicoes().indexOf(this.getPosicao()));
        System.out.println(this.getPosicao());

        int indicePosicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(this.getPosicao());

        Posicao velhaPosTorre;
        Posicao novaPosTorre;

        if (numRoque == 1) {

            //se poder mover 2 casas (roque curto)
            velhaPosTorre = tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro + 1); //posição no canto (agora o rei esta do lado)
            novaPosTorre = tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro - 1);  //posição do lado contrario do rei

        } else{ //if(numRoque == 2)

            //fazer roque longo

            velhaPosTorre = tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro - 2); //posição no canto (agora o rei esta do lado)
            novaPosTorre = tabuleiro.getPosicoes().get(indicePosicaoNoTabuleiro + 1);  //posição do lado contrario do rei
        }
        //move a torre para o outro lado do rei
        novaPosTorre.setPeca(velhaPosTorre.getPeca());
        velhaPosTorre.setPeca(null);
        novaPosTorre.getPeca().setPosicao(novaPosTorre);
    }

    public void setPrimeiroMov(boolean primeiroMov) {
        this.primeiroMov = primeiroMov;
    }

    public boolean getPrimeiroMov(){
        return primeiroMov;
    }

    @Override
    public String toString() {
        return "R" +  this.getCor().charAt(0);
    }
    //esta dando prolema nos cantos
}
