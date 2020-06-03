/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author brian
 */
public class AlgoritmoN implements ActionListener{

    public View view;
    public AlgoritmoN(View v) {
        this.view=v;
        this.view.btnCalcular.addActionListener(this);
        this.view.btnLimpiar.addActionListener(this);
        this.view.menuArLim.addActionListener(this);
        this.view.menuAcercaDe.addActionListener(this);
        this.view.menuAyuda.addActionListener(this);        
        this.view.menuTable.addActionListener(this);
    }
    public void Iniciar(){
        view.setTitle("Algoritmo de Booth");
        view.setLocationRelativeTo(null);
        view.lblError1.setVisible(false);
        view.lblError2.setVisible(false);
        view.lblErrorNumeros.setVisible(false); 
        view.txtResultados.setEditable(false);
        //view.btnCalcular.setEnabled(false);
    }
    
    public void Calculos(){        
        
        int bits = Integer.parseInt(view.cboxBits.getSelectedItem().toString().trim());
        int multiplicando = Integer.parseInt(view.txtMultiplicando.getText().trim());
        int multiplicador = Integer.parseInt(view.txtMultiplicador.getText().trim());
        view.txtResp.setText(String.valueOf(multiplicador*multiplicando));
        
        String AA = "", SS = "", PP = "";
        String sA, sB;
        
        if(multiplicando<0){
            multiplicando=SiNegativo(multiplicando);
            String Ab = decimalAbinario(multiplicando, bits);            
            AA = CA2(Ab);
            SS = Ab;
            sA = "-";
        }else{
            String Ab = decimalAbinario(multiplicando, bits);
             AA = Ab;
            SS = CA2(Ab);
            sA = "+";
        }
        
        if(multiplicador<0){
            multiplicador=SiNegativo(multiplicador);
            String Bb = decimalAbinario(multiplicador, bits);
            for (int i = 0; i < bits; i++) PP += "0";
            PP = PP + CA2(Bb) + "0";
            sB = "-";          
        }else{
             String Bb = decimalAbinario(multiplicador, bits);
            for (int i = 0; i < bits; i++) PP += "0";
            PP = PP + Bb + "0";
            sB = "+";
        }
        
        for (int i = 0; i <= bits; i++) {
            AA += "0";
            SS += "0";
        }

        //System.out.println("\nBinario de " + multiplicando + " = " + decimalAbinario(multiplicando, bits));  
        //System.out.println("Binario de " + multiplicador + " = " + decimalAbinario(multiplicador, bits) + "\n");
        // System.out.println("Complemento A2 de " + multiplicando + " = " + CA2(decimalAbinario(multiplicando, bits)));
        //System.out.println("Complemento A2 de " + multiplicador + " = " + CA2(decimalAbinario(multiplicador, bits)) + "\n"); 
        view.txtResultados.append("\nBinario de " + multiplicando + " = " + decimalAbinario(multiplicando, bits)+"\n");        
        view.txtResultados.append("Binario de " + multiplicador + " = " + decimalAbinario(multiplicador, bits) + "\n\n");       
        
        view.txtResultados.append("Complemento A2 de " + multiplicando + " = " + CA2(decimalAbinario(multiplicando, bits))+"\n");
        view.txtResultados.append("Complemento A2 de " + multiplicador + " = " + CA2(decimalAbinario(multiplicador, bits)) + "\n\n");
        
        show("    A      ", AA);
        show("    S      ", SS);
        show("    P      ", PP);
     
        for (int i = 0; i < bits; i++) {
            //System.out.println("\nITERACION #" + (i + 1));
            view.txtResultados.append("\n\n ITERACION #" + (i + 1)+"\n");
            show("    A      ", AA);
            show("    S      ", SS);                       
            if(PP.charAt((bits*2+1)-2)=='0' && PP.charAt((bits*2+1)-1)=='1'){ // P = P + A
                show("    P      ", PP);               
                PP = suma(PP, AA, (bits * 2) + 1);
                show("P = P + A  ", PP);               
            } else if(PP.charAt((bits*2+1)-2)=='1' && PP.charAt((bits*2+1)-1)=='0'){ // P = P + S
                show("    P      ", PP);                
                PP = suma(PP, SS, (bits * 2) + 1);
                show("P = P + S  ", PP);                
            } else show("    P      ", PP);
            PP = desplazar(PP);
            show("    P" + (i + 1) + "     ", PP);           
        }

        int ii = 0;        
        if(sA == sB) ii = 0; 
        else ii=1;
        
        String resultado = "";
       // System.out.print(" RESULTADO     ");
        view.txtResultados.append("\nRESULTADO:    ");
        
        for (int i = 0; i < PP.length()-1; i++) {
            resultado += PP.charAt(i);
            System.out.print(resultado.charAt(i) + "  ");
            view.txtResultados.append(resultado.charAt(i) + "  ");
            if ((i == ((PP.length()/2)-1))){ //System.out.print("  ");           
                view.txtResultados.append(" ");
            }
        }
        if(ii==1){
           // System.out.print("  = -" + (multiplicador*multiplicando) + " = CA2("+ (multiplicador*multiplicando) + ")\n");
            view.txtResultados.append("  = -" + (multiplicador*multiplicando) + " = CA2("+ (multiplicador*multiplicando) + ")\n");
        }else{
            //System.out.print("  = " + binarioAdecimal(resultado) + " = CA2("+ binarioAdecimal(resultado) + ")");
            view.txtResultados.append("  = " + binarioAdecimal(resultado) + " = CA2("+ binarioAdecimal(resultado) + ")\n");
        }
        
    }
    
