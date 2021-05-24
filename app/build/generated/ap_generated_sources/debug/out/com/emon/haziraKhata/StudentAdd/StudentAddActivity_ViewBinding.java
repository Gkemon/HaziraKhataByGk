// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.StudentAdd;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StudentAddActivity_ViewBinding implements Unbinder {
  private StudentAddActivity target;

  private View view7f0900b3;

  private View view7f0900db;

  @UiThread
  public StudentAddActivity_ViewBinding(StudentAddActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StudentAddActivity_ViewBinding(final StudentAddActivity target, View source) {
    this.target = target;

    View view;
    target.etParent2Name = Utils.findRequiredViewAsType(source, R.id.person2ParentTextFromStudentAct, "field 'etParent2Name'", EditText.class);
    target.etParent2Phone = Utils.findRequiredViewAsType(source, R.id.Parents2phoneNumbersFromStudentAct, "field 'etParent2Phone'", EditText.class);
    target.etBirthCertificate = Utils.findRequiredViewAsType(source, R.id.et_birth_certificate, "field 'etBirthCertificate'", EditText.class);
    target.rbMale = Utils.findRequiredViewAsType(source, R.id.rb_male, "field 'rbMale'", RadioButton.class);
    target.rbFemale = Utils.findRequiredViewAsType(source, R.id.rb_female, "field 'rbFemale'", RadioButton.class);
    view = Utils.findRequiredView(source, R.id.btn_birth_date_select, "field 'btnBirthDay' and method 'selectBirthDate'");
    target.btnBirthDay = Utils.castView(view, R.id.btn_birth_date_select, "field 'btnBirthDay'", Button.class);
    view7f0900b3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.selectBirthDate(Utils.castParam(p0, "doClick", 0, "selectBirthDate", 0, Button.class));
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_student_pro_pic_upload, "method 'chooseProPic'");
    view7f0900db = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.chooseProPic();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    StudentAddActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etParent2Name = null;
    target.etParent2Phone = null;
    target.etBirthCertificate = null;
    target.rbMale = null;
    target.rbFemale = null;
    target.btnBirthDay = null;

    view7f0900b3.setOnClickListener(null);
    view7f0900b3 = null;
    view7f0900db.setOnClickListener(null);
    view7f0900db = null;
  }
}
