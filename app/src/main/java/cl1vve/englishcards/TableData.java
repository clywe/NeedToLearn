package cl1vve.englishcards;

import android.provider.BaseColumns;

/**
 * Created by CL1VVE on 26.03.2017.
 */

public class TableData {

    public TableData(){

    }

    public static abstract class TableInfo implements BaseColumns
    {
        public static final String SET_ID = "set_id";
        public static final String NAME_OF_SET ="name_of_set";
        public static final String WORD = "word";
        public static final String TRANSLATION = "translation";
        public static final String EXAMPLES = "examples";
        public static final String ADDITIONAL_INFO = "additional_info";
        public static final String ADDITIONAL_INFO2 = "additional_info2";
        public static final String DATABASE_NAME = "all_sets";
        public static final String TABLE_NAME = "sets";
    }

}
