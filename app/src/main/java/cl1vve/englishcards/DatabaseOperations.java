package cl1vve.englishcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {
    public static final int database_version = 1;
    public String CREATE_QUERY = "CREATE TABLE " + TableData.TableInfo.TABLE_NAME +
            "(" + TableData.TableInfo.ID + " INTEGER," +
            TableData.TableInfo.WORD + " TEXT," +
            TableData.TableInfo.TRANSLATION + " TEXT," +
            TableData.TableInfo.INFO + " INTEGER);";
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {
        sdb.execSQL(CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public int showLastId(DatabaseOperations dop){
        SQLiteDatabase SQ = dop. getReadableDatabase();
        String[] coloumns = { TableData.TableInfo.ID};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, coloumns, null, null, null, null, null);
        return CR.getCount();
    }
    public void putInformation(DatabaseOperations dop, int id, String word, String translation,
                               int word_info){
        SQLiteDatabase SQ = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TableData.TableInfo.ID, id);
        cv.put(TableData.TableInfo.WORD, word);
        cv.put(TableData.TableInfo.TRANSLATION, translation);
        cv.put(TableData.TableInfo.INFO, word_info);
        SQ.insert(TableData.TableInfo.TABLE_NAME, null, cv);
        Log.d("Operations", "One raw inserted");
    }

    public Cursor getInformation(DatabaseOperations dop){
        SQLiteDatabase SQ = dop.getReadableDatabase();
        String[] coloumns = {TableData.TableInfo.ID,
                TableData.TableInfo.WORD, TableData.TableInfo.TRANSLATION,
                TableData.TableInfo.INFO};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, coloumns, null, null, null, null, null);
        return CR;
    }
    public Cursor getTranslation(DatabaseOperations DOP, String word){
        SQLiteDatabase SQ = DOP.getReadableDatabase();
        String selection = TableData.TableInfo.WORD + " LIKE ?";
        String coloumns [] = {TableData.TableInfo.TRANSLATION};
        String args[] = {word};
        Cursor CR = SQ.query(TableData.TableInfo.TABLE_NAME, coloumns, selection, args, null, null, null);
        return CR;

    }
    public void deleteRow(DatabaseOperations DOP, String word, String translation){
        String selection = TableData.TableInfo.WORD + " LIKE ? AND " + TableData.TableInfo.TRANSLATION + " LIKE ?";
        String args[] = {word, translation};
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        SQ.delete(TableData.TableInfo.TABLE_NAME, selection, args);
    }
    public void updateDatabase(DatabaseOperations DOP, String word, String translation,
                               String new_word, String new_translation){
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        String selection = TableData.TableInfo.WORD + " LIKE ? AND " + TableData.TableInfo.TRANSLATION + " LIKE ?";
        String args[] = {word, translation};
        ContentValues values = new ContentValues();
        values.put(TableData.TableInfo.WORD, new_word);
        values.put(TableData.TableInfo.TRANSLATION, new_translation);
        SQ.update(TableData.TableInfo.TABLE_NAME, values, selection, args);
    }
    public void deleteAll(DatabaseOperations DOP){
        SQLiteDatabase SQ = DOP.getWritableDatabase();
        SQ.delete(TableData.TableInfo.TABLE_NAME,null,null);
    }


}

