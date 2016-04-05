/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloc;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/**
 *
 * @author ssnakesito
 */
public class Bloc extends JFrame implements ActionListener{
    
    JMenuBar barra= new JMenuBar();
    JMenu archivo= new JMenu("Archivo");//Mensaje que se mostrara
    JMenuItem abrir= new JMenuItem("Abrir");
    JMenuItem nuevo= new JMenuItem("Nuevo");
    JMenuItem guardar= new JMenuItem("Guardar");
    JTextArea areaTexto= new JTextArea();
    
    //Constructor de la ventana
    public Bloc(){
        setTitle("BlocPTC"); //Nombre ventana
        setLayout(new BorderLayout()); //Layout manager
        setSize(640,480);   //Tamaño de la ventana
        setLocation(0,0);
        
        nuevo.addActionListener(this);
        abrir.addActionListener(this);
        guardar.addActionListener(this);
        
        archivo.add(nuevo); //Archivo se añade a menu
        archivo.add(abrir);
        archivo.add(guardar);
        
        barra.add(archivo); //Crea barra de menu, pero aun no se agrega
        
        setJMenuBar(barra); //Hace menu y lo acomoda
        add(new JScrollPane(areaTexto),BorderLayout.CENTER);
        
        setVisible(true); //No aparece la ventana
        
        addWindowListener(new WindowAdapter() {
            //@Override
            public void WindowsClosing(WindowEvent e) {
                int opc = JOptionPane.showConfirmDialog(areaTexto, "Quieres guardar?", "Guardar y salir", JOptionPane.YES_NO_CANCEL_OPTION);
                switch (opc) {
                    case 0:
                        try {
                            JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
                            fc.showSaveDialog(fc);
                            File guarda = fc.getSelectedFile();
                            if (guarda != null) {
                                FileWriter guardaA = new FileWriter(guarda);
                                guardaA.write(areaTexto.getText());
                                guardaA.close();
                            }
                            setVisible(true);
                            
                        } catch (IOException ee) {
                            
                        }
                        break;
                    
                    case 1:
                        System.exit(0);
                        break;
                    
                    default:
                        break;
                }
                setVisible(true);
            }
            
        });
    }
    
    
    public static void main(String[] args){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Bloc().setVisible(true);
            }
        });
    }
    
    //Le da las acciones a tomar a los eventos
    //Identficamos los eventos que se detonan y creamos 
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == nuevo){
            Bloc bloc2= new Bloc();
        }
        if(e.getSource() == abrir){
            JFileChooser chooser= new JFileChooser(System.getProperty("user.dir"));
            chooser.setApproveButtonText("Seleccionar");
            chooser.showOpenDialog(null); 
            //Si no esta en nulo nos va indicar el evento repetido
            File archivo= chooser.getSelectedFile();
            
            try{
                //Recibe un flujo como parametro
                BufferedReader reader= new BufferedReader(new FileReader(archivo));
                String linea= reader.readLine();
                
                while(linea!=null){
                    areaTexto.append(linea+"\n");
                    linea= reader.readLine();
                }
            }catch(Exception ex){
                
            }
        }
        
        if(e.getSource() == guardar){
            try{
                JFileChooser fc= new JFileChooser(System.getProperty("user.dir"));
                fc.showSaveDialog(this);
                
                File guarda= fc.getSelectedFile();
                //Si tiene un nombre entonces entra para saber a donde o como guardara
                if(guarda != null){
                    FileWriter guardaA= new FileWriter(guarda);
                    guardaA.write(areaTexto.getText());
                    guardaA.close();
                }
                    
            }catch(Exception ex){
                
            }
        }
    }
}
