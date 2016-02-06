package com.example.caryn.xrcise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    HashMap<String, String> singular = new HashMap<String, String>();
    HashMap<String, String> plural = new HashMap<String, String>();
    HashMap<String, String> type = new HashMap<String, String>();
    HashMap<String, Double> perHunCalorie = new HashMap<String, Double>();
    String top_ex = "walking";
    String bottom_ex = "walking";
    Integer editing = 0; //1=top 2=bottom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top_ex = extras.getString("top_ex");
            bottom_ex = extras.getString("bottom_ex");
        }
        setUp();

        Button one = (Button) findViewById(R.id.top_button);
        one.setOnClickListener(this); // calling onClick() method
        Button two = (Button) findViewById(R.id.bottom_button);
        two.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button:
                editing = 1;
                break;
            case R.id.bottom_button:
                editing = 2;
                break;
        }

        Intent secondActivity = new Intent(this, Select_Activity.class);
        secondActivity.putExtra("top_ex",top_ex);
        secondActivity.putExtra("bottom_ex", bottom_ex);
        secondActivity.putExtra("editing", editing);
        startActivity(secondActivity);
    }

    double convertToCalories(String exercise, double num) {
        Double perCalorieVal = 100.00 / perHunCalorie.get(exercise);
        return num * perCalorieVal;
    }

     double calorieToAmount(double cals, String exercise) {
        Double perCalorieVal = 100.00 / perHunCalorie.get(exercise);
        return cals / perCalorieVal;
    }

    String correctPlurality(int amount, String exercise) {
        if (amount == 1) {
            return singular.get(exercise);
        } else {
            return plural.get(exercise);
        }
    }

    String type(String exercise) {
        return type.get(exercise);
    }

    void setUp() {
        String[] exercises = new String[]{"pushup", "situp", "squats", "leg-lift", "plank", "jumping jacks", "pullup", "cycling", "walking", "jogging", "swimming", "stair-climbing"};
        String[] singulars = new String[]{"rep", "rep", "rep", "min", "min", "min", "rep", "min", "min", "min", "min", "min"};
        String[] plurals = new String[]{"reps", "reps", "reps", "mins", "mins", "mins", "reps", "mins", "mins", "mins", "mins", "mins"};
        Double[] amounts = new Double[]{350.00, 200.00, 225.00, 25.00, 25.00, 10.00, 100.00, 12.00, 20.00, 12.00, 13.00, 15.00};
        String[] types = new String[]{"arms", "core", "legs", "legs", "core", "cardio", "arms", "cardio", "cardio", "cardio", "cardio", "cardio"};
        for (int i = 0; i < exercises.length; i++) {
            singular.put(exercises[i], singulars[i]);
            plural.put(exercises[i], plurals[i]);
            type.put(exercises[i], types[i]);
            perHunCalorie.put(exercises[i], amounts[i]);
        }

        changeTop(top_ex, -1);
        updateTop(top_ex, -1);
        changeBottom(bottom_ex);
        updateBottom(bottom_ex, -1);
        updateCals(-1);
    }

    void changeTop(String exercise, int calories) {

        //get the exercise text
        TextView exercise_text = (TextView) findViewById(R.id.top_exercise);
        exercise_text.setText(exercise);

        //get the exercise type text
        TextView exercise_type = (TextView) findViewById(R.id.top_exercise_type);
        String t = type(exercise);
        exercise_type.setText(t);

//      //get the text box
        TextView input_box = (TextView)findViewById(R.id.top_reps_minutes);
        double amount = calorieToAmount(calories, exercise);
        input_box.setText(String.valueOf((int) Math.round(amount)));

        //get the units text
        TextView units = (TextView) findViewById(R.id.top_info_string);
        units.setText(correctPlurality((int) Math.round(amount), exercise));

         ImageView img= (ImageView) findViewById(R.id.top_image);
         if (t.equals("core")) {
             img.setImageResource(R.mipmap.core);
         } else if (t.equals("arms")) {
             img.setImageResource(R.mipmap.arms);
         } else if (t.equals("legs")) {
             img.setImageResource(R.mipmap.legs);
         } else {
             img.setImageResource(R.mipmap.cardio);
         }

    }

    void changeBottom(String exercise) {
        TextView exercise_text = (TextView) findViewById(R.id.bottom_exercise);
        exercise_text.setText(exercise);

        TextView exercise_type= (TextView)findViewById(R.id.bottom_type_exercise);
        String t = type(exercise);
        exercise_type.setText(t);

        ImageView img= (ImageView) findViewById(R.id.bottom_image);
        if (t.equals("core")) {
            img.setImageResource(R.mipmap.core);
        } else if (t.equals("arms")) {
            img.setImageResource(R.mipmap.arms);
        } else if (t.equals("legs")) {
            img.setImageResource(R.mipmap.legs);
        } else {
            img.setImageResource(R.mipmap.cardio);
        }
    }

    void updateCals(double cals) {
        TextView calories = (TextView) findViewById(R.id.enter_calories);
        if (cals == -1) {
            calories.setText("");
        } else {
            calories.setText(String.valueOf((int) Math.round(cals)));
        }
    }

    void updateBottom(String exercise, double calories){
        TextView result= (TextView)findViewById(R.id.bottom_result);
        if (calories == -1) {
            result.setText("");
        } else {
            double amount = calorieToAmount(calories, exercise);
            int amt = (int) Math.round(amount);
            String plural = correctPlurality(amt, exercise);
            result.setText(String.valueOf(amt) + " " + plural);
        }
    }

    void updateTop(String exercise, double calories){
        TextView result= (TextView)findViewById(R.id.top_reps_minutes);
        if (calories ==-1) {
            result.setText("");
        }else {
            TextView label = (TextView) findViewById(R.id.top_info_string);
            double amount = calorieToAmount(calories, exercise);
            int amt = (int) Math.round(amount);
            String plural = correctPlurality(amt, exercise);
            result.setText(String.valueOf(amt));
            label.setText(plural);
        }
    }

    public void convert(View v) {
        //add case where the two activities are the same

        EditText top_text = (EditText)findViewById(R.id.top_reps_minutes);
        String text = top_text.getText().toString();
        Integer i = Integer.parseInt(text);
        double calories = convertToCalories(top_ex, (double)i);
        updateBottom(bottom_ex, calories);
        updateCals(calories);

    }

    public void calculate(View v) {
        EditText cal_text = (EditText) findViewById(R.id.enter_calories);
        String cals = cal_text.getText().toString();
        Integer calories = Integer.parseInt(cals);
        updateBottom(bottom_ex, calories);
        updateTop(top_ex, calories);
    }
}
