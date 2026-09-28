package org.example;
import java.util.Scanner;
import java.util.Random;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("JOC X ȘI 0");

        tabla tabla1=new tabla();
        tabla1.liniiColoane();

        Scanner scanner=new Scanner(System.in);
        Random random=new Random();
        char jucatorCurent='X';

        while(true) {
            System.out.println("Este randul lui " + jucatorCurent);
            int rand;
            int coloana;

            if(jucatorCurent=='X'){
                System.out.println("Randul (1-3): ");
                rand=scanner.nextInt();
                System.out.println("Coloana (1-3): ");
                coloana=scanner.nextInt();
            }else{
                System.out.println("Calculatorul se gandeste la cea mai buna miscare...");
               int[] mutare=tabla1.gasesteMutareaPerfecta('0', 'X');
               rand=mutare[0];
               coloana=mutare[1];
            }

            boolean mutareReusita = tabla1.locMutare(rand, coloana, jucatorCurent);

            if (mutareReusita) {
                System.out.println();
                tabla1.liniiColoane();


                if (tabla1.VerificaCastigator(jucatorCurent)) {
                    System.out.println("Felicitari! Jucatorul " + jucatorCurent + " a castigat!");
                    break;
                }
                if (tabla1.TablaEstePlina()) {
                    System.out.println("Remiza!!!");
                    break;
                }

                if (jucatorCurent == 'X') {
                    jucatorCurent = '0';
                } else {
                    jucatorCurent = 'X';
                }
            }
        }


    }

}
