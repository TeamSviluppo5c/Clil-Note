/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverclil.xml;

import clil.xml.EverNote;
import serverclil.xml.Note;
import serverclil.xml.NoteBook;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
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
        NoteBook notedata=new NoteBook();
        
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
                        notedescr.getNotes().clear();
                        for(Note nota:note.getNotes())
                        {
                            if(nota.getDescription().contains(risposte[0]))
                            {
                                notedescr.AddNote(nota);
                            }
                        }
                        JAXBContext jb;
                        try {        
                            jb = JAXBContext.newInstance(NoteBook.class);
                            Marshaller m1=jb.createMarshaller();

                            try {
                            
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(latoclient.getOutputStream())), true);
                            StringWriter sw = new StringWriter();
                            m1.marshal(notedescr,sw);
                            System.out.println(sw.toString());
                            out.println(sw.toString());

                            

                        } catch (IOException ex) {
                            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        } catch (JAXBException ex) {
                            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        
                    }
                    
                    if(risposte[1].compareTo("3")==0)
                    {
                        String[] data= risposte[0].split("-");
                        notedata.getNotes().clear();
                        for(Note nota:note.getNotes())
                        {
                            if((nota.getData().getYear()+1900)==Integer.parseInt(data[0]))
                            {
                                if((nota.getData().getMonth())==Integer.parseInt(data[1]))
                                {
                                    if((nota.getData().getDate())==Integer.parseInt(data[2]))
                                    {
                                        notedata.AddNote(nota);
                                    }                                  
                                }
                                
                            }
                        }
                        
                        JAXBContext jb;
                        try {        
                            jb = JAXBContext.newInstance(NoteBook.class);
                            Marshaller m1=jb.createMarshaller();

                            try {
                            
                            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(latoclient.getOutputStream())), true);
                            StringWriter sw = new StringWriter();
                            m1.marshal(notedata,sw);
                            System.out.println(sw.toString());
                            out.println(sw.toString());

                            

                        } catch (IOException ex) {
                            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        } catch (JAXBException ex) {
                            Logger.getLogger(ServerClilXML.class.getName()).log(Level.SEVERE, null, ex);
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
    
  
