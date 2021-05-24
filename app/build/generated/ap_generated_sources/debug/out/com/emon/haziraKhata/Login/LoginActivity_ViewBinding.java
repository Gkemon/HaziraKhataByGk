// Generated code from Butter Knife. Do not modify!
package com.emon.haziraKhata.Login;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.emon.haziraKhata.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.tlPhone = Utils.findRequiredView(source, R.id.tl_phone, "field 'tlPhone'");
    target.tlPin = Utils.findRequiredView(source, R.id.tl_pin, "field 'tlPin'");
    target.tlEmail = Utils.findRequiredView(source, R.id.tl_email, "field 'tlEmail'");
    target.tlPass = Utils.findRequiredView(source, R.id.pass_layout, "field 'tlPass'");
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tlPhone = null;
    target.tlPin = null;
    target.tlEmail = null;
    target.tlPass = null;
  }
}
