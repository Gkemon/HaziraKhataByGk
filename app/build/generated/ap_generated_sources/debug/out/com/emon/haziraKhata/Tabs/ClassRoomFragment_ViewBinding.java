// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.Tabs;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ClassRoomFragment_ViewBinding implements Unbinder {
  private ClassRoomFragment target;

  private View view7f0900d2;

  @UiThread
  public ClassRoomFragment_ViewBinding(final ClassRoomFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_make_schedule, "method 'showRoutine'");
    view7f0900d2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showRoutine();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f0900d2.setOnClickListener(null);
    view7f0900d2 = null;
  }
}
