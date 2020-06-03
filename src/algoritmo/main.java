/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author brian
 */
public class main{

    public static void main(String[] args) {
        View v =  new View();        
        AlgoritmoN algoritmo = new AlgoritmoN(v);
        //AlgoritmoN algoritmoN = new AlgoritmoN(v);
        
        v.setVisible(true);
        algoritmo.Iniciar();
    }
}