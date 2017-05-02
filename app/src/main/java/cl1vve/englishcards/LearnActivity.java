package cl1vve.englishcards;

import android.content.Context;
import android.database.Cursor;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class LearnActivity extends AppCompatActivity {
    public static String Language;
    public static boolean AutoPlay;
    String t_word, t_tr;
    TextView word, translation;
    Cursor all;
    ImageButton eng;
    int amount_of_words = 0;
    int f = 0, flag = 0, number_of_used_words; //flag = 1, если выводим перевод
    TextToSpeech t1;
    Set<Integer> generated;
    ArrayList<Integer> numbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);
        word = (TextView) findViewById(R.id.learn_word);
        translation = (TextView) findViewById(R.id.learn_translation);
        final Button show = (Button) findViewById(R.id.show);
        eng = (ImageButton) findViewById(R.id.voice_eng);
        if (Language == null){
            Language = "British";
        }
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    switch (Language) {
                        case "German":
                            t1.setLanguage(Locale.GERMANY);
                            break;
                        case "American":
                            t1.setLanguage(Locale.US);
                            break;
                        case "British":
                            t1.setLanguage(Locale.UK);
                            break;
                    }
                }
                if ((AutoPlay) && (!"".equals(word.getText().toString())))
                    t1.speak(t_word, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        DatabaseOperations DB = new DatabaseOperations(this);
        all = DB.getInformation(DB);
        amount_of_words = all.getCount();
        generate_random_numbers();
        update(all);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (f == 0) {
                    if (flag == 0) {
                        translation.setText(t_tr);
                    } else {
                        word.setText(t_word);
                        eng.setVisibility(View.VISIBLE);
                        if (AutoPlay){
                            t1.speak(t_word, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    f = 1;
                    show.setText("Дальше");
                } else {
                    f = 0;
                    show.setText("Показать");
                    update(all);
                }
            }
        });
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(t_word, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }
    public void generate_random_numbers(){
        number_of_used_words = 0;
        numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < amount_of_words) {
            int random = randomGenerator .nextInt(amount_of_words);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
    }
    public void update(Cursor CR){
        CR.moveToFirst();
        CR.move(numbers.get(number_of_used_words));
        t_word = CR.getString(1);
        t_tr = CR.getString(2);
        number_of_used_words++;
        if (number_of_used_words == amount_of_words) {
            generate_random_numbers();
        }
        Random randomGenerator1 = new Random();
        flag = randomGenerator1.nextInt(2);

        if (flag == 1) {
            translation.setText(t_tr);
            word.setText("");
            eng.setVisibility(View.INVISIBLE);

        } else {
            word.setText(t_word);
            translation.setText("");
            eng.setVisibility(View.VISIBLE);
            if (AutoPlay){
                t1.speak(t_word, TextToSpeech.QUEUE_FLUSH, null);
            }

        }
    }
}
