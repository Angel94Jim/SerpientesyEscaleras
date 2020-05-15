package Clases;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Boton extends JFrame implements ActionListener {

    JButton boton1;
    public int numero=0;

    public Boton(){


        setLayout(null);
        boton1=new JButton("Lanzar Dado: ");
        boton1.setBounds(66,20,150,50);
        add(boton1);
        boton1.addActionListener(this);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==boton1) {
           numero = (int)(Math.random()*6+1);
           System.out.println("Tu numero es: "+numero);

        }

    }
}
