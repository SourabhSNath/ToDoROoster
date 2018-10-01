package com.example.sourabh.todorooster;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sourabh.todorooster.Database.ToDoRepository;
import com.example.sourabh.todorooster.ViewModel.RoosterViewModel;
import com.example.sourabh.todorooster.databinding.TodoDisplayBinding;

import static com.example.sourabh.todorooster.Utils.Constants.ARG_ID;

public class DisplayFragment extends Fragment {
    private RoosterViewModel viewModel;
    private TodoDisplayBinding binding;

    public static DisplayFragment newInstance(ToDoModel model) {
        DisplayFragment fragment = new DisplayFragment();
        if (model != null) {
            Bundle args = new Bundle();

            args.putString(ARG_ID, model.id());
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = TodoDisplayBinding.inflate(getLayoutInflater(), container, false);

        viewModel=ViewModelProviders.of(getActivity()).get(RoosterViewModel.class); //getActivity instead of 'this' to provide the same viewmodel for all the fragments



        return binding.getRoot();
    }

    private String getModelID() {
        assert getArguments() != null;
        return getArguments().getString(ARG_ID);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToDoModel model = ToDoRepository.getInstance().find(getModelID());
        binding.setModel(model);
        binding.setCreatedOn(DateUtils.getRelativeDateTimeString(getActivity(),
                model.createdOn().getTimeInMillis(), DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_display,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.edit){
            ((Contract)getActivity()).editModel(binding.getModel());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    interface Contract{
        void editModel(ToDoModel model);
    }
}
