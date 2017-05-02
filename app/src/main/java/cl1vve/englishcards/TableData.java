package cl1vve.englishcards;

import android.provider.BaseColumns;

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns
    {
        public static final String ID = "id";
        public static final String WORD = "word";
        public static final String TRANSLATION = "translation";
        public static final String INFO = "info";
        public static final String DATABASE_NAME = "all_words";
        public static final String TABLE_NAME = "words";
    }

}
