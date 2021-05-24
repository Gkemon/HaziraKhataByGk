// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.note;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NoteShowingDialog_ViewBinding implements Unbinder {
  private NoteShowingDialog target;

  private View view7f09017d;

  @UiThread
  public NoteShowingDialog_ViewBinding(final NoteShowingDialog target, View source) {
    this.target = target;

    View view;
    target.rvNote = Utils.findRequiredViewAsType(source, R.id.rv_note, "field 'rvNote'", RecyclerView.class);
    target.vEmptyView = Utils.findRequiredView(source, R.id.toDoEmptyView, "field 'vEmptyView'");
    view = Utils.findRequiredView(source, R.id.fab_add_note, "method 'addNewRoutine'");
    view7f09017d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addNewRoutine();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    NoteShowingDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rvNote = null;
    target.vEmptyView = null;

    view7f09017d.setOnClickListener(null);
    view7f09017d = null;
  }
}
