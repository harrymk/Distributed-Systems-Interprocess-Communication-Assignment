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

public class SocketServer extends Application{

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
        Label labelServer = new Label("Server");
        labelServer.setStyle("-fx-background-color:yellow;");
        root.add(labelServer,4,0);

        Label labelClientConnectionNotf = new Label("Awaiting Client to connect...");
        labelClientConnectionNotf.setStyle("-fx-background-color:brown;");
        root.add(labelClientConnectionNotf,4,1);

        Label labelDataFromClient = new Label("Awaiting Data From Client...");
        labelDataFromClient.setStyle("-fx-background-color:brown;");
        root.add(labelDataFromClient,4,2);

        Scene scene = new Scene(root, 900, 900);
        primaryStage.setTitle("Sockets Server");
        primaryStage.setScene(scene);
        primaryStage.show();

        // sockets stuff
        ServerSocket ss=new ServerSocket(3333);  
        Socket s=ss.accept();
        DataInputStream din=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        // fn 1 get notification from client showing connection has taken place
        String str=din.readUTF();
        labelClientConnectionNotf.setText(str);

        // fn2 send client notification requesting for input
        String str3 = "Please send the information";
        dout.writeUTF(str3);
        dout.flush();

        // close all
        din.close();  
        dout.close();
        s.close();  
        ss.close();

        // sockets stuff 2
        ServerSocket ss2=new ServerSocket(3333);  
        Socket s2=ss2.accept();
        DataInputStream din2=new DataInputStream(s2.getInputStream());
        DataOutputStream dout2=new DataOutputStream(s2.getOutputStream());
        BufferedReader br2=new BufferedReader(new InputStreamReader(System.in));

        // fn 3 get information from client that was requested
        String str42=din2.readUTF();
        labelDataFromClient.setText(str42);

        // fn4 send client thank you notification
        String str52 = "The information is well received. Thank you Mr. Client.";
        dout2.writeUTF(str52);
        dout2.flush();

        din2.close();  
        dout2.close();
        s2.close();  
        ss2.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
