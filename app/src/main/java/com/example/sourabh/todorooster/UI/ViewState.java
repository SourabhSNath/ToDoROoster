package com.example.sourabh.todorooster.UI;

import androidx.annotation.Nullable;

import com.example.sourabh.todorooster.ToDoModel;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AutoValue
public abstract class ViewState {

    public abstract List<ToDoModel> items();

    @Nullable
    public abstract ToDoModel current();

    public static Builder builder() {
        return new AutoValue_ViewState.Builder();
    }

    public static Builder empty() {
        return builder().items(Collections.unmodifiableList(new ArrayList<>()));
    }

    public Builder toBuilder() {
        return builder().items(items()).current(current());
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder items(List<ToDoModel> items);

        public abstract Builder current(ToDoModel current);

        public abstract ViewState build();
    }

    //Create a new ViewState with the revised list (or, in reality, an unmodifiable
    //list that wraps the revised list
    //use this add method in ViewModel
    public ViewState add(ToDoModel model) {
        List<ToDoModel> models = new ArrayList<>(items());
        models.add(model);

        sort(models);

        return toBuilder().items(Collections.unmodifiableList(models)).current(model).build();
    }

    private void sort(List<ToDoModel> models) {
        Collections.sort(models, ToDoModel.SORT_BY_DESC);
    }

    public ViewState modify(ToDoModel model) {
        List<ToDoModel> models = new ArrayList<>(items());
        ToDoModel original = find(models, model.id());
        if (original != null) {
            int index = models.indexOf(original);
            models.set(index, model);
        }
        sort(models);
        return toBuilder().items(Collections.unmodifiableList(models)).current(model).build();
    }


    ViewState delete(ToDoModel model) {
        List<ToDoModel> models = new ArrayList<>(items());
        ToDoModel original = find(models, model.id());
        if (original == null) {
            throw new IllegalArgumentException("Cannot find model to delete: " + model.toString());
        } else {
            models.remove(original);
        }

        sort(models);

        return toBuilder()
                .items(Collections.unmodifiableList(models))
                .current(null)
                .build();

    }


    private ToDoModel find(List<ToDoModel> models, String id) {
        int position = findPosition(models, id);
        return position >= 0 ? models.get(position) : null;
    }

    private int findPosition(List<ToDoModel> models, String id) {
        for (int i = 0; i < models.size(); i++) {
            ToDoModel candidate = models.get(i);
            if (id.equals(candidate.id())) {
                return i;
            }
        }
        return -1;
    }
}