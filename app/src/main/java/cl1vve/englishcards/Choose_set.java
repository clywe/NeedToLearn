package cl1vve.englishcards;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Choose_set extends AppCompatActivity {
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayList<String> listItems1 = new ArrayList<String>();
    ArrayAdapter<String> adapter,adapter1;
    String[] sets = {"Цвета","Популярные глаголы","Популярные существительные",
    "Популярные прилагательные", "Фразовые глаголы"};
    Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_set);

        DatabaseOperations DB = new DatabaseOperations(ctx);
        DB.putInformation(DB, 0, "test_set","test_word","test_trans","test_examples",0,0);
        Cursor CR = DB.getInformation(DB);
        CR.moveToFirst();


        final ListView MySets = (ListView) findViewById(R.id.my_sets);
        final ListView AllSets = (ListView) findViewById(R.id.all_sets);
        for(int i =  0; i < sets.length; i++){
            listItems.add(sets[i]);
        }
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        AllSets.setAdapter(adapter);
        AllSets.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String set = (String) AllSets.getItemAtPosition(position);
                Intent intent = new Intent(Choose_set.this, Card.class);
                intent.putExtra("chosen_set", set);
                startActivity(intent);
            }
        });
        adapter1=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems1);
        AllSets.setAdapter(adapter);
        MySets.setAdapter(adapter1);
        MySets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                String set = (String) MySets.getItemAtPosition(position);
                Intent intent = new Intent(Choose_set.this, Card.class);
                intent.putExtra("my_chosen_set", set);
                startActivity(intent);
            }
        });
    }
}
