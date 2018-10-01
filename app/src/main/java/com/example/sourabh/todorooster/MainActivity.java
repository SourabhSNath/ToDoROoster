package com.example.sourabh.todorooster;

import android.content.Intent;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import static com.example.sourabh.todorooster.Utils.Constants.BACK_STACK_SHOW;

public class MainActivity extends AppCompatActivity
        implements RosterListFragment.Contract, DisplayFragment.Contract, EditFragment.Contract {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentById(android.R.id.content) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new RosterListFragment())
                    .commit();
        }
    }

    @Override
    public void showModel(ToDoModel model) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, DisplayFragment.newInstance(model))
                .addToBackStack(BACK_STACK_SHOW)
                .commit();
    }

    @Override
    public void editModel(ToDoModel model) {
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, EditFragment.newInstance(model))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void finishEdit(boolean deleted) {
        if (deleted) {
            getSupportFragmentManager().popBackStack(BACK_STACK_SHOW, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void addModel() {
        editModel(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
