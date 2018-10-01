package com.example.sourabh.todorooster;

import androidx.test.runner.AndroidJUnit4;

import com.example.sourabh.todorooster.Database.ToDoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class RepositoryInstrumentedTest {
    private ToDoRepository repository;


    @Before
    public void setup() {
        repository = new ToDoRepository();

        repository.add(ToDoModel.creator()
                .description("Buy a copy of _Exploring Android_")
                .notes("See https://wares.commonsware.com")
                .isCompleted(true)
                .build());
        repository.add(ToDoModel.creator()
                .description("Complete all of the tutorials")
                .build());
        repository.add(ToDoModel.creator()
                .description("Write an app for somebody in my community")
                .notes("Talk to some people at non-profit organizations to see what they need!")
                .build());

    }

    @Test
    public void getAll() {
        assertEquals(3, repository.all().size());
    }

    @Test
    public void add() {
        ToDoModel model = ToDoModel.creator().isCompleted(true).description("Fu").build();

        repository.add(model);
        List<ToDoModel> models = repository.all();

        assertEquals(4, models.size());
        assertThat(models, hasItem(model));  //matchers hasItem import
    }

    /*
 Grab the second item out of the list of to-do items
 Create a modified edition of that item
 replace() the original item with the modified item
 Assert that we still have three model objects in the repo and that the second
 item in the revised list is the modified item
  */
    @Test
    public void replace() {
        ToDoModel original = repository.all().get(1);
        ToDoModel editied = original.toBuilder().isCompleted(true).description("FU").build();
        repository.replace(editied);
        assertEquals(3, repository.all().size());
        assertSame(editied, repository.all().get(1));

    }

    @Test
    public void delete(){
        assertEquals(3,repository.all().size());
        repository.delete(repository.all().get(0));
        assertEquals(2,repository.all().size());
        repository.delete(repository.all().get(0).toBuilder().build()); //Create an identical instance of the first one and delete that one
        assertEquals(1,repository.all().size());
    }
}
