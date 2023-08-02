import java.util.ArrayList;

public abstract class Peca {

    private String cor;
    private Posicao posicao;

    String simbolo;

    public Peca(String nome, String cor, Posicao posicao){
        this.cor = cor;
        this.posicao = posicao;
        this.simbolo = nome;
    }

    public boolean mover(Tabuleiro tabuleiro, Posicao posicao){
        //recebe por parametro a posicao para onde vai

        ArrayList<Posicao> possiveisPosicoes = possiveisMovimentos(tabuleiro);

        for(Posicao posicaoPossivel : possiveisPosicoes){

            if(posicaoPossivel == posicao) {
                //atribuindo a peca para a nova posicao (no tableiro)
                posicao.setPeca(this);
                //removendo a peca da posicao antesrior
                this.posicao.setPeca(null);
                //trocando a posicao atual da peca
                this.posicao = posicao;
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

    public String getSimbolo(){
        return simbolo;
    }

}
