package com.alpha.smart.factorytest.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alpha.smart.factorytest.R;
import com.alpha.smart.factorytest.beans.WifiBean;
import com.alpha.smart.factorytest.utils.Constant;
import com.alpha.smart.factorytest.utils.Result;
import com.alpha.smart.factorytest.utils.WifiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WifiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WifiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private WifiUtils mWifiUtils;
    private ToggleButton mSwitch;
    private ListView mWifiList;
    private List<WifiBean> mList = new ArrayList<WifiBean>();
    private WifiAdapter mAdapter;
    private TextView mTips;

    public WifiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiFragment newInstance(String param1, String param2) {
        WifiFragment fragment = new WifiFragment();
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
        mContext = getActivity();
        mWifiUtils = new WifiUtils(mContext, mHandler);
        registerWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mContext.registerReceiver(mScanReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_wifi, container, false);
        initView(root);
        return root;
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    class WifiAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public WifiAdapter(LayoutInflater mInflater) {
            this.mInflater = mInflater;
        }

        @Override
        public int getCount() {
            return mList.size();
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
            if (null == convertView) {
                convertView = mInflater.inflate(R.layout.list_item_wifi_list, null);
                holder = new ViewHolder();
                holder.ssid = (TextView) convertView.findViewById(R.id.ssid);
                holder.level = (TextView) convertView.findViewById(R.id.level);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.ssid.setText(mList.get(position).ssid);
            holder.level.setText(mList.get(position).level + " dbm");
            return convertView;
        }

        @Override
        public boolean isEnabled(int position) {
            return super.isEnabled(position);
        }

        class ViewHolder {
            TextView ssid;
            TextView level;
        }
    }

    private void initView(View root) {
        mTips = (TextView) root.findViewById(R.id.tips);
        mSwitch = (ToggleButton) root.findViewById(R.id.switch_button);
        mWifiList = (ListView) root.findViewById(R.id.wifi_list);
        mAdapter = new WifiAdapter(LayoutInflater.from(mContext));
        mWifiList.setAdapter(mAdapter);
        mWifiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mTips.setText(getString(R.string.connecting) + mList.get(position).ssid);
                mWifiUtils.connectWifi(mList.get(position));
            }
        });
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mWifiUtils.openWifi();
                    mWifiUtils.startScan();
                } else {
                    mWifiUtils.closeWifi();
                    mList.clear();
                    mTips.setText(R.string.choose_net);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        mSwitch.setChecked(true);
        CheckBox checkNoNetwork = (CheckBox)root.findViewById(R.id.check_no_network);
        CheckBox checkConnectFail = (CheckBox)root.findViewById(R.id.check_connect_fail);
        checkConnectFail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.WIFI_SEARCH);
                } else {
                    Result.passed(Constant.WIFI_SEARCH);
                }
            }
        });
        checkConnectFail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Result.failed(Constant.WIFI_CONNECT);
                } else {
                    Result.passed(Constant.WIFI_CONNECT);
                }
            }
        });
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
        mListener = null;
        mContext.unregisterReceiver(mScanReceiver);
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

    private BroadcastReceiver mScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                mList = mWifiUtils.getWifiInfo();
                mAdapter.notifyDataSetChanged();
            }

            if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                Parcelable pe = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (null != pe) {
                    NetworkInfo info = (NetworkInfo) pe;
                    NetworkInfo.State state = info.getState();
                    if (NetworkInfo.State.CONNECTED == state) {
                        Log.d("shy", "wifi connected");
                        String ssid = mWifiUtils.getCurSSID();
                        if (mSwitch.isChecked() && ssid != null && !ssid.contains("unknown")) {
                            mTips.setText(getString(R.string.connected) + ssid);
                        }
                    }
                }
            }
        }
    };
}
