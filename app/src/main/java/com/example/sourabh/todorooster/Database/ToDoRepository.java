package com.example.sourabh.todorooster.Database;

import com.example.sourabh.todorooster.ToDoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Model in MVI flow diagram, its a combination of TodoModel and the repository
 */

public class ToDoRepository {
    private List<ToDoModel> toDoModelList = new ArrayList<>();

    private static volatile ToDoRepository instance = new ToDoRepository();

    public synchronized static ToDoRepository getInstance() {
        return instance;
    }


    public List<ToDoModel> all() {
        return new ArrayList<>(toDoModelList);
    }

    public void add(ToDoModel model) {
        toDoModelList.add(model);
    }


    public void replace(ToDoModel model) {
        for (int i = 0; i < toDoModelList.size(); i++) {
            if (model.id().equals(toDoModelList.get(i).id())) { //check with the received models i one by one
                toDoModelList.set(i, model);
            }
        }
    }

    public void delete(ToDoModel model) {
        for (ToDoModel original :
                toDoModelList) {
            if (model.id().equals(original.id())) {
                toDoModelList.remove(original);
                return;
            }
        }
    }

    public ToDoModel find(String id) {
        for (ToDoModel candidate :
                all()) {
            if (candidate.id().equals(id)) {
                return candidate;
            }
        }
        return null;
    }

}


//sample data
//    public ToDoRepository() {
//        toDoModelList.add(ToDoModel.creator().description("This is a description")
//                .notes("See www.xhamster.com")
//                .isCompleted(true).build());
//        toDoModelList.add(ToDoModel.creator().description("This is another description")
//                .build());
//        toDoModelList.add(ToDoModel.creator().description("More description")
//                .notes("Create an app")
//                .build());
//    }