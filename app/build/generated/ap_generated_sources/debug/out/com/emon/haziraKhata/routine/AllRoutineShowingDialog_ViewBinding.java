// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.routine;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.HelperClasses.ViewUtils.CustomViewPager;
import com.emon.haziraKhata.R;
import com.google.android.material.tabs.TabLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AllRoutineShowingDialog_ViewBinding implements Unbinder {
  private AllRoutineShowingDialog target;

  private View view7f0900dc;

  private View view7f0900b1;

  @UiThread
  public AllRoutineShowingDialog_ViewBinding(final AllRoutineShowingDialog target, View source) {
    this.target = target;

    View view;
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", CustomViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tabs, "field 'tabLayout'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_tutorial, "method 'showTutorial'");
    view7f0900dc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showTutorial();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_add_routine, "method 'addRoutine'");
    view7f0900b1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.addRoutine();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AllRoutineShowingDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.tabLayout = null;

    view7f0900dc.setOnClickListener(null);
    view7f0900dc = null;
    view7f0900b1.setOnClickListener(null);
    view7f0900b1 = null;
  }
}