    public int SiNegativo(int n){
        int pos=0;
        if(n<0){
            pos= -1*n;
        }
        return pos;
    }
    public static String desplazar(String PP){
        String nuevo = "" + PP.charAt(0);
        for (int i = 0; i < PP.length()-1; i++) nuevo += PP.charAt(i);
        return nuevo;
    }
    
    public void show(String operacion, String valor){
        //System.out.print(operacion + "  ");
        view.txtResultados.append(operacion+"  ");
        for (int i = 0; i < valor.length(); i++){
            //System.out.print("  " + valor.charAt(i));
            view.txtResultados.append("  " + valor.charAt(i));
            if ((i == ((valor.length()/2)-1)) || (i == (valor.length()-2))) {System.out.print("  "); view.txtResultados.append("  ");}
        }
        //System.out.print("\n");
        view.txtResultados.append("\n");
    }
    
    public static String decimalAbinario(int numero, int tam) {
        String binario = "";
        if (numero > 0) {
            while (numero > 0) {
                if (numero % 2 == 0) {
                    binario = "0" + binario;
                } else {
                    binario = "1" + binario;
                }
                numero = (int) numero / 2;
            }
        } else if (numero == 0) {
            binario = "0";
        } else {
            binario = "No se pudo convertir el numero. Ingrese solo números positivos";
        }
        int bb = binario.length();
        if (bb != tam) {
            for (int i = 0; i < (tam - bb); i++) {
                binario = "0" + binario;
            }
        }
        return binario;
    }

    public static Long binarioAdecimal(String numero){
        return Long.parseLong(numero, 2);
    }
    
    public static String CA1(String binario) {
        String ca1 = "";
        for (int i = 0; i < binario.length(); i++) {
            if (binario.charAt(i) == '1') ca1 += "0";
            else ca1 += "1";
        }
        return ca1;
    }

    public static String CA2(String binario) {
        binario = CA1(binario);
        String s = decimalAbinario(1, binario.length());
        return suma(binario,s,binario.length());
    }
    
    public static String suma(String a, String b, int tam) {///suma dos binarios
        long a1 = Long.parseLong(a, 2);
        long b1 = Long.parseLong(b, 2);
        String resultado = Long.toString(a1 + b1, 2);
        String l = "";
        if (resultado.length() > tam) {
            LinkedList<Integer> A = doList(resultado);
            A.removeFirst();
            resultado = doString(A);
        }
        if (resultado.length() != tam) {
            for (int i = 0; i < (tam) - resultado.length(); i++) l = l + "0";
        }
        return l + resultado;
    }
    
