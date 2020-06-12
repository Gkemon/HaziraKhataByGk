package com.Teachers.HaziraKhataByGk.routine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.CustomViewPager;
import com.Teachers.HaziraKhataByGk.HelperClassess.ViewUtils.ViewPagerAdapter;
import com.Teachers.HaziraKhataByGk.Home.SettingsActivity;
import com.Teachers.HaziraKhataByGk.R;
import com.Teachers.HaziraKhataByGk.Widget.BaseFullScreenDialog;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

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

    @OnClick(R.id.btn_add_routine)
    void addRoutine() {
        if (getActivity() != null) {
            routineViewModel.setSelectedRoutineItem(null);
            RoutineInputDialog.showDialog(getActivity().getSupportFragmentManager());
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

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_show_routine, container, false);
        ButterKnife.bind(this, view);

        if(getActivity()!=null){
            if(PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getBoolean(SettingsActivity.IS_NOTIFICATION_ENABLED,false))
            {
                routineViewModel = new ViewModelProvider(getActivity()).get(RoutineViewModel.class);
                routineViewModel.getAllLiveRoutines().observe(getViewLifecycleOwner(), routineItems -> {
                    if(routineItems.isEmpty())
                        RoutineUtils.stopEventShowingService(getContext());
                    else RoutineUtils.startEventShowingService(getContext(),routineItems,false);
                });
            }
        }



        setupViewPager();
        setupTab();

        return view;
    }

}
