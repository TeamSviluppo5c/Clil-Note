/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverclil.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author SSLAVIERO
 */
public class ServerClilXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        NoteBook note=new NoteBook();
        NoteBook notedescr=new NoteBook();
        
       try {        
       JAXBContext jc = JAXBContext.newInstance(Note.class);
       Unmarshaller u = jc.createUnmarshaller();

        try {
            ServerSocket latoServer = new ServerSocket(9090);
            while (true) {
                
                try (Socket latoclient = latoServer.accept()) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(latoclient.getInputStream()));
                    String risposta = input.readLine();
                    System.out.println(risposta);
                    String[] risposte=risposta.split("#");
                    
                    if(risposte[1].compareTo("1")==0)
                    {
                        StringReader reader = new StringReader(risposte[0]);
                        Note o = (Note) u.unmarshal(reader);
                        note.AddNote(o);
                    }
                    
                    if(risposte[1].compareTo("2")==0)
                    {
                        for(Note nota:note.getNotes())
                        {
                            if(nota.getDescription().contains(risposte[0]))
                            {
                                notedescr.AddNote(nota);
                            }
                        }
                        
                    }
                    
                    

                }                
            }
                    
        } catch (IOException ex) {
            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
         
        }
        } catch (JAXBException ex) {
            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
  
