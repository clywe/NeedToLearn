package cl1vve.englishcards;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    Context ctx = this;
    public DatabaseOperations DB = new DatabaseOperations(ctx);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = (Button) findViewById(R.id.add_word);
        Button learn = (Button) findViewById(R.id.learn_words);
        Button settings = (Button) findViewById(R.id.settings);
        final Button my_words = (Button) findViewById(R.id.my_words);
        final EditText word = (EditText) findViewById(R.id.word);
        final EditText translation = (EditText) findViewById(R.id.translation);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        LearnActivity.AutoPlay = sharedPreferences.getBoolean("AutoPlay", false);
        LearnActivity.Language = sharedPreferences.getString("Language", "British");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!"".equals(word.getText().toString())) && (!"".equals(translation.getText().toString()))) {
                    DB.putInformation(DB, DB.showLastId(DB), word.getText().toString(),
                            translation.getText().toString(), 0);
                    word.setText("");
                    translation.setText("");
                } else {
                    if ("".equals(word.getText().toString())) {
                        word.setError("Необходимо ввести слово");
                    } else {
                        translation.setError("Необходимо ввести перевод");
                    }

                }

            }
        });
        learn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Cursor all = DB.getInformation(DB);
                if (all.getCount()>0) {
                    Intent intent = new Intent(MainActivity.this, LearnActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ctx, "Сначала надо добавить слова!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        my_words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor all = DB.getInformation(DB);
                if (all.getCount()>0) {
                    Intent intent = new Intent(MainActivity.this, MyWords.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ctx, "Сначала надо добавить слова!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);
            }
        });
    }
}
