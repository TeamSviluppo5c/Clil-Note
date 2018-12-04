/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clil.xml;

import serverclil.xml.*;
import serverclil.xml.Note;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author SSLAVIERO
 */
@XmlRootElement
public class NoteBook {
    
    
    ArrayList<Note> notes=new ArrayList();
    
@XmlElement
    public ArrayList<Note> getNotes() {
        return notes;
    }
    ArrayList<Note> notes_word=new ArrayList();
    ArrayList<Note> notes_date=new ArrayList();
    Date data =new Date();
    
    public void AddNote(String description)
    {
        notes.add(new Note(description));
    }
    
    public void AddNote(Note t)
    {
        notes.add(t);
    }
    
    public ArrayList<Note> GetNoteByDescription(String word)
    {
        notes_word.removeAll(notes);
        
        for (int i = 0; i < notes.size(); i++) {
             if(notes.get(i).getDescription().compareTo(word)==0)
             {
                 notes_word.add(notes.get(i));
             }           
             
        }
        return notes_word;
    }
    public ArrayList<Note> GetNotesByDate(Date date)
    {
        notes_date.removeAll(notes);
        
        for (int i = 0; i < notes.size(); i++) {
             if(notes.get(i).getData().compareTo(date)==0)
             {
                 notes_date.add(notes.get(i));
             }           
             
        }
        return notes_word;
    }
    //Team--->Git---->Initialize repository(inizializzo il deposito dove salvo i file)
    //Team--->Commit---> salvo effettivamente il file che poi posso aggiornare
}
