package com.example.sourabh.todorooster;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

import java.util.Calendar;
import java.util.Comparator;
import java.util.UUID;

@AutoValue
public abstract class ToDoModel {

    public abstract String id();

    public abstract boolean isCompleted();
    //check if task completed

    public abstract String description();

    @Nullable
    public abstract String notes();  //some notes in case there is more info

    public abstract Calendar createdOn();


    public static Builder builder() {
        return new com.example.sourabh.todorooster.AutoValue_ToDoModel.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract ToDoModel build();

        //abstract setter methods
        //id and created on are package-private
        abstract Builder id(String id);

        public abstract Builder description(String desc);

        public abstract Builder notes(String notes);

        public abstract Builder isCompleted(boolean isCompleted);

        abstract Builder createdOn(Calendar date);

        /*
          The id() and createdOn() properties are distinctive, though, in that we do not want
          new values for those to be created on a whim. Instead, we want to:
          •Use new values when creating a logically new instance of a to-do item, and
          •Later, use values loaded from a database, as we load in existing to-do items

          To simplify this a bit, we can offer a creator() method, separate from the builder()
          method, that fills in new ID and created-on values. We would use creator() when
          we are creating a new-to-the-universe to-do list item, and use builder() for other cases.

         */
    }

     static Builder creator() {
        return builder()
                .isCompleted(false)
                .id(UUID.randomUUID().toString())
                .createdOn(Calendar.getInstance());
    }

    /*
    To create a replacement ToDoModel, it would be helpful if we could get a Builder
that is pre-populated with the existing values for the description, notes, and so
forth. One pattern for this is to have a toBuilder() method that converts an existing
object into a Builder to create an identical object. Then, we can just call methods
on the Builder for the individual things that we want to change, such as the
description.

     */

     Builder toBuilder(){
        return builder()
                .id(id())
                .isCompleted(isCompleted())
                .description(description())
                .notes(notes())
                .createdOn(createdOn());
    }


    public static final Comparator<ToDoModel> SORT_BY_DESC =
            (one,two) -> (one.description().compareTo(two.description())); //next add this to ViewState

}
