// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.Attendance;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AttendanceActivity_ViewBinding implements Unbinder {
  private AttendanceActivity target;

  private View view7f09028d;

  private View view7f090295;

  @UiThread
  public AttendanceActivity_ViewBinding(AttendanceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AttendanceActivity_ViewBinding(final AttendanceActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rb_absent_all, "method 'absentAll'");
    view7f09028d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.absentAll();
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_present_all, "method 'presentAll'");
    view7f090295 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.presentAll();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view7f09028d.setOnClickListener(null);
    view7f09028d = null;
    view7f090295.setOnClickListener(null);
    view7f090295 = null;
  }
}
