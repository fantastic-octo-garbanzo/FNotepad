import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class FNotepad implements ItemListener{  
    Checkbox en, de;  
    Label label;  
    FNotepad(){    
        Frame f = new Frame("FNotepad - Language");    
        label = new Label();            
        label.setAlignment(Label.CENTER);    
        label.setSize(600,400);    
        en = new Checkbox("English");    
        en.setBounds(100,100, 250,50);    
        de = new Checkbox("Deutsch");    
        de.setBounds(100,150, 250,50);    
        f.add(en); f.add(de); f.add(label);    
        en.addItemListener(this);    
        de.addItemListener(this);    
        f.setSize(400,400);    
        f.setLayout(null);    
        f.setVisible(true);    
    }    
     
    public void itemStateChanged(ItemEvent e) {      
        if(e.getSource()==en) {
            // label.setText("Language English: "+ (e.getStateChange()==1?"Welcome to FNotepad!":" ")); 
            new FNotepadEN();  
        }
        if(e.getSource()==de) {
        	// label.setText("Sprache Deutsch: "+ (e.getStateChange()==1?"Willkommen bei FNotepad!":" "));
        	new FNotepadDE();
        }    
    }  
     
	public static void main(String args[]) {    
    	new FNotepad();    
	}    
}