    public static String doString(LinkedList<Integer> a) {//saca del array los digitos y une en un string
        String num = "";
        for (int i = 0; i < a.size(); i++) {
            num = num + a.get(i);
        }
        return num;
    }

    public static LinkedList<Integer> doList(String num) {//separa el string en digitos para poder meterlo al Array
        LinkedList<Integer> arreglo = new LinkedList<>();
        String[] dig = num.split("");
        for (int i = 0; i < dig.length; i++) {
            arreglo.add(Integer.parseInt(dig[i]));
        }
        return arreglo;
    }

    public void PressCalcular(){
        Calculos();                        
        view.lblErrorNumeros.setVisible(false);
        view.txtResultados.append("\n************************************************  FIN  **********************************************************\n\n\n");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.btnCalcular){               
            if(view.txtMultiplicador.getText().equals("") || view.txtMultiplicando.getText().equals("")){               
               view.lblError1.setVisible(true);
               view.lblError2.setVisible(true);                              
            }else{                
                view.lblError1.setVisible(false);
                view.lblError2.setVisible(false);                                                                            
                int op = Integer.parseInt(view.cboxBits.getSelectedItem().toString());
                switch(op){
                    case 4:
                        if(Integer.parseInt(view.txtMultiplicando.getText().trim()) < -8 || Integer.parseInt(view.txtMultiplicando.getText().trim()) > 7 || Integer.parseInt(view.txtMultiplicador.getText().trim())< -8 || Integer.parseInt(view.txtMultiplicador.getText().trim()) > 7){
                            JOptionPane.showMessageDialog(view, "ERROR: Overflow\n Rango para 4 bits: [-8, 7].", "Mensje de Error! 4 Bits.", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            PressCalcular();
                        }
                        break;
                    case 8:
                        if(Integer.parseInt(view.txtMultiplicando.getText().trim()) < -128 || Integer.parseInt(view.txtMultiplicando.getText().trim()) > 127 || Integer.parseInt(view.txtMultiplicador.getText().trim())< -128 || Integer.parseInt(view.txtMultiplicador.getText().trim()) > 127){
                            JOptionPane.showMessageDialog(view, "ERROR: Overflow\n Rango para 8 bits: [-128, 127]." , "Mensje de Error! 8 Bits.", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            PressCalcular();
                        }
                        break;
                    case 16:
                        if(Integer.parseInt(view.txtMultiplicando.getText().trim()) < -32768 || Integer.parseInt(view.txtMultiplicando.getText().trim()) > 32767 || Integer.parseInt(view.txtMultiplicador.getText().trim())< -32768 || Integer.parseInt(view.txtMultiplicador.getText().trim()) > 32767){
                            JOptionPane.showMessageDialog(view, "ERROR: Overflow\n Rango para 16 bits: [-32768, 32767]." , "Mensje de Error! 16 bits.", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            PressCalcular();
                        }
                        break;                            
                }                                                        
            }            
        }
        
        if(e.getSource()== view.btnLimpiar){
            view.txtMultiplicando.setText(" ");
            view.txtMultiplicador.setText(" ");
            view.txtResp.setText("0");
            view.txtResultados.setText("");         
        }   
        if(e.getSource() == view.menuArLim){
            view.txtMultiplicando.setText(" ");
            view.txtMultiplicador.setText(" ");
            view.txtResp.setText("0");
            view.txtResultados.setText(""); 
        }
        if(e.getSource() ==  view.menuAcercaDe){
            JOptionPane.showMessageDialog(null, "El presente software ha sido diseño y codificado por:\n  -Bria Mora\n  -Juan Bustamante\n\nPara la asignatura de Arquitecturas del Computador\nUniversidad de Cuenca");
        }
        if(e.getSource()== view.menuAyuda) {
            ayuda ayuda = new ayuda();
            ayuda.setVisible(true);
        }
        if(e.getSource()==view.menuTable){
            tabla table = new tabla();
            table.setVisible(true);
        }
    }
}
