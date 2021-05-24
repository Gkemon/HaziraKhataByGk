// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.routine;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import com.gk.emon.android.BanglaDaysPicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RoutineInputDialog_ViewBinding implements Unbinder {
  private RoutineInputDialog target;

  private View view7f09028f;

  private View view7f0900f2;

  private View view7f090290;

  private View view7f09028e;

  private View view7f090293;

  private View view7f090294;

  private View view7f090296;

  private View view7f0900a8;

  private View view7f0900a9;

  private View view7f0900a7;

  private View view7f0900a5;

  private View view7f0900b5;

  private View view7f0900dc;

  private View view7f0900ea;

  private View view7f0902ae;

  @UiThread
  public RoutineInputDialog_ViewBinding(final RoutineInputDialog target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.rb_class_routine, "field 'rbClassRoutine' and method 'onCheckedChanged'");
    target.rbClassRoutine = Utils.castView(view, R.id.rb_class_routine, "field 'rbClassRoutine'", RadioButton.class);
    view7f09028f = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.cb_trigger_alarm, "field 'cbTriggerAlarm' and method 'handleAlarm'");
    target.cbTriggerAlarm = Utils.castView(view, R.id.cb_trigger_alarm, "field 'cbTriggerAlarm'", CheckBox.class);
    view7f0900f2 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.handleAlarm(Utils.castParam(p0, "onCheckedChanged", 0, "handleAlarm", 0, CheckBox.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_exam_routine, "field 'rbExamRoutine' and method 'onCheckedChanged'");
    target.rbExamRoutine = Utils.castView(view, R.id.rb_exam_routine, "field 'rbExamRoutine'", RadioButton.class);
    view7f090290 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_admin_routine, "field 'rbAdminRoutine' and method 'onCheckedChanged'");
    target.rbAdminRoutine = Utils.castView(view, R.id.rb_admin_routine, "field 'rbAdminRoutine'", RadioButton.class);
    view7f09028e = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_others_routine, "field 'rbOtherRoutine' and method 'onCheckedChanged'");
    target.rbOtherRoutine = Utils.castView(view, R.id.rb_others_routine, "field 'rbOtherRoutine'", RadioButton.class);
    view7f090293 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_permanent_routine, "field 'rbPermanentRoutine' and method 'onCheckedChanged'");
    target.rbPermanentRoutine = Utils.castView(view, R.id.rb_permanent_routine, "field 'rbPermanentRoutine'", RadioButton.class);
    view7f090294 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_temporary_routine, "field 'rbTemporaryRoutine' and method 'onCheckedChanged'");
    target.rbTemporaryRoutine = Utils.castView(view, R.id.rb_temporary_routine, "field 'rbTemporaryRoutine'", RadioButton.class);
    view7f090296 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedChanged(Utils.castParam(p0, "onCheckedChanged", 0, "onCheckedChanged", 0, RadioButton.class), p1);
      }
    });
    target.etSubject = Utils.findRequiredViewAsType(source, R.id.et_subject, "field 'etSubject'", EditText.class);
    target.etRoom = Utils.findRequiredViewAsType(source, R.id.et_room, "field 'etRoom'", EditText.class);
    view = Utils.findRequiredView(source, R.id.bt_from_time, "field 'btnFromTime' and method 'showStartTimeDialog'");
    target.btnFromTime = Utils.castView(view, R.id.bt_from_time, "field 'btnFromTime'", AppCompatButton.class);
    view7f0900a8 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showStartTimeDialog();
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_to_time, "field 'btnToTime' and method 'showEndTimeDialog'");
    target.btnToTime = Utils.castView(view, R.id.bt_to_time, "field 'btnToTime'", AppCompatButton.class);
    view7f0900a9 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showEndTimeDialog();
      }
    });
    target.banglaDaysPicker = Utils.findRequiredViewAsType(source, R.id.weekdays, "field 'banglaDaysPicker'", BanglaDaysPicker.class);
    view = Utils.findRequiredView(source, R.id.bt_date_select, "field 'btnDateSelect' and method 'showDate'");
    target.btnDateSelect = Utils.castView(view, R.id.bt_date_select, "field 'btnDateSelect'", AppCompatButton.class);
    view7f0900a7 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showDate(Utils.castParam(p0, "doClick", 0, "showDate", 0, AppCompatButton.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_color_select, "field 'btnColorSelect' and method 'selectColor'");
    target.btnColorSelect = Utils.castView(view, R.id.bt_color_select, "field 'btnColorSelect'", AppCompatButton.class);
    view7f0900a5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectColor(Utils.castParam(p0, "doClick", 0, "selectColor", 0, AppCompatButton.class));
      }
    });
    target.etDetails = Utils.findRequiredViewAsType(source, R.id.et_detail, "field 'etDetails'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_delete_routine, "field 'btnDeleteRoutine' and method 'deleteRoutine'");
    target.btnDeleteRoutine = Utils.castView(view, R.id.btn_delete_routine, "field 'btnDeleteRoutine'", FloatingActionButton.class);
    view7f0900b5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.deleteRoutine(Utils.castParam(p0, "doClick", 0, "deleteRoutine", 0, FloatingActionButton.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_tutorial, "method 'showTutorial'");
    view7f0900dc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.showTutorial();
      }
    });
    view = Utils.findRequiredView(source, R.id.cancel, "method 'cancel'");
    view7f0900ea = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.cancel();
      }
    });
    view = Utils.findRequiredView(source, R.id.save, "method 'saveRoutine'");
    view7f0902ae = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.saveRoutine();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RoutineInputDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rbClassRoutine = null;
    target.cbTriggerAlarm = null;
    target.rbExamRoutine = null;
    target.rbAdminRoutine = null;
    target.rbOtherRoutine = null;
    target.rbPermanentRoutine = null;
    target.rbTemporaryRoutine = null;
    target.etSubject = null;
    target.etRoom = null;
    target.btnFromTime = null;
    target.btnToTime = null;
    target.banglaDaysPicker = null;
    target.btnDateSelect = null;
    target.btnColorSelect = null;
    target.etDetails = null;
    target.btnDeleteRoutine = null;

    ((CompoundButton) view7f09028f).setOnCheckedChangeListener(null);
    view7f09028f = null;
    ((CompoundButton) view7f0900f2).setOnCheckedChangeListener(null);
    view7f0900f2 = null;
    ((CompoundButton) view7f090290).setOnCheckedChangeListener(null);
    view7f090290 = null;
    ((CompoundButton) view7f09028e).setOnCheckedChangeListener(null);
    view7f09028e = null;
    ((CompoundButton) view7f090293).setOnCheckedChangeListener(null);
    view7f090293 = null;
    ((CompoundButton) view7f090294).setOnCheckedChangeListener(null);
    view7f090294 = null;
    ((CompoundButton) view7f090296).setOnCheckedChangeListener(null);
    view7f090296 = null;
    view7f0900a8.setOnClickListener(null);
    view7f0900a8 = null;
    view7f0900a9.setOnClickListener(null);
    view7f0900a9 = null;
    view7f0900a7.setOnClickListener(null);
    view7f0900a7 = null;
    view7f0900a5.setOnClickListener(null);
    view7f0900a5 = null;
    view7f0900b5.setOnClickListener(null);
    view7f0900b5 = null;
    view7f0900dc.setOnClickListener(null);
    view7f0900dc = null;
    view7f0900ea.setOnClickListener(null);
    view7f0900ea = null;
    view7f0902ae.setOnClickListener(null);
    view7f0902ae = null;
  }
}
