package com.emon.haziraKhata.note;

/**
 * Created by Gk Emon on 7/21/2020.
 */
public class NoteDao {
    private static NoteDao noteDao;
    public NoteDao getNoteDao(){
        if(noteDao==null)return new NoteDao();
        else return noteDao;
    }
}
