package cl1vve.englishcards;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import static cl1vve.englishcards.R.id.parent;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final CheckBox autoplay = (CheckBox) findViewById(R.id.checkBox);

        if (LearnActivity.AutoPlay == true){
            autoplay.setChecked(true);
            editor.putBoolean("AutoPlay", true);
        }
        autoplay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autoplay.isChecked()){
                    LearnActivity.AutoPlay = true;
                    editor.putBoolean("AutoPlay", true);
                } else {
                    editor.putBoolean("AutoPlay", false);
                    LearnActivity.AutoPlay = false;
                }
                editor.commit();
            }
        });
        editor.commit();
        final Spinner spinner = (Spinner) findViewById(R.id.LanguageSpinner);
        if (LearnActivity.Language == null){
            LearnActivity.Language = "British";
        }
        switch (LearnActivity.Language) {
            case "German":
                spinner.setSelection(2);
                break;
            case "American":
                spinner.setSelection(1);
                break;
            case "British":
                spinner.setSelection(0);
                break;
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = spinner.getSelectedItem().toString();

                switch (selected) {
                    case "Немецкий":
                        LearnActivity.Language = "German";
                        editor.putString("Language", "German");
                        break;
                    case "Американский английский":
                        LearnActivity.Language = "American";
                        editor.putString("Language", "American");
                        break;
                    case "Британский английский":
                        LearnActivity.Language = "British";
                        editor.putString("Language", "British");
                        break;
                }
                editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
