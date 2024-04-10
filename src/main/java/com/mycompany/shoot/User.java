/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.shoot;

/**
 *
 * @author nik-s
 */
public class User {
    String name;
    int count_shoot;
    int score;
    int id;
    
    public User() {}

    public User(String name) {
        this.name = name;
    }
    
    public User(String name, int id) {
        this.name = name;
        this.id =id;
    }

    User(int id, int countShoot, int score, String name) {
        this.count_shoot=countShoot;
        this.id=id;
        this.name=name;
        this.score=score;
    }
    
    
}
