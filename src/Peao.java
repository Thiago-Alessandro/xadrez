import java.util.ArrayList;

public class Peao  extends Peca{

    private boolean primeiroMov = true;

    public Peao(String cor, Posicao posicao){
        super("P", cor, posicao);
    }

    private boolean segundoMov = false;

    @Override
    public ArrayList<Posicao> possiveisMovimentos(Tabuleiro tabuleiro) {
       // System.out.println("vendo possiveis movs do peao");
//        0
        ArrayList<Posicao> possiveisMovimentos = new ArrayList<>();

        Posicao posicaoAtual = this.getPosicao();

        int posicaoNoTabuleiro = tabuleiro.getPosicoes().indexOf(posicaoAtual);

        ArrayList<Posicao> posicoesTabuleiro = tabuleiro.getPosicoes();

        if(this.getCor().equals("Preto")) {//se a minha peca for preta

           // System.out.println("peao preto");
            //System.out.println(posicaoNoTabuleiro);

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

            //en peassant
            if(posicaoNoTabuleiro >= 32 && posicaoNoTabuleiro <= 39){ //linha do en peassant mudaraaaaa (ta com problema)

                Peca peaoInimigoEsquerda = posicoesTabuleiro.get(posicaoNoTabuleiro + 1).getPeca();
                Posicao posEnpassantEsquerda = posicoesTabuleiro.get(posicoesTabuleiro.indexOf( posicoesTabuleiro.indexOf( peaoInimigoEsquerda.getPosicao()) + 8));
                Peca peaoInimigoDireita = posicoesTabuleiro.get(posicaoNoTabuleiro - 1).getPeca();
                Posicao posEnpassantDireita = posicoesTabuleiro.get(posicoesTabuleiro.indexOf( posicoesTabuleiro.indexOf( peaoInimigoDireita.getPosicao()) + 8));

                if(posicaoNoTabuleiro != 39 && peaoInimigoEsquerda != null &&
                        peaoInimigoEsquerda.getCor() != this.getCor() )         //verificar com atributo "possivelEnpeassant"
                {
                    possiveisMovimentos.add(posEnpassantEsquerda);

                } else if(posicaoNoTabuleiro != 32 && peaoInimigoDireita != null &&
                        peaoInimigoDireita.getCor() != this.getCor() ){ //verificar com atributo "possivelEnpeassant"

                    possiveisMovimentos.add(posEnpassantDireita);
                }
            }

        }else if(this.getCor().equals("Branco")){

           // System.out.println("peao branco");
           // System.out.println(posicaoNoTabuleiro);

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

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro-9)); //tava +9 e em baixo +7

            }
            if(posicoesTabuleiro.get(posicaoNoTabuleiro - 7).getPeca()!=null &&
                    posicoesTabuleiro.get(posicaoNoTabuleiro - 7).getPeca().getCor().equals("Preto")
                    && !validarExtremidade(posicaoNoTabuleiro + 1)){

                possiveisMovimentos.add(posicoesTabuleiro.get(posicaoNoTabuleiro-7));

            }

            //en peassant
            if(posicaoNoTabuleiro >= 24 && posicaoNoTabuleiro <= 31){ //linha do en peassant
                //System.out.println("branco linha en passant");
                Peca peaoInimigoEsquerda = tabuleiro.getPosicoes().get(posicaoNoTabuleiro - 1).getPeca();
                Peca peaoInimigoDireita = tabuleiro.getPosicoes().get(posicaoNoTabuleiro + 1).getPeca();

                if(posicaoNoTabuleiro != 24 && peaoInimigoEsquerda != null &&
                        peaoInimigoEsquerda.getCor() != this.getCor() )         //verificar com atributo "possivelEnpeassant"
                {
                   // System.out.println("posicoesTabuleiro");System.out.println(tabuleiro);            --------------------------
                    Posicao posEnpassantEsquerda = posicoesTabuleiro.get(posicoesTabuleiro.indexOf(peaoInimigoEsquerda.getPosicao())-8);
                            //posicoesTabuleiro.get(posicoesTabuleiro.indexOf( posicoesTabuleiro.indexOf( peaoInimigoEsquerda.getPosicao()) - 8 ));
                    possiveisMovimentos.add(posEnpassantEsquerda);

                } else if(posicaoNoTabuleiro != 31 && peaoInimigoDireita != null &&
                        !peaoInimigoDireita.getCor().equals(this.getCor())){ //verificar com atributo "possivelEnpeassant"

                    Posicao posEnpassantDireita = posicoesTabuleiro.get(posicoesTabuleiro.indexOf( posicoesTabuleiro.indexOf( peaoInimigoDireita.getPosicao()) - 8 ));
                    possiveisMovimentos.add(posEnpassantDireita);
                }
            }

        }
        return possiveisMovimentos;
    }

    public boolean isPrimeiroMov() {
        return primeiroMov;
    }

    public void setPrimeiroMov(boolean primeiroMov) {
        this.primeiroMov = primeiroMov;
    }

    public boolean isSegundoMov() {
        return segundoMov;
    }

    public void setSegundoMov(boolean segundoMov) {
        this.segundoMov = segundoMov;
    }

    @Override
    public String toString() {
        return "P" + this.getCor().charAt(0);
    }
}
