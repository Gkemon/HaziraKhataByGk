package com.emon.haziraKhata.note.dataSource;

import androidx.lifecycle.MutableLiveData;

import com.emon.haziraKhata.Listener.CommonCallback;
import com.emon.haziraKhata.Model.Notes;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Gk Emon on 7/21/2020.
 */
interface RemoteNoteSource {
    MutableLiveData<List<Notes>> getNotes(DatabaseReference databaseReference);
    Notes getNotesByUID(DatabaseReference databaseReference);
    void addNote(DatabaseReference databaseReference, Notes notes, CommonCallback commonCallback);
}
