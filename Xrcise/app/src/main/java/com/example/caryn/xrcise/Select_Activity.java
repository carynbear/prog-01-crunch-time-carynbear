package com.example.caryn.xrcise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.regex.Pattern;

public class Select_Activity extends AppCompatActivity {

    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_);
        listView = (ListView) findViewById(R.id.list);

        Bundle extras = getIntent().getExtras();
        final String top_ex = extras.getString("top_ex");
        final String bottom_ex = extras.getString("bottom_ex");
        final int editing = extras.getInt("editing");


        String[] values = new String[] {
                "pullup (arms)",
                "pushup (arms)",
                "cycling (cardio)",
                "jogging (cardio)",
                "jumping jacks (cardio)",
                "stair-climbing (cardio)",
                "swimming (cardio)",
                "walking (cardio)",
                "plank (core)",
                "situp (core)",
                "leg-lift (legs)",
                "squats (legs)"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                String[] parts = itemValue.split(Pattern.quote(" "));
                String newSelection = parts[0];
                if (newSelection.equals("jumping")){
                    newSelection = "jumping jacks";
                }

                Intent firstActivity = new Intent(view.getContext(), MainActivity.class);
                if (editing == 1) {
                    firstActivity.putExtra("top_ex", newSelection);
                    firstActivity.putExtra("bottom_ex", bottom_ex);
                } else {
                    firstActivity.putExtra("top_ex", top_ex);
                    firstActivity.putExtra("bottom_ex", newSelection);
                }
                startActivity(firstActivity);


            }

        });

    }
}
