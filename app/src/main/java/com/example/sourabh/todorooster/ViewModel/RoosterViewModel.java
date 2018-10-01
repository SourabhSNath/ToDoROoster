package com.example.sourabh.todorooster.ViewModel;

import android.app.Application;

import com.example.sourabh.todorooster.Controller.Controller;
import com.example.sourabh.todorooster.Database.ToDoRepository;
import com.example.sourabh.todorooster.Intents.Action;
import com.example.sourabh.todorooster.Intents.Result;
import com.example.sourabh.todorooster.UI.ViewState;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.BackpressureStrategy;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * This is the Reducer in MVI here
 */
public class RoosterViewModel extends AndroidViewModel {

    //   private final MutableLiveData<ViewState> states=new MutableLiveData<>(); commenting after adding reactivex live data bridge component
    private final LiveData<ViewState> states;

    private ViewState lastState = ViewState.empty().build();
    /**
     * A ReplaySubject is like a PublishSubject, except that it caches objects that flow
     * through it. In our case, we ask it to cache the latest one, via the createWithSize(1)
     * factory method.
     */
    private final ReplaySubject<ViewState> stateSubject = ReplaySubject.createWithSize(1);

    public RoosterViewModel(@NonNull Application application) {
        super(application);

//        ViewState initial = ViewState.builder().items(ToDoRepository.getInstance().all()).build();
//        states.postValue(initial);  //no longer needed, controller takes control

        Controller controller = new Controller();

        controller.resultStream()
                .subscribe(result -> {
                    lastState = foldResultIntoState(lastState, result);
                    stateSubject.onNext(lastState);
                }, stateSubject::onError);

        states = LiveDataReactiveStreams.fromPublisher(stateSubject.toFlowable(BackpressureStrategy.LATEST));

        controller.subscribeToActions(actionPublishSubject);

    }

    public LiveData<ViewState> stateStream() {
        return states;
    }

    public final PublishSubject<Action> actionPublishSubject = PublishSubject.create();  //subject is an object that serves as source of events

    //Our fragments will use this process() method to push an Action over to the
    //RosterViewModel. onNext() is how you emit another object onto the stream of the
    //Subject
    public void process(Action action) {
        actionPublishSubject.onNext(action);
    }

    //using add from ViewState
    private ViewState foldResultIntoState(@NonNull ViewState state, @NonNull Result result) throws Exception {
        if (result instanceof Result.Added) {
            return state.add(((Result.Added) result).model());
        } else {
            throw new IllegalStateException("Unexpected Result Type: " + result.toString());
        }
    }

}
