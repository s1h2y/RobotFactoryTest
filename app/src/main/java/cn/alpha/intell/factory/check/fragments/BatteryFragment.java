package cn.alpha.intell.factory.check.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cn.alpha.intell.factory.check.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BatteryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BatteryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BatteryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    int item_keys[] = {R.string.battery_volume, R.string.battery_status, R.string.battery_voltage,
            R.string.battery_temp};
    String item_values[] = new String[item_keys.length];
    private ListView mList;
    private Context mContext;
    private BatteryReceiver mReceiver;
    private BatteryAdapter mAdapter;

    class BatteryAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public BatteryAdapter(LayoutInflater mInflater) {
            this.mInflater = mInflater;
        }

        @Override
        public int getCount() {
            return item_keys.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.display_list_item, null);
                holder = new ViewHolder();
                holder.key = (TextView) convertView.findViewById(R.id.item_key);
                holder.value = (TextView) convertView.findViewById(R.id.item_value);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.key.setText(item_keys[position]);
            holder.value.setText(item_values[position]);
            return convertView;
        }

        class ViewHolder {
            TextView key;
            TextView value;
        }
    }

    public BatteryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BatteryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BatteryFragment newInstance(String param1, String param2) {
        BatteryFragment fragment = new BatteryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        prepareData();
        mContext = getActivity();
        registerReceiver();
    }

    private void prepareData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_display_simple, container, false);
        mList = (ListView) root.findViewById(R.id.list);
        mList.setAdapter(mAdapter = new BatteryAdapter(inflater));
        return root;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unRegisterReceiver();
        mListener = null;
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        mReceiver = new BatteryReceiver();
        mContext.registerReceiver(mReceiver, filter);
    }

    private void unRegisterReceiver() {
        if (null != mReceiver) {
            mContext.unregisterReceiver(mReceiver);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class BatteryReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
            int volt = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            item_values[0] = level + " %";
            switch (status) {
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    item_values[1] = getString(R.string.battery_status_unknown);
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    item_values[1] = getString(R.string.battery_status_charging);
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    item_values[1] = getString(R.string.battery_status_discharging);
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    item_values[1] = getString(R.string.battery_status_not_charging);
                    break;
                case BatteryManager.BATTERY_STATUS_FULL:
                    item_values[1] = getString(R.string.battery_status_full);
                    break;
            }
            item_values[2] = volt / 1000.0f + " V";
            item_values[3] = temp / 10.0f + " " + getString(R.string.temp_symbol);
            mAdapter.notifyDataSetChanged();
        }
    }

}
