/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sharpshootergame;

/*
 *
 * @author nik-s
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SharpShooterGame {
    private JFrame frame;

    // Добавляем объекты стрелы и мишеней
    //private Arrow arrow;
    //private Target target1, target2;
  
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SharpShooterGame window = new SharpShooterGame();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public SharpShooterGame() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Установка в максимальный размер
        frame.setResizable(false); // Запрет на изменение размера
       
    }
}