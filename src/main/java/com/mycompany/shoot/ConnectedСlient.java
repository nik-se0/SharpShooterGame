/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shoot;

import com.google.gson.Gson;
import com.mycompany.shoot.Message.MessageAction;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author nik-s
 */

public class ConnectedСlient implements Runnable {
    
    Server MyServer;
    String name = "";
    int id;
    boolean filled = true;
    boolean ready = false;
    
    Socket cs;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;
    Random rand = new Random();
    
    
    public ConnectedСlient(Socket cs, Server MyServer, int number) {
        this.cs = cs;
        this.MyServer = MyServer;
        this.id = number;
        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException ex) { System.out.append("Error");}
    }

    public void ChangeFilled() {
        this.filled = false;
    }
    
    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);
            
            while (true) {
                String s = dis.readUTF();
                System.out.println("Read "+ id +": " + s);
                
                Gson gson = new Gson();
                Message MyServerg = gson.fromJson(s, Message.class);
                switch(MyServerg.act) {
                    case USERNAME:              //Ввод никнейма
                        name = ((User)MyServerg.data.usd).name;
                        while(MyServer.AllUsernames.contains(name)) {
                            name += rand.nextInt(10);
                        }
                        MyServer.AllUsernames.add(name);
                        ChangeFilled();
                        break;                  
                    case READY:                 //Подтверждение готовности
                        ready = true;
                        ChangeFilled();
                        break;
                    case STOP:                  //Приостановка игры
                        ready = false;
                        MyServer.GameStart=false;
                        break;
                    case START:                 //Запуск стрелы
                        MyServer.flagA2=true;
                        MyServer.flagA[id]=true;
                        MyServer.c.ard.id=id;
                        break;
                }  
            }
        } catch (IOException ex) { System.out.append("Error");}
    }

    
}

