package com.example.sourabh.todorooster;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sourabh.todorooster.databinding.TodoRowBinding;

public class RowViewHolder extends RecyclerView.ViewHolder {

    private final TodoRowBinding binding;

    private final ToDoAdapter adapter;

    public RowViewHolder(TodoRowBinding binding, ToDoAdapter adapter) {
        super(binding.getRoot());  //get root brings in the root widget, here the constraint layout
        this.binding=binding;
        this.adapter=adapter;    //get the adapter since this class of viewholder doesnt have access to it
    }

    /*

    Call setModel(), to tell the data binding framework to take this particular
    ToDoModel and update the widgets from that
    Call executePendingBindings(), as we want the data binding framework to
    evaluate its binding expressions now, not sometime later.

     */

    void bind(ToDoModel model){
        binding.setModel(model);
        binding.setHolder(this);
        binding.executePendingBindings();
    }

    public void completeChanged(ToDoModel model, boolean isChecked){
        if (model.isCompleted()!=isChecked){
            adapter.replace(model,isChecked);
        }
    }

    public void onClick(){
        adapter.showModel(binding.getModel());
    }
}
