package cl1vve.englishcards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Card extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        TextView a = (TextView) findViewById(R.id.set_name);
        Intent intent = getIntent();

        String set = intent.getStringExtra("chosen_set");
        a.setText(set);
    }
}
