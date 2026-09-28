package org.example;

public class tabla {
    private char[][] tabla;

    public tabla() {
        tabla = new char[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                tabla[i][j] = ' ';
    }
    public void liniiColoane(){
        for(int i=0;i<3;i++){
            System.out.println( " "+ tabla[i][0]+ " | "+ tabla[i][1]+" | "+ tabla[i][2]);
         if(i<2)
             System.out.println("____________");

        }
        System.out.println();


    }
    public boolean locMutare(int rand, int coloana, char jucator){
        int i=rand-1;
        int j=coloana-1;

        if(i<0||j<0||i>=3||j>=3 ){
            System.out.println("Mutare invalida! Incearca un numar intre 1 si 3");
            return false;
        }
        if(tabla[i][j]!=' '){
            System.out.println("Casuta este deja ocupta! Incearca alta");
            return false;
        }

        tabla[i][j]=jucator;
        return true;
    }

    public boolean VerificaCastigator(char jucator){
        for(int i=0;i<3;i++){
            if(tabla[i][0]==jucator && tabla[i][1]==jucator && tabla[i][2]==jucator){
                return true;
            }
        }
        for(int j=0;j<3;j++){
            if(tabla[0][j]==jucator && tabla[1][j]==jucator && tabla[2][j]==jucator){
                return true;
            }
        }
        for(int j=0;j<3;j++){
            if(tabla[0][0]==jucator && tabla[1][1]==jucator && tabla[2][2]==jucator){
                return true;
            }
        }
        for(int j=0;j<3;j++){
            if(tabla[0][2]==jucator && tabla[1][1]==jucator && tabla[2][0]==jucator){
            return true;}

        }
        return false;
    }
    public boolean TablaEstePlina(){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(tabla[i][j]==' '){
                    return false;
                }
            }
        }
        return true;
    }
    public int[] gasesteMutare(char jucator){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(tabla[i][j]==' '){
                    tabla[i][j]=jucator;
                    if(VerificaCastigator(jucator)){
                        tabla[i][j]=' ';
                        return new int[]{i+1,j+1};
                    }
                    tabla[i][j]=' ';
                }
            }
        }
        return null;
    }

    public int minimx(boolean esteRandulAI, char simbolAI, char simbolOm){
        if(VerificaCastigator(simbolAI)) return 10;
        if(VerificaCastigator(simbolOm)) return -10;
        if(TablaEstePlina()) return 0;

        if(esteRandulAI){
            int celMaiBunScor=-1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(tabla[i][j]==' '){
                        tabla[i][j]=simbolAI;
                        int scor=minimx(false, simbolAI,simbolOm);
                        tabla[i][j]=' ';
                        if(scor>celMaiBunScor){ celMaiBunScor=scor; }
                    }
                }
            }
            return celMaiBunScor;
        }else{
            int celMaiBunScor=1000;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    if(tabla[i][j]==' '){
                        tabla[i][j]=simbolOm;
                        int scor=minimx(true, simbolAI,simbolOm);
                        tabla[i][j]=' ';

                        if(scor<celMaiBunScor){ celMaiBunScor=scor; }

                    }
                }
            }
            return celMaiBunScor;

        }
    }
    public  int[] gasesteMutareaPerfecta(char simbolAI, char simbolOm){
        int celMaiBunScor=-1000;
        int[] ceaMaiBunaMutare=new int[2];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(tabla[i][j]==' '){
                    tabla[i][j]=simbolAI;
                    int scor=minimx(false, simbolAI,simbolOm);
                    tabla[i][j]=' ';
                    if(scor>celMaiBunScor){
                        celMaiBunScor=scor;
                        ceaMaiBunaMutare[0]=i+1;
                        ceaMaiBunaMutare[1]=j+1;
                    }
                }
            }
        }
        return ceaMaiBunaMutare;
    }






}

