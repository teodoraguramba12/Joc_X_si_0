package org.example;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.shape.Line;

public class jocGUI extends Application {

    private tabla tablaJoc = new tabla();
    private Button[][] butoane = new Button[3][3];
    private boolean jocInceput=false;
    private Button buttonRestart =new Button("Start");
    private int dificultate=3;

    private StackPane zonaDeJoc = new StackPane();

    private  char simbolOm='X';
    private  char simbolAI='O';

    private boolean simbolAles=false;


    @Override
    public void start(Stage fereastraPrincipala) {
        fereastraPrincipala.setTitle("Joc X si 0");
        VBox cutieprincipala=new VBox(20);
        javafx.scene.layout.VBox cutiePrincipala = new javafx.scene.layout.VBox(20);
        cutiePrincipala.setAlignment(javafx.geometry.Pos.CENTER);


        javafx.scene.control.Label textSus= new javafx.scene.control.Label("Apasă aici ca să incepi jocul");
        textSus.setStyle("-fx-font-size: 16px; -fx-font-weight: normal; -fx-text-fill: #333333;");

        javafx.scene.control.Label textJos= new javafx.scene.control.Label("X  și O");
        textJos.setStyle("-fx-font-size: 30px; -fx-font-weight: 900; -fx-text-fill: #1a4d2e;");


        VBox continutButon=new VBox(5);
        continutButon.setAlignment(javafx.geometry.Pos.CENTER);
        continutButon.getChildren().addAll(textSus,textJos);



        Button btnStartMare=new Button();
        btnStartMare.setGraphic(continutButon);

        btnStartMare.setMaxWidth(260);
        btnStartMare.setStyle(
                "-fx-background-color: lightgreen; " +
                        "-fx-border-color: darkgreen; " +
                        "-fx-border-width: 3px; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-padding: 10px 15px; " +
                        "-fx-cursor: hand;"
        );


        VBox meniuSimbol = new VBox(15);
        meniuSimbol.setAlignment(javafx.geometry.Pos.CENTER);

        Button btnAlegeX = new Button("Vreau să fiu X (Încep primul)");
        Button btnAlege0 = new Button("Vreau să fiu 0 (AI-ul începe)");

        String stilBtnSimbol = "-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-pref-width: 250px;";
        btnAlegeX.setStyle(stilBtnSimbol);
        btnAlege0.setStyle(stilBtnSimbol);

        meniuSimbol.getChildren().addAll(new javafx.scene.control.Label("Alege cu ce vrei să joci:"), btnAlegeX, btnAlege0);

        VBox meniuDificultate = new VBox(15);
        meniuDificultate.setAlignment(javafx.geometry.Pos.CENTER);

        Button btnAlegeSimbol=new Button("1. Alege Simbolul");
        btnAlegeSimbol.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-pref-width: 260px; -fx-background-color: #ffd700; -fx-font-weight: bold;");

        Button btnUsor=new Button("Nivel: Usor (Min)");
        Button btnMediu=new Button("Nivel: Medium (Med)");
        Button btnGreu=new Button("Nivel: Greu (Hard)");




        String stilBtnDif="-fx-font-size: 16px; -fx-padding: 10px 20px; -fx-pref-width: 220px;";
        btnUsor.setStyle(stilBtnDif);
        btnMediu.setStyle(stilBtnDif);
        btnGreu.setStyle(stilBtnDif);

        meniuDificultate.getChildren().addAll(btnAlegeSimbol, new javafx.scene.control.Label("Apoi alege dificultatea pentru a începe:"), btnUsor, btnMediu, btnGreu);

        GridPane grila= new GridPane();
        grila.setAlignment(javafx.geometry.Pos.CENTER);

       for(int i=0;i<3;i++)
           for(int j=0;j<3;j++)
           {
               Button button = new Button();
               button.setMinSize(100,100);
               button.setStyle("-fx-font-size: 40px; -fx-font-weight: bold;");
               butoane[i][j]=button;

               int rand=i;
               int coloana=j;

               button.setOnAction(eveniment -> {

                   if(!jocInceput || verificaFinalJoc() || !button.getText().isEmpty()){
                       return;
                   }
                   tablaJoc.locMutare(rand+1, coloana+1, simbolOm);
                   button.setText(String.valueOf(simbolOm));

                   if(verificaFinalJoc()) return;

                   int[] mutareAI;
                   if(dificultate==1){
                       mutareAI=gasesteMutareAleatoare();
                   }else if(dificultate==2){
                       if(Math.random()<0.5){
                           mutareAI=tablaJoc.gasesteMutareaPerfecta(simbolAI, simbolOm);
                       }else{
                           mutareAI=gasesteMutareAleatoare();
                       }
                   }else{
                       mutareAI=tablaJoc.gasesteMutareaPerfecta(simbolAI, simbolOm);
                   }

                   int randAI=mutareAI[0]-1;
                   int coloanaAI=mutareAI[1]-1;

                   tablaJoc.locMutare(mutareAI[0], mutareAI[1], simbolAI);
                   butoane[randAI][coloanaAI].setText(String.valueOf(simbolAI));

                   verificaFinalJoc();

               });
               grila.add(button,j,i);

           }

       zonaDeJoc.getChildren().addAll(btnStartMare);
       buttonRestart.setStyle("-fx-font-size: 16px; -fx-padding: 10px 20px;");
       buttonRestart.setVisible(false);


       btnStartMare.setOnAction(eveniment -> {
           zonaDeJoc.getChildren().clear();
           zonaDeJoc.getChildren().add(meniuDificultate);
       });

       btnAlegeSimbol.setOnAction(eveniment -> {
           zonaDeJoc.getChildren().clear();
           zonaDeJoc.getChildren().add(meniuSimbol);
       });

        btnAlegeX.setOnAction(e -> {
            simbolOm = 'X';
            simbolAI = 'O';
            simbolAles = true;
            btnAlegeSimbol.setText("Ai ales X (Modifică)");
            zonaDeJoc.getChildren().clear();
            zonaDeJoc.getChildren().add(meniuDificultate);
        });

        btnAlege0.setOnAction(e -> {
            simbolOm = 'O';
            simbolAI = 'X';
            simbolAles = true;
            btnAlegeSimbol.setText("Ai ales 0(Modifică)");
            zonaDeJoc.getChildren().clear();
            zonaDeJoc.getChildren().add(meniuDificultate);
        });


        btnUsor.setOnAction(eveniment -> {
            if (!simbolAles) { arataMesajAvertizare("Te rugăm să alegi un simbol (X sau 0) întâi!"); return; }
            incepeMeciul(1, zonaDeJoc, grila);
        });
        btnMediu.setOnAction(eveniment -> {
            if (!simbolAles) { arataMesajAvertizare("Te rugăm să alegi un simbol (X sau 0) întâi!"); return; }
            incepeMeciul(2, zonaDeJoc, grila);
        });
        btnGreu.setOnAction(eveniment -> {
            if (!simbolAles) { arataMesajAvertizare("Te rugăm să alegi un simbol (X sau 0) întâi!"); return; }
            incepeMeciul(3, zonaDeJoc, grila);
        });


        btnUsor.setOnAction(eveniment -> {incepeMeciul(1, zonaDeJoc, grila);});
        btnMediu.setOnAction(eveniment -> {incepeMeciul(2, zonaDeJoc, grila);});
        btnGreu.setOnAction(eveniment -> {incepeMeciul(3, zonaDeJoc, grila);});


        buttonRestart.setOnAction(eveniment -> {

           zonaDeJoc.getChildren().clear();
           zonaDeJoc.getChildren().add(meniuDificultate);
           buttonRestart.setVisible(false);
       });



       cutiePrincipala.getChildren().addAll(zonaDeJoc, buttonRestart );
       Scene scena = new Scene(cutiePrincipala, 350, 450);
       fereastraPrincipala.setScene(scena);
       fereastraPrincipala.show();
    }




