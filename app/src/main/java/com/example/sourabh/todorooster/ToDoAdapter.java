package com.example.sourabh.todorooster;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.sourabh.todorooster.Database.ToDoRepository;
import com.example.sourabh.todorooster.UI.ViewState;
import com.example.sourabh.todorooster.databinding.TodoRowBinding;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<RowViewHolder> {
    private List<ToDoModel>models;
    private final RosterListFragment host;

   // private final ToDoRepository repository;

    public ToDoAdapter(RosterListFragment host) {
     //   this.repository=ToDoRepository.getInstance();
        this.host=host;
    }


    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*we are using the getLayoutInflator and host to setup ToDORowBinding

        (some container classes, like RelativeLayout, really need to know their parent in
        order to work properly, so we use this standard recipe for calling inflate())
         */

        TodoRowBinding binding =TodoRowBinding.inflate(host.getLayoutInflater(),viewGroup,false);
        return new RowViewHolder(binding,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder rowViewHolder, int position) {
        rowViewHolder.bind(models.get(position));

    }

    @Override
    public int getItemCount() {
        return models==null? 0: models.size();  //if we have not received the viewstate yet
    }

    public void replace(ToDoModel model, boolean isChecked) {
       // repository.replace(model.toBuilder().isCompleted(isChecked).build());
        host.replace(model.toBuilder().isCompleted(isChecked).build());
    }

    /*
    Call getActivity() on the RosterListFragment
    Cast that to the Contract interface
    Call showModel() on the Contract, passing along a ToDoModel to be displayed
     */
    void showModel(ToDoModel model){
        ((RosterListFragment.Contract)host.getActivity()).showModel(model);  //getting model for rowViewHolder and sending it to mainActivity
    }

    void setState(ViewState state){
        models=state.items();
        notifyDataSetChanged();
    }
}
