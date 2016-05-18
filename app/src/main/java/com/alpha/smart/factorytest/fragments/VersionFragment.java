package com.alpha.smart.factorytest.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alpha.smart.factorytest.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link VersionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link VersionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VersionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    ListView mList;
    int item_keys[] = {R.string.item_version, R.string.seq, R.string.lcd_manufacturer,
            R.string.ddr_manufacturer, R.string.soc_manufacturer};
    String item_values[] = new String[item_keys.length];

    class MyAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public MyAdapter(LayoutInflater mInflater) {
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

    public VersionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VersionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VersionFragment newInstance(String param1, String param2) {
        VersionFragment fragment = new VersionFragment();
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
    }

    private void prepareData() {
        item_values[0] = Build.VERSION.RELEASE;
        item_values[1] = Build.SERIAL;
        item_values[2] = Build.PRODUCT;
        item_values[3] = Build.BOARD;
        item_values[4] = Build.MANUFACTURER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_simple, container, false);
        mList = (ListView) view.findViewById(R.id.list);
        mList.setAdapter(new MyAdapter(inflater));
        return view;
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
}
