package com.example.sourabh.todorooster.Intents;

import com.example.sourabh.todorooster.ToDoModel;
import com.google.auto.value.AutoValue;

/**
 * Intent
 */

public abstract class Action {

    //Auto generate actions

    @AutoValue
    public static abstract class Add extends Action {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Edit extends Action {
        public abstract ToDoModel model();
    }

    @AutoValue
    public static abstract class Delete extends Action {
        public abstract ToDoModel model();
    }

    @AutoValue
    static abstract class Show extends Action {
        public abstract ToDoModel current();
    }

    public static class Load extends Action {
    }

    //create factory methods to get a better name than for example AutoValue_Action_Add

    //So, now, to create an Action.Add instance, we just call Action.add(), supplying the
    //ToDoModel representing the item to be added.

    public static Action add(ToDoModel model) {
        return new AutoValue_Action_Add(model);
    }

    public static Action edit(ToDoModel model) {
        return new AutoValue_Action_Edit(model);
    }

    public static Action delete(ToDoModel model) {
        return new AutoValue_Action_Delete(model);
    }

    public static Action show(ToDoModel model) {
        return new AutoValue_Action_Show(model);
    }

    public static Action load() {
        return new Action.Load();
    }


}
