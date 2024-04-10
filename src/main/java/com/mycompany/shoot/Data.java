/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shoot;

/**
 *
 * @author nik-s
 */
public class Data {
   
    public Arrow ard = new Arrow(-1);
    public Circle crd = new Circle(0,0);
    public User usd = new User("");        
    
    public Data(){}
    public Data(int i, int[] c, int k11, int k22 , int q11, int q22, String s){
        
        ard.id=i;
        ard.k=c;
        
        crd.k1=k11;
        crd.k2=k22;
        crd.q1=q11;
        crd.q2=q22;
        
        usd.name=s;
        
    }
    
}
