/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shoot;

/**
 *
 * @author nik-s
 */
public class Message {
    public enum MessageAction {USERNAME, READY, STOP, START, ARROW, CIRCLE, USER, WIN}
    
    public MessageAction act;
    public Data data = null;
    
    public Message(MessageAction act, Data data) {
        this.act = act;
        this.data = data;
    }
    public Message(MessageAction act) {
        this.act = act;
    }
}

