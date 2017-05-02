package cl1vve.englishcards;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyWords extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_words);
        update_list();
    }
    public void update_list(){
        //generate list
        ArrayList<String> list = new ArrayList<String>();
        DatabaseOperations DB = new DatabaseOperations(this);
        Cursor data = DB.getInformation(DB);
        data.moveToFirst();
        do {
            list.add(data.getString(1) + '\n'+ data.getString(2));
        }
        while (data.moveToNext());

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);
        adapter.notifyDataSetChanged();
        ListView lView = (ListView)findViewById(R.id.words_in_list);
        lView.setAdapter(adapter);
    }
}
