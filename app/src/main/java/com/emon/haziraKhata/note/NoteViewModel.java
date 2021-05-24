package com.emon.haziraKhata.note;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Gk Emon on 8/3/2020.
 */
public class NoteViewModel extends ViewModel {
    MutableLiveData<Boolean> isNewNoteAdded=new MutableLiveData<>(false);
}
