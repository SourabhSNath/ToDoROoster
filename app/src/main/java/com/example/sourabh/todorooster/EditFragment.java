package com.example.sourabh.todorooster;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sourabh.todorooster.Database.ToDoRepository;
import com.example.sourabh.todorooster.Intents.Action;
import com.example.sourabh.todorooster.ViewModel.RoosterViewModel;
import com.example.sourabh.todorooster.databinding.TodoEditBinding;

import static com.example.sourabh.todorooster.Utils.Constants.ARG_ID;

public class EditFragment extends Fragment {
    private RoosterViewModel viewModel;
    private TodoEditBinding binding;

    public static EditFragment newInstance(ToDoModel model) {
        EditFragment fragment = new EditFragment();

        if (model != null) {
            Bundle args = new Bundle();

            args.putString(ARG_ID, model.id());
            fragment.setArguments(args);
        }
        return fragment;
    }

//    private String getModelID() {
//        return getArguments().getString(ARG_ID);  This wont work when adding new items, as it assumes there is always a model
//    }

    private String getModelID() {
        return getArguments() == null ? null : getArguments().getString(ARG_ID);  //Now this will work
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = TodoEditBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setModel(ToDoRepository.getInstance().find(getModelID()));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_edit, menu);

        menu.findItem(R.id.delete).setVisible(binding.getModel()!=null);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            save();
            return true;
        }
        else if (item.getItemId()==R.id.delete){
            delete();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void save() {  //see old version below
        ToDoModel.Builder builder;

        if (binding.getModel() == null) {
            builder = ToDoModel.creator();
        } else {
            builder = binding.getModel().toBuilder();
        }

        ToDoModel newModel = builder.description(binding.description.getText().toString())
                .notes(binding.notes.getText().toString())
                .isCompleted(binding.isCompleted.isChecked())
                .build();

        if (binding.getModel()==null){
//            ToDoRepository.getInstance().add(newModel);
            viewModel.process(Action.add(newModel));
        }else {
//            ToDoRepository.getInstance().replace(newModel);
            viewModel.process(Action.edit(newModel));
        }

        ((Contract)getActivity()).finishEdit(false);

    }

    private void delete(){
//        ToDoRepository.getInstance().delete(binding.getModel());
//        ((Contract)getActivity()).finishEdit(true);

        viewModel.process(Action.delete(binding.getModel()));
        ((Contract)getActivity()).finishEdit(true);
    }

    interface Contract {
        void finishEdit(boolean delete);
    }
}



//    private void save() {         //This method was used to replace already existing sample data, so cannot be used for adding new
//        ToDoModel newModel = binding.getModel().toBuilder()
//                .description(binding.description.getText().toString())
//                .isCompleted(binding.isCompleted.isChecked())
//                .notes(binding.notes.getText().toString())
//                .build();
//        ToDoRepository.getInstance().replace(newModel);
//        ((Contract)Objects.requireNonNull(getActivity())).finishEdit();
//    }

