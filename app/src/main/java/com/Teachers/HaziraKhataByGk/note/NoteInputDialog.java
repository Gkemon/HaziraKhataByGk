package com.Teachers.HaziraKhataByGk.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.Teachers.HaziraKhataByGk.HelperClasses.DialogUtils;
import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsDateTime;
import com.Teachers.HaziraKhataByGk.Listener.CommonCallback;
import com.Teachers.HaziraKhataByGk.Model.Notes;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.Teachers.HaziraKhataByGk.note.dataSource.RemoteDataSourceImp;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteInputDialog extends BaseFullScreenDialog {
    RemoteDataSourceImp remoteDataSourceImp;
    private String databasePath;
    private Notes notes;
    @BindView(R.id.bt_date)
    Button btnDate;
    @BindView(R.id.et_note_title)
    EditText etNoteTitle;
    @BindView(R.id.et_content)
    EditText etNoteContent;
    @BindView(R.id.btn_add_note)
    Button btnAdd;
    @BindView(R.id.btn_save_note)
    Button btnSaveNote;
    @BindView(R.id.btn_delete_note)
    Button btnDelete;
    private NoteViewModel noteViewModel;

    public static void showDialog(FragmentManager manager, String databasePath, Notes notes) {
        NoteInputDialog dialog = new NoteInputDialog();

        Bundle bundle = new Bundle();
        bundle.putString("databasePath", databasePath);
        bundle.putParcelable("note", notes);

        dialog.setArguments(bundle);

        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, NoteInputDialog.class.getSimpleName());
    }

    @OnClick({R.id.btn_add_note,R.id.btn_save_note})
    public void addNewNote() {


        notes.setDate(btnDate.getText().toString());
        notes.setheading(etNoteTitle.getText().toString());
        notes.setContent(etNoteContent.getText().toString());

        if(notes.getheading().isEmpty()&&!notes.getContent().isEmpty()){
            if(notes.getContent().length()>=20)
            notes.setheading(notes.getContent().substring(0,19));
            else notes.setheading(notes.getContent());
        }


        if (notes == null || !UtilsCommon.isValideString(notes.getheading())) {
            DialogUtils.showInfoAlertDialog("", "নোটটি খালি। দয়া করা অন্তত শিরোনামটি ইনপুট দিন সেভ করার আগে।", getContext());
            return;
        }
        remoteDataSourceImp.addNote(FirebaseDatabase.getInstance(databasePath).getReference()
                , notes, new CommonCallback() {
                    @Override
                    public void onFailure(Throwable r) {
                        UtilsCommon.handleError(r);
                        dismiss();
                    }

                    @Override
                    public void onSuccess() {
                        dismiss();
                        noteViewModel.isNewNoteAdded.setValue(true);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_note_add, container, false);
        ButterKnife.bind(this, view);
        remoteDataSourceImp = new RemoteDataSourceImp();
        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);

        notes = new Notes();
        if (getArguments() != null && UtilsCommon.isValideString(getArguments().getString("databasePath"))) {
            databasePath = getArguments().getString("databasePath");
            notes = getArguments().getParcelable("note");
            if (notes != null) {
                if (UtilsCommon.isValideString(notes.getDate()))
                    btnDate.setText(notes.getDate());
                else btnDate.setText(UtilsDateTime.getSimpleDateText(Calendar.getInstance()));

                if (UtilsCommon.isValideString(notes.getContent()))
                    etNoteContent.setText(notes.getContent());

                if (UtilsCommon.isValideString(notes.getheading()))
                    etNoteTitle.setText(notes.getheading());

                btnAdd.setVisibility(View.GONE);
                btnDelete.setVisibility(View.VISIBLE);
                btnSaveNote.setVisibility(View.VISIBLE);
            } else {
                btnDate.setText(UtilsDateTime.getSimpleDateText(Calendar.getInstance()));
                btnAdd.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.GONE);
                btnSaveNote.setVisibility(View.GONE);
            }
        } else {
            btnDate.setText(UtilsDateTime.getSimpleDateText(Calendar.getInstance()));
            btnAdd.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
            btnSaveNote.setVisibility(View.GONE);
        }


        return view;
    }

    @OnClick(R.id.bt_date)
    void showDate(AppCompatButton btn) {
        DialogUtils.showDateDialog(null, getContext(), (datePicker, year, month, dayOfMonth) -> {
            btn.setText(UtilsDateTime.getSimpleDateText(year, month, dayOfMonth));
            notes.setDate(UtilsDateTime.getSimpleDateText(year, month, dayOfMonth));
        });
    }
}
