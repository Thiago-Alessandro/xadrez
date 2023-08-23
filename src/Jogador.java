import java.util.ArrayList;

public class Jogador {

    private final String nome;
    private String cor;
    private ArrayList<Peca> pecas;

    public Jogador(String nome){
        this.nome = nome;
        this.pecas = new ArrayList<>();

    }

    public void moverPeca(Peca peca, Posicao posicao, Tabuleiro tabuleiro, Jogador adversario){

        Peca pecaAdversaria = posicao.getPeca();

        peca.mover(tabuleiro,posicao);

        if(pecaAdversaria != null){
            adversario.pecas.remove(pecaAdversaria);
        }
    }

    public void setCor(String cor, Tabuleiro tabuleiro) {
        this.cor = cor;

        for(Posicao posicao:tabuleiro.getPosicoes()){
            if(posicao.getPeca()!=null && posicao.getPeca().getCor().equals(cor)){
                this.pecas.add(posicao.getPeca());
            }
        }
    }

    public String getCor() {
        return cor;
    }

    public void setPecas(ArrayList<Peca> pecas) {
        this.pecas = pecas;
    }

    public ArrayList<Peca> getPecas() {
        return pecas;
    }

    public String getNome(){ return nome;}

    public Peca getRei(){
        for (Peca peca : pecas){
            if(peca instanceof Rei){
                return peca;
            }
        }
        return null;
    }
}
