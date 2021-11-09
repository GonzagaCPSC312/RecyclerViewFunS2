package com.sprint.gina.recyclerviewfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivityTag";
    List<Book> books; // data source for our recyclerview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // warmup
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        books = new ArrayList<>();
        books.add(new Book("The Sorcerer's Stone", "JK Rowling", 111));
        books.add(new Book("The Chamber of Secrets", "JK Rowling", 222));
        books.add(new Book("The Prisoner of Azkaban", "JK Rowling", 333));
        books.add(new Book("The Goblet of Fire", "JK Rowling", 444));

        // AdapterView and RecyclerView: a ViewGroup that gets its child views from an adapter
        // Adapter: a middleman between an AdapterView/RecyclerView and its data source
        // creates one child view for each item in the data source
        // Data source: static (e.g. a fixed array that doesn't change)
        // or dynamic (e.g. a list that grows or shrinks in size, or a database cursor)

        // Examples of AdapterViews: Spinner, ListView, GridView, ...
        // AdapterView vs RecyclerView
        // RecyclerView is like AdapterView 2.0
        // it is "more performant" because it recycles its views

        // game plan for setting up a recyclerview
        // 1. set up the layout manager
        // few examples: LinearLayoutManager, GridLayoutManager, StaggeredGridLayoutManager,...
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // 2. set up a custom adapter
        // 2.A. set up a custom RecyclerView.Adapter class by subclassing the abstract class
        // 2.B. set up a custom RecyclerView.ViewHolder class by subclassing the abstract class
        // 2.C. wire it all up!
        CustomAdapter adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);

        // 3. set up click listeners (plus alert dialogs)
        // normal click and long click

        // 4. make it all look better

        // 5. demo of our dynamic data source
        // lets say we are going to remove an item from our data source
        // we need to "notify" our adapter so it can force a refresh of the
        // recycler view
        // lets kick this off in 5 seconds
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // remove the chamber of secrets
//                books.remove(1);
//                // let the adapter know!!
//                adapter.notifyItemRemoved(1);
//            }
//        }, 5000);
    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
        // we are going to set up a contextual action mode (CAM) that the user
        // can enter on long click of an item
        // once in CAM, the user can select multiple items and do a multi-item delete
        boolean multiSelect = false;
        ActionMode actionMode;
        ActionMode.Callback callbacks;
        List<Book> selectedItems = new ArrayList<>();

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
//            TextView text1;
            CardView myCardView1;
            TextView myText1;
            ImageView myImage1;
            public CustomViewHolder(@NonNull View itemView) {
                super(itemView);

//                text1 = itemView.findViewById(android.R.id.text1);
                myCardView1 = itemView.findViewById(R.id.myCardView1);
                myText1 = itemView.findViewById(R.id.myText1);
                myImage1 = itemView.findViewById(R.id.myImage1);

                // wire 'em up
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public void updateView(Book b) {
//                text1.setText(b.toString());
                myText1.setText(b.toString());
                myImage1.setImageResource(R.drawable.placeholderimage);
            }

            public void selectItem(Book b) {
                // called when the user clicks on an item
                if (multiSelect) {
                    if (selectedItems.contains(b)) {
                        // item is already selected, deselect it
                        selectedItems.remove(b);
                    }
                    else {
                        // item is not selected, select it
                        selectedItems.add(b);
                    }
                    // update the title to show the number of selected items
                    actionMode.setTitle(selectedItems.size() + " item(s) selected");
                }
            }

            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                selectItem(books.get(getAdapterPosition()));
            }

            @Override
            public boolean onLongClick(View v) {
                Log.d(TAG, "onLongClick: ");
                // suppose on long click we want to show an alert dialog
                // use an AlertDialog.Builder object and method chaining to build an alert dialog
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("Item Long Clicked")
//                        .setMessage("You long clicked on an item")
//                        .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // executes when the user presses "Okay"
//                                Toast.makeText(MainActivity.this, "OKAY", Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNegativeButton("Dismiss", null);
//                builder.show();
                MainActivity.this.startActionMode(callbacks);
                selectItem(books.get(getAdapterPosition()));
                return true; // false means this event handler did not handle or "consume" the event
            }
        }

        public CustomAdapter() {
            super();
            callbacks = new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    // this executes when the CAM is first attempted to be created
                    // return true to enter CAM, false to not
                    multiSelect = true;
                    actionMode = mode;
                    // next, we inflate our cam_menu
                    MenuInflater menuInflater = getMenuInflater();
                    menuInflater.inflate(R.menu.cam_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    // figure out which item was clicked
                    switch (item.getItemId()) {
                        case R.id.deleteMenuItem:
                            Toast.makeText(MainActivity.this, "TODO: delete selected items", Toast.LENGTH_SHORT).show();
                            // TODO: delete selected items
                            mode.finish(); // exit CAM
                            return true;
                    }
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    // called when exiting CAM
                    multiSelect = false;
                }
            };
        }

        @NonNull
        @Override
        public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // a few options for setting up a layout for custom views
            // 1. use standard layout template from Android
//            View view = LayoutInflater.from(MainActivity.this)
//                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            // 2. use our own custom layout (e.g. a new XML layout file)
            View view = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.card_view_list_item, parent, false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
            // get the Book at position then pass it to our updateView() method for displaying
            Book b = books.get(position);
            holder.updateView(b);
        }

        @Override
        public int getItemCount() {
            return books.size();
        }
    }
}