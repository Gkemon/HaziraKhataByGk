package com.Teachers.HaziraKhataByGk.adapter;

//public class MonthlyDataAdapter extends BaseAdapter {
//    public  ArrayList<String> MonthDate;
//    public Activity activity;
//

//    public MonthlyDataAdapter(Activity activity, ArrayList<String> nameList) {
//        this.MonthDate = nameList;
//    }
//    @Override
//    public int getCount() {
//        return nameList.size();
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(final int position, View v, ViewGroup parent) {
//
//        if (v == null) {
//            LayoutInflater vi = LayoutInflater.from(activity);
//            v = vi.inflate(R.layout.monthly_total_data_item, null);
//        }
//
//
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent launchinIntent = new Intent(attendanceActivity.activity, studentAllInfoShowActiviy.class);
//                String roll = attendanceActivity.rolls.get(position);
//
//                launchinIntent.putExtra("Roll", roll);
//                attendanceActivity.activity.startActivity(launchinIntent);
//            }
//        });
//
//
//        return v;
//    }
//
//
//
//    public void clear() {
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }
//    public String getItem(int position) {
//        return nameList.get(position);
//    }
//
//
//    public void remove(String item) {
//        int position = nameList.indexOf(item);
//        if (position > -1) {
//            nameList.remove(position);
//
//        }
//    }
//
//    public int getItemCount() {
//        return nameList.size();
//    }
//}
