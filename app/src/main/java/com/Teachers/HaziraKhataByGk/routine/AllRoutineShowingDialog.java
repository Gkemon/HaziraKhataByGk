package com.Teachers.HaziraKhataByGk.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.Teachers.HaziraKhataByGk.HelperClasses.UtilsCommon;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.CustomViewPager;
import com.Teachers.HaziraKhataByGk.HelperClasses.ViewUtils.ViewPagerAdapter;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AllRoutineShowingDialog extends BaseFullScreenDialog {

    RoutineViewModel routineViewModel;

    @BindView(R.id.viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    public static void showDialog(FragmentManager manager) {

        AllRoutineShowingDialog dialog = new AllRoutineShowingDialog();
        FragmentTransaction ft = manager.beginTransaction();
        dialog.show(ft, AllRoutineShowingDialog.class.getSimpleName());

    }


    @OnClick(R.id.btn_tutorial)
    void showTutorial(){
        UtilsCommon.openWithFaceBook("https://www.facebook.com/comrate.lenin.7/videos/751533605417104/",getContext());
    }
    @OnClick(R.id.btn_add_routine)
    void addRoutine() {
        if (getActivity() != null) {
            if(routineViewModel!=null){
                routineViewModel.setSelectedRoutineItem(null);
                routineViewModel.inputOperation=InputOperation.ADD;
                RoutineInputDialog.showDialog(getActivity().getSupportFragmentManager());
            }
        }
    }



    private void setupViewPager() {

        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        Bundle bundle1 = new Bundle();
        bundle1.putString(RoutineConstant.routineType, RoutineConstant.ROUTINE_TYPE_CLASS);
        RoutineWeekViewFragment fragmentClassRoutine = new RoutineWeekViewFragment();
        fragmentClassRoutine.setArguments(bundle1);
        adapter.addFrag(fragmentClassRoutine);

        Bundle bundle2 = new Bundle();
        bundle2.putString(RoutineConstant.routineType, RoutineConstant.ROUTINE_TYPE_EXAM);
        RoutineWeekViewFragment fragmentExamRoutine = new RoutineWeekViewFragment();
        fragmentExamRoutine.setArguments(bundle2);
        adapter.addFrag(fragmentExamRoutine);

        Bundle bundle3 = new Bundle();
        bundle3.putString(RoutineConstant.routineType, RoutineConstant.ROUTINE_TYPE_ADMINISTRATION);
        RoutineWeekViewFragment fragmentAdmin = new RoutineWeekViewFragment();
        fragmentAdmin.setArguments(bundle3);
        adapter.addFrag(fragmentAdmin);

        Bundle bundle4 = new Bundle();
        bundle4.putString(RoutineConstant.routineType, RoutineConstant.ROUTINE_TYPE_OTHER);
        RoutineWeekViewFragment fragmentOthers = new RoutineWeekViewFragment();
        fragmentOthers.setArguments(bundle4);
        adapter.addFrag(fragmentOthers);

        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupTab() {

        TextView tabOne = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.routine_class);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_class_fragment, 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.routine_exam);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_nibondhon, 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.routine_admin);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_action_job, 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
        tabFour.setText(R.string.routine_others);
        tabFour.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person, 0, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_show_routine, container, false);
        ButterKnife.bind(this, view);

        if(getActivity()!=null){
            routineViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
        }
        setupViewPager();
        setupTab();

        return view;
    }

}
