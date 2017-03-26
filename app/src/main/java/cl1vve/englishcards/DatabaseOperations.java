package cl1vve.englishcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by CL1VVE on 26.03.2017.
 */

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_NAME +
            "(" + TableData.TableInfo.SET_ID + " INTEGER," +
            TableData.TableInfo.NAME_OF_SET + " TEXT," +
            TableData.TableInfo.WORD + " TEXT," +
            TableData.TableInfo.TRANSLATION + " TEXT," +
            TableData.TableInfo.EXAMPLES + " TEXT," +
            TableData.TableInfo.ADDITIONAL_INFO + " INTEGER," +
            TableData.TableInfo.ADDITIONAL_INFO2 + " INTEGER);";
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
        Log.d("Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
        Log.d("Operations", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void putInformation(DatabaseOperations dop, int set_id, String set_name,
                               String word, String translation, String examples,
                               int word_info, int word_info2){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.SET_ID, set_id);
        cv.put(TableData.TableInfo.NAME_OF_SET, set_name);
        cv.put(TableData.TableInfo.WORD, word);
        cv.put(TableData.TableInfo.TRANSLATION, translation);
        cv.put(TableData.TableInfo.EXAMPLES, examples);
        cv.put(TableData.TableInfo.ADDITIONAL_INFO, word_info);
        cv.put(TableData.TableInfo.ADDITIONAL_INFO2, word_info2);
        long k = SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Operations", "One raw inserted");
        /*
        Context ctx = this;
        DatabaseOperations DB = new DatabaseOperations(ctx);
        DB.putInformation(DB, 0, "test","test","test","test",0,0);
         */
    }

    public Cursor getInformation(DatabaseOperations dop){
        SQLiteDatabase SQ = dop. getReadableDatabase();
        String[] coloumns = {TableData.TableInfo.SET_ID, TableData.TableInfo.NAME_OF_SET,
                TableData.TableInfo.WORD, TableData.TableInfo.TRANSLATION,
                TableData.TableInfo.EXAMPLES, TableData.TableInfo.ADDITIONAL_INFO,
                TableData.TableInfo.ADDITIONAL_INFO2};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, coloumns, null, null, null, null, null);
        return CR;
        /*
        Context ctx = this;
        DatabaseOperations DB = new DatabaseOperations(ctx);
        Cursor CR = DB.getInformation(DB);
        CR.moveToFirst();
        do{
            Log.d("db111",CR.getString(0) + '-'+CR.getString(1) + '-'+CR.getString(2)
                    + '-'+CR.getString(3) + '-'+CR.getString(4) + '-'+CR.getString(5)
                    + '-'+CR.getString(6));
        }
        while(CR.moveToNext());
        */
    }
    public Cursor getTranslation(DatabaseOperations DOP, String word){
        SQLiteDatabase SQ = DOP.getReadableDatabase();
        String selection = TableData.TableInfo.WORD + " LIKE ?";
        String coloumns [] = {TableData.TableInfo.TRANSLATION};
        String args[] = {word};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, coloumns, selection, args, null, null, null);
        return CR;

    }
    public void deleteRow(DatabaseOperations DOP, String set_name, String word){
        String selection = TableData.TableInfo.NAME_OF_SET + " LIKE ? AND " + TableData.TableInfo.WORD + " LIKE ?";
        String args[] = {set_name, word};
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        SQ.delete(TableData.TableInfo.TABLE_NAME, selection, args);
        Log.d("Operations", word + " deleted in " + set_name);
    }
    public void updateDatabase(DatabaseOperations DOP, String set_name, String word, int additional_info,
                               int additional_info2){
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        String selection = TableData.TableInfo.NAME_OF_SET + " LIKE ? AND " + TableData.TableInfo.WORD + " LIKE ?";
        String args[] = {set_name, word};
        ContentValues values = new ContentValues();
        values.put(TableData.TableInfo.ADDITIONAL_INFO,additional_info);
        values.put(TableData.TableInfo.ADDITIONAL_INFO2,additional_info2);
        SQ.update(TableData.TableInfo.TABLE_NAME, values, selection, args);
        Log.d("Operations", " update in row: "  + set_name + " - " + word );
    }
}