    private void mutareCalculator(){
        if(!jocInceput) return;

        int[] mutareAI;
        if(dificultate==1){
            mutareAI=gasesteMutareAleatoare();
        }else if(dificultate==2){
            if(Math.random()<0.5) mutareAI=tablaJoc.gasesteMutareaPerfecta(simbolAI,  simbolOm);
            else mutareAI=gasesteMutareAleatoare();
        }else
        {
            mutareAI=tablaJoc.gasesteMutareaPerfecta(simbolAI, simbolOm);
        }
        int randAI=mutareAI[0]-1;
        int  coloanaAI=mutareAI[1]-1;
        tablaJoc.locMutare(mutareAI[0], mutareAI[1], simbolAI);
        butoane[randAI][coloanaAI].setText(String.valueOf(simbolAI));
        verificaFinalJoc();


    }
    private void incepeMeciul(int nivelAles, StackPane zonaDeJoc, GridPane grila) {
        dificultate = nivelAles;
        jocInceput = true;
        buttonRestart.setVisible(true);
        buttonRestart.setDisable(false);

        tablaJoc = new tabla();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                butoane[i][j].setText("");
            }
        }
        zonaDeJoc.getChildren().clear();
        zonaDeJoc.getChildren().add(grila);

        if(simbolOm=='O'){
            mutareCalculator();
        }
    }

    private void arataMesajAvertizare(String mesaj) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Atenție!");
        alerta.setHeaderText(null);
        alerta.setContentText(mesaj);
        alerta.showAndWait();
    }

    private int[] gasesteMutareAleatoare(){
        java.util.List<int[]> casuteLibere= new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if(butoane[i][j].getText().isEmpty()){
                    casuteLibere.add(new int[]{i+1,j+1});
                }
            }
        }
        java.util.Collections.shuffle(casuteLibere);
        return casuteLibere.get(0);
    }


    public static void main(String[] args) {
        launch(args);


    }
    boolean verificaFinalJoc(){
         if (tablaJoc.VerificaCastigator(simbolOm)){
             DeseneazaLiniaCastigatoare();
            arataMesaj("Felicitări! Ai câștigat!");
            jocInceput=false;
            buttonRestart.setText("Restart");
            return true;
        }
        if (tablaJoc.VerificaCastigator(simbolAI)){
            DeseneazaLiniaCastigatoare();
            arataMesaj("Calculatorul a castigat");
            jocInceput=false;
            buttonRestart.setText("Restart");
            return true;
        }
        if (tablaJoc.TablaEstePlina()){
            arataMesaj("Remiza! Bine jucat!");
            jocInceput=false;
            buttonRestart.setText("Restart");
            return true;
        }
        return  false;

    }
    private void arataMesaj(String mesaj){
        new Thread(() -> {
            try {Thread.sleep(150);
            }catch (InterruptedException e){}

            javafx.application.Platform.runLater(() -> {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Rezultat");
                alerta.setHeaderText(null);
                alerta.setContentText(mesaj);
                alerta.showAndWait();
            });

        }).start();
    }

    private void DeseneazaLiniaCastigatoare(){
        Line linie=new Line();
        linie.setStroke(Color.RED);
        linie.setStrokeWidth(8);
        linie.setOpacity(0.8);

        for (int i = 0; i < 3; i++){
                if(esteLinieCastigatoare(butoane[i][0], butoane[i][1], butoane[i][2])){
                    linie.setStartX(0); linie.setStartY(0);
                    linie.setEndX(300); linie.setEndY(0);
                    linie.setTranslateY((i-1)*100);
                    zonaDeJoc.getChildren().add(linie);
                    return;

                }
            }


        for(int j=0;j<3;j++){
            if(esteLinieCastigatoare(butoane[0][j],  butoane[1][j], butoane[2][j])){
                linie.setStartX(0); linie.setStartY(0);
                linie.setEndX(0); linie.setEndY(300);
                linie.setTranslateX((j-1)*100);
                zonaDeJoc.getChildren().add(linie);
                return;
            }
        }
        if(esteLinieCastigatoare(butoane[0][0], butoane[1][1], butoane[2][2])){
            linie.setStartX(0); linie.setStartY(0);
            linie.setEndX(300); linie.setEndY(300);
            zonaDeJoc.getChildren().add(linie);
            return;
        }
        if(esteLinieCastigatoare(butoane[0][2], butoane[1][1], butoane[2][0])){
            linie.setStartX(300); linie.setStartY(0);
            linie.setEndX(0); linie.setEndY(300);
            zonaDeJoc.getChildren().add(linie);
            return;
        }
    }

    private  boolean esteLinieCastigatoare(Button b1, Button b2, Button b3){
        String text =b1.getText();
        return !text.isEmpty() && text.equals(b2.getText()) && text.equals(b3.getText());
    }




}
