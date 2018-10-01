package com.example.sourabh.todorooster;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sourabh.todorooster.Intents.Action;
import com.example.sourabh.todorooster.UI.ViewState;
import com.example.sourabh.todorooster.ViewModel.RoosterViewModel;

import java.util.Objects;

/**
 * Views in MVI are the fragments here
 */

public class RosterListFragment extends Fragment {
    private RoosterViewModel viewModel;
    private RecyclerView recyclerView;
    private View empty;
    private ToDoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.todo_roster,container,false);

        setHasOptionsMenu(true);

        recyclerView=view.findViewById(R.id.items);
        empty =view.findViewById(R.id.emptyText);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);

        adapter=new ToDoAdapter(this);

        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);

        viewModel.stateStream().observe(getViewLifecycleOwner(),this::render);  //viewState handed to render method

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_rooster,menu);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.add){
            ((Contract)getActivity()).addModel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface Contract{
        void showModel(ToDoModel model);
        void addModel();
    }

    public void render(ViewState state){  //pass state to the adapter
        adapter.setState(state);
        if (recyclerView.getAdapter().getItemCount()>0){
            empty.setVisibility(View.GONE);
        }
    }

    public void replace(ToDoModel model){
        viewModel.process(Action.edit(model));
    }
}
