package com.miftah.asyst.moviemiftah.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.miftah.asyst.moviemiftah.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterFragment extends BottomSheetDialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    Spinner spinnerYear, spinnerSortBy;
    Button btnFilter;
    String year, sort;
    OnSubmitButtonListener listener;

    public FilterFragment() {
        // Required empty public constructor
    }

    public static FilterFragment newInstance(String year, String sortBy) {
        FilterFragment filterFragment = new FilterFragment();
        Bundle bundle = new Bundle();
        bundle.putString("year", year);
        bundle.putString("sortBy", sortBy);

        filterFragment.setArguments(bundle);
        return filterFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        spinnerYear = view.findViewById(R.id.spinner_year);
        spinnerSortBy = view.findViewById(R.id.spinner_filter);
        btnFilter = view.findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(this);

        spinnerYear.setOnItemSelectedListener(this);
        spinnerSortBy.setOnItemSelectedListener(this);

        ArrayList<String> years = new ArrayList<String>();
        years.add("None");
        for (int i = 2020; i >= 1999; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, years);
        spinnerYear.setAdapter(adapterYear);

        ArrayList<String> sort = new ArrayList<>();
        sort.add("popularity.asc");
        sort.add("popularity.desc");
        sort.add("release_date.asc");
        sort.add("release_date.desc");
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sort);
        spinnerSortBy.setAdapter(sortAdapter);

        //menerimad data
        if (getArguments() != null) {
            this.year = getArguments().getString("year", "");
            this.sort = getArguments().getString("sortBy", "");

            int positionYear = adapterYear.getPosition(this.year);
            spinnerYear.setSelection(positionYear);
            int positionSort = sortAdapter.getPosition(this.sort);
            spinnerSortBy.setSelection(positionSort);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_filter:
                year = spinnerYear.getSelectedItem().toString();
                sort = spinnerSortBy.getSelectedItem().toString();

                if (year == "None") {
                    listener.onSubmitButton("", sort);
                } else {
                    listener.onSubmitButton(year, sort);
                }
                dismiss();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        year = spinnerYear.getSelectedItem().toString();
        sort = spinnerSortBy.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FilterFragment.OnSubmitButtonListener) {
            listener = (FilterFragment.OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface OnSubmitButtonListener");
        }
    }


    public interface OnSubmitButtonListener {
        void onSubmitButton(String year, String sort);
    }
}
