/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shoot;

import com.google.gson.Gson;
import com.mycompany.shoot.Message.MessageAction;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author nik-s
 */

public class Server {
    
    int port = 3124;
    InetAddress ip = null;
    ServerSocket ss;
    
    ArrayList<ConnectedСlient> AllClients = new ArrayList<>();
    ArrayList<String> AllUsernames = new ArrayList<>();
    boolean GameStart = false;
    boolean flagA2=false;
    boolean[] flagA = {false,false,false,false};
    int[]Userscore = {0,0,0,0};
    int[]Usershoot = {0,0,0,0};
    Data c = new Data();
    
    int number = 0;
    
    
    void CircleMove() {
  
        if(Math.abs(c.crd.k1+c.crd.q1)>25){c.crd.q1=-c.crd.q1;}
        if(Math.abs(c.crd.k2+c.crd.q2)>25){c.crd.q2=-c.crd.q2;}
        
        c.crd.k1=c.crd.k1+c.crd.q1; c.crd.k2=c.crd.k2+c.crd.q2;
        Data d = c;
        sendAll(new Message(MessageAction.CIRCLE, c));
    }
    void ArrowMove() throws InterruptedException{ 
        
        for(int i=0;i<4;i++){
            if(flagA[i]){
                if(c.ard.k[i]>15){
                    c.ard.k[i]=-1; flagA[i]=false; 
                    c.usd.id=i;
                    Usershoot[i]++;
                    c.usd.count_shoot=Usershoot[i];
                    c.usd.score=Userscore[i];
                    sendAll(new Message(MessageAction.USER, c));
                    Thread.sleep(120);
                }
                if((c.ard.k[i]==11)&&(Math.abs(c.crd.k1)<3)){
                    c.ard.k[i]=-1; flagA[i]=false; 
                    c.usd.id=i;
                    Usershoot[i]++;
                    Userscore[i]++;
                    c.usd.count_shoot=Usershoot[i];
                    c.usd.score=Userscore[i];
                    if(Userscore[i]>=10){
                        sendAll(new Message(MessageAction.WIN, c));
                        GameStart=false; int a =0;
                        for (ConnectedСlient client : AllClients) {
                            client.ready = false; 
                            Userscore[a]=0;
                            Usershoot[a]=0;
                            a++;
                        }
                    }
                    else{sendAll(new Message(MessageAction.USER, c));}
                    Thread.sleep(120);
                }
                if((c.ard.k[i]==13)&&(Math.abs(c.crd.k2)<4)){
                    c.ard.k[i]=-1; flagA[i]=false; 
                    c.usd.id=i;
                    Usershoot[i]++;
                    Userscore[i]=Userscore[i]+2;
                    c.usd.count_shoot=Usershoot[i];
                    c.usd.score=Userscore[i];
                    if(Userscore[i]>=10){
                        sendAll(new Message(MessageAction.WIN, c));
                        GameStart=false; int a =0;
                        for (ConnectedСlient client : AllClients) {
                            client.ready = false; 
                            Userscore[a]=0;
                            Usershoot[a]=0;
                            a++;
                        }
                    }
                    else{sendAll(new Message(MessageAction.USER, c));}
                    Thread.sleep(120);
                }
                c.ard.k[i]++;
                c.ard.id=i;
                sendAll(new Message(MessageAction.ARROW, c));
            }
        }
        
        
        
    }
    void SetUsernames() {
        for (ConnectedСlient client: AllClients) {
            if (client.filled == false){
                client.filled = true;
                Data d = new Data();
                d.usd = new User(client.name, client.id);
                sendAll(new Message(MessageAction.USERNAME, d));
            }
        }
    }
    
    
    Thread Game = new Thread(()->{
        boolean ready;
        try {
            while(true) {
                if (GameStart) {                  //Если игра запущена, двигаем стрелки и мишени
                    CircleMove();
                    if(flagA2) { ArrowMove();}
                } else {                          //Иначе ждем, пока все игроки будут готовы 
                    if(AllClients.isEmpty()){ GameStart = false;}
                    else { 
                        GameStart = true;
                        for (ConnectedСlient client: AllClients) {
                            if (client.ready == false) {GameStart = false; break;}}
                    }
                }
                SetUsernames();
                Thread.sleep(120);}
        } catch (InterruptedException ex) { System.out.append("Error");}

        });
 
    public void ServerStart() throws UnknownHostException, IOException {
        
        Game.start();
        
        try {
            ip = InetAddress.getLocalHost();
            ss = new ServerSocket(port, 0, ip);
            System.out.append("Server start.");
            while(true) {
                Socket cs;
                cs = ss.accept();
                System.out.append("Client connect.");  
                
                ConnectedСlient cx = new ConnectedСlient(cs, this, number);
                number += 1;
                AllClients.add(cx);
                new Thread(cx).start(); //ожидаем сообщение от клиента и обрабатываем его
            }
            
        } 
        catch (IOException ex) {System.out.append("Error");}
        


        
    }
    
    void sendAll(Message msg) {
        try{
            Gson gson = new Gson();
            String s = gson. toJson(msg);
            System.out.println("Send all:" + s);
            for (ConnectedСlient client: AllClients) {
                client.dos.writeUTF(s);
            }
        } catch (IOException ex) {
            {System.out.append("Error");}
        }
    }


    
    /**
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        try {
            new Server().ServerStart();
        } catch (UnknownHostException ex){System.out.append("Error");}
    }
}
