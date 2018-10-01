package com.example.sourabh.todorooster.Controller;

import com.example.sourabh.todorooster.Database.ToDoRepository;
import com.example.sourabh.todorooster.Intents.Action;
import com.example.sourabh.todorooster.Intents.Result;
import com.example.sourabh.todorooster.ToDoModel;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Responsible for responding to actions published by the views and also updating the Model in response
 */

public class Controller {
    private final ToDoRepository repository = ToDoRepository.getInstance();

    public void processImpl(Action action) { //process the actions, using the methods declared below

        if (action instanceof Action.Add) {
            add(((Action.Add) action).model());
        } else if (action instanceof Action.Edit) {
            modify(((Action.Edit) action).model());
        } else if (action instanceof Action.Delete) {
            delete(((Action.Delete) action).model());
        }

    }
    /**
     * Using RxJava, to get the actions published by the fragment and passing it to processImpl()
     * <p>
     * RxJava Observable publishes a stream of whatever type you declare
     * <p>
     * observeOn() says what thread we want to use to receive the objects. In this
     * case, we use a thread provided to use by RxJava itself, called
     * Schedulers.io(), that is designed for background I/O work.
     * <p>
     * subscribe() says where and how we want to receive the objects themselves
     * that are published to this stream. In this case, we use a Java 8 method
     * reference to point to the processImpl() method.
     */
    public void subscribeToActions(Observable<Action> actionStream) {
        actionStream.observeOn(Schedulers.io())
                .subscribe(this::processImpl);
    }

    private void add(ToDoModel model) {
        repository.add(model);
        resultSubject.onNext(Result.added(model));
    }

    private void modify(ToDoModel model) {
        repository.replace(model);
        resultSubject.onNext(Result.modified(model));
    }

    private void delete(ToDoModel toDelete) {
        repository.delete(toDelete);
        resultSubject.onNext(Result.deleted(toDelete));
    }

    private final PublishSubject<Result> resultSubject =
            PublishSubject.create();

    //To let subscribers have access to resultSubject
    public Observable<Result> resultStream() {
        return resultSubject;
    }


}
