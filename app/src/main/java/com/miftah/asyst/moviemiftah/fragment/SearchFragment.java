package com.miftah.asyst.moviemiftah.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.miftah.asyst.moviemiftah.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    Button btnSearch;
    EditText etSearch;
    SearchFragment.OnSubmitButtonListener listener;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String search) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("search", search);

        searchFragment.setArguments(bundle);
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        etSearch = view.findViewById(R.id.et_search);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String search = etSearch.getText().toString();
                if (!TextUtils.isEmpty(search)) {
                    listener.onSubmitButton(search);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Harus Diisi", Toast.LENGTH_SHORT).show();
                }

                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSubmitButtonListener) {
            listener = (SearchFragment.OnSubmitButtonListener) context;
        } else {
            throw new RuntimeException(context.toString() + "Activity harus implement interface OnSubmitButtonListener");
        }
    }

    public interface OnSubmitButtonListener {
        void onSubmitButton(String query);
    }
}
