package com.emon.haziraKhata.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emon.haziraKhata.Adapter.NoteListAdapter;
import com.emon.haziraKhata.HelperClasses.UtilsCommon;
import com.emon.haziraKhata.Listener.RecyclerItemClickListener;
import com.emon.haziraKhata.R;
import com.emon.haziraKhata.Widget.BaseFullScreenDialog;
import com.emon.haziraKhata.note.dataSource.RemoteDataSourceImp;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NoteShowingDialog extends BaseFullScreenDialog implements RecyclerItemClickListener {
    RemoteDataSourceImp remoteDataSourceImp;
    NoteListAdapter noteListAdapter;
    NoteViewModel noteViewModel;
    @BindView(R.id.rv_note)
    RecyclerView rvNote;
    @BindView(R.id.toDoEmptyView)
    View vEmptyView;
    private String databasePath;

    public static void showDialog(FragmentManager manager, String databasePath) {

        NoteShowingDialog dialog = new NoteShowingDialog();

        Bundle bundle = new Bundle();
        bundle.putString("databasePath", databasePath);
        dialog.setArguments(bundle);

        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, NoteShowingDialog.class.getSimpleName());

    }

    @OnClick(R.id.fab_add_note)
    public void addNewRoutine() {
        if (getActivity() != null)
            NoteInputDialog.showDialog(getActivity().getSupportFragmentManager(),databasePath,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_note_list, container, false);
        ButterKnife.bind(this, view);
        noteListAdapter = new NoteListAdapter(getContext(), this);
        rvNote.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNote.setAdapter(noteListAdapter);

        remoteDataSourceImp = new RemoteDataSourceImp();

        noteViewModel = new ViewModelProvider(getActivity()).get(NoteViewModel.class);

        if (getArguments() != null && UtilsCommon.isValideString(getArguments().getString("databasePath"))) {
            databasePath = getArguments().getString("databasePath");
            fetchNotes();
            noteViewModel.isNewNoteAdded.observe(getViewLifecycleOwner(), isAdded -> {
                if(isAdded){
                    fetchNotes();
                }
            });
        }


        return view;
    }

    private void fetchNotes() {
        remoteDataSourceImp.getNotes(FirebaseDatabase.getInstance().getReference().child(databasePath))
                .observe(getViewLifecycleOwner(), notes -> {
                    if(notes.isEmpty())
                    vEmptyView.setVisibility(View.VISIBLE);
                    else noteListAdapter.addAll(notes);
                });
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    @Override
    public void onItemLongPressed(int position, View view) {

    }
}
