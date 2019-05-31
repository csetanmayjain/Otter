package com.scottblechman.otter.ui.fragment.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.scottblechman.otter.R;
import com.scottblechman.otter.lifecycle.AlarmViewModel;
import com.scottblechman.otter.db.Alarm;
import com.scottblechman.otter.ui.activity.AlarmActivity;
import com.scottblechman.otter.ui.fragment.AlarmFragment;
import com.scottblechman.otter.ui.fragment.AlarmFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.Objects;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Alarm} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

    private List<Alarm> mValues;
    private final OnListFragmentInteractionListener mListener;
    private AlarmViewModel mAlarmViewModel;
    private AlarmFragment mFragment;

    public AlarmRecyclerViewAdapter(OnListFragmentInteractionListener listener, AlarmFragment fragment) {
        mListener = listener;
        mFragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm, parent, false);
        mAlarmViewModel = ViewModelProviders.of(mFragment).get(AlarmViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Log.d("AlarmRecyclerViewAdapte", "onBindViewHolder: "+mValues.get(position).toString());
        holder.mDateView.setText(mValues.get(position).getDate().toString());
        holder.mTimeView.setText(mValues.get(position).getDate().toString());
        holder.mLabelView.setText(mValues.get(position).getLabel());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void setAlarms(List<Alarm> alarms){
        mValues = alarms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null) {
            return mValues.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mDateView;
        final TextView mTimeView;
        final TextView mLabelView;
        final Button mDeleteButton;
        final Button mAlarmButton;
        Alarm mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = view.findViewById(R.id.date);
            mTimeView = view.findViewById(R.id.time);
            mLabelView = view.findViewById(R.id.label);
            mDeleteButton = view.findViewById(R.id.buttonDelete);
            mAlarmButton = view.findViewById(R.id.buttonAlarm);

            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlarmViewModel.delete(mItem);
                    Toast.makeText(v.getContext(),"Delete item " + mItem.getLabel(),
                            Toast.LENGTH_SHORT).show();
                }
            });

            mAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mFragment.getContext(), AlarmActivity.class);
                    Objects.requireNonNull(mFragment.getActivity()).startActivity(intent);
                }
            });
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTimeView.getText() + "'";
        }
    }
}