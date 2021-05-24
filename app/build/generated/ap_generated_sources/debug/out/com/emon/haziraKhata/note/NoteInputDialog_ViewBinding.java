// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.note;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NoteInputDialog_ViewBinding implements Unbinder {
  private NoteInputDialog target;

  private View view7f0900a6;

  private View view7f0900b0;

  private View view7f0900d7;

  @UiThread
  public NoteInputDialog_ViewBinding(final NoteInputDialog target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.bt_date, "field 'btnDate' and method 'showDate'");
    target.btnDate = Utils.castView(view, R.id.bt_date, "field 'btnDate'", Button.class);
    view7f0900a6 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showDate(Utils.castParam(p0, "doClick", 0, "showDate", 0, AppCompatButton.class));
      }
    });
    target.etNoteTitle = Utils.findRequiredViewAsType(source, R.id.et_note_title, "field 'etNoteTitle'", EditText.class);
    target.etNoteContent = Utils.findRequiredViewAsType(source, R.id.et_content, "field 'etNoteContent'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_add_note, "field 'btnAdd' and method 'addNewNote'");
    target.btnAdd = Utils.castView(view, R.id.btn_add_note, "field 'btnAdd'", Button.class);
    view7f0900b0 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addNewNote();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save_note, "field 'btnSaveNote' and method 'addNewNote'");
    target.btnSaveNote = Utils.castView(view, R.id.btn_save_note, "field 'btnSaveNote'", Button.class);
    view7f0900d7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addNewNote();
      }
    });
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btn_delete_note, "field 'btnDelete'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoteInputDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnDate = null;
    target.etNoteTitle = null;
    target.etNoteContent = null;
    target.btnAdd = null;
    target.btnSaveNote = null;
    target.btnDelete = null;

    view7f0900a6.setOnClickListener(null);
    view7f0900a6 = null;
    view7f0900b0.setOnClickListener(null);
    view7f0900b0 = null;
    view7f0900d7.setOnClickListener(null);
    view7f0900d7 = null;
  }
}
