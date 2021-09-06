import java.net.*; 
import java.io.*;
// ui
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class SocketClient extends Application{
    // Label labelInfoReceivedConfirmation; 
    // TextField tfNumber; 
    // TextField tfName;
    // TextField tfFCD; 
    // TextField tfThanks;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //SCENE1
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(11,12,23,14));
        root.setHgap(20);
        root.setVgap(20);
        root.setBorder(new Border(new BorderStroke(Color.BROWN,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        root.setStyle("-fx-background-color:azure;");

        //Labels and TextFields below for title and whatnot..!!
        Label labelServer = new Label("Client");
        labelServer.setStyle("-fx-background-color:yellow;");
        root.add(labelServer,4,0);

        Label labelServerRequestPrompt = new Label("Awaiting Server to connect...");
        labelServerRequestPrompt.setStyle("-fx-background-color:brown;");
        root.add(labelServerRequestPrompt,4,1);

        Label labelAddDetails = new Label ("Add Details");
        labelAddDetails.setStyle("-fx-background-color:yellow;");
        root.add(labelAddDetails,4,2);

        Label labelNumber = new Label ("Number");
        labelNumber.setStyle("-fx-background-color:yellow;");
        root.add(labelNumber,4,3);

        TextField tfNumber = new TextField();
        root.add(tfNumber,5,3);

        Label labelName = new Label ("Name");
        labelName.setStyle("-fx-background-color:yellow;");
        root.add(labelName,4,4);

        TextField tfName = new TextField();
        root.add(tfName,5,4);

        Label labelFCD = new Label ("Faculty, Course, Degree");
        labelFCD.setStyle("-fx-background-color:yellow;");
        root.add(labelFCD,4,5);
        
        TextField tfFCD = new TextField();
        root.add(tfFCD,5,5);

        Label labelThanks = new Label ("Thank you Message");
        labelThanks.setStyle("-fx-background-color:yellow;");
        root.add(labelThanks,4,6);

        TextField tfThanks = new TextField();
        root.add(tfThanks,5,6);

        // this label is below the button below
        Label labelInfoReceivedConfirmation = new Label ("Awaiting Server Confirmation of information receipt");
        labelInfoReceivedConfirmation.setStyle("-fx-background-color:brown;");
        root.add(labelInfoReceivedConfirmation,4,8);

        //Button for acting on the entering of institution data
        Button done1 = new Button("Done");
        root.add(done1,4,7);
        done1.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                try{
                    // sockets stuff 2
                    Socket s2=new Socket("localhost",3333);  
                    DataInputStream din2=new DataInputStream(s2.getInputStream());  
                    DataOutputStream dout2=new DataOutputStream(s2.getOutputStream());  
                    BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));

                    // fn3 relaying data to server by sockets
                    String str22 = tfNumber.getText() + "," + tfName.getText() + "," + tfFCD.getText() + "," + tfThanks.getText();
                    dout2.writeUTF(str22);
                    dout2.flush();

                    // fn4 get thank you message from server for sending the info
                    String str42=din2.readUTF();
                    labelInfoReceivedConfirmation.setText(str42);

                    din2.close();
                    dout2.close();  
                    s2.close();
                }
                catch(Exception err){
                    System.out.println(err.getMessage());
                }
            }
        });

        Scene scene = new Scene(root, 900, 900);
        primaryStage.setTitle("Sockets Client");
        primaryStage.setScene(scene);
        primaryStage.show();

         // sockets stuff
        Socket s=new Socket("localhost",3333);  
        DataInputStream din=new DataInputStream(s.getInputStream());  
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        // fn1 send msg to server showing connection accepted
        String str="Client Connected";
        dout.writeUTF(str);
        dout.flush();

        // fn2 get request message from server requesting client to send info
        String str3=din.readUTF();
        labelServerRequestPrompt.setText(str3);

        // close all
        din.close();
        dout.close();  
        s.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
