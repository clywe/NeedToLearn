package cl1vve.englishcards;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_list, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton)view.findViewById(R.id.delete_btn);
        ImageButton editBtn = (ImageButton)view.findViewById(R.id.edit_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String[] words = list.get(position).split("\n");
                DatabaseOperations DB = new DatabaseOperations(context);
                DB.deleteRow(DB, words[0],words[1]);
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.edit_dialog);
                final EditText word = (EditText) dialog.findViewById(R.id.editText2);
                final EditText translation = (EditText) dialog.findViewById(R.id.editText);
                final String[] words = list.get(position).split("\n");
                word.setText(words[0]);
                translation.setText(words[1]);
                dialog.show();
                Button dialog_edit_button = (Button) dialog.findViewById(R.id.dialog_edit_button);
                dialog_edit_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ((!"".equals(word.getText().toString())) && (!"".equals(translation.getText().toString()))) {
                            DatabaseOperations DB = new DatabaseOperations(context);
                            DB.updateDatabase(DB, words[0], words[1], word.getText().toString(),
                                    translation.getText().toString());
                            list.remove(position);
                            list.add(position, word.getText().toString() + '\n' + translation.getText().toString());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Введите и слово, и перевод",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}