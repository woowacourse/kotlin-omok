package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_COL
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_NEXT_TURN
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_ROW
import woowacourse.omok.OmokBoardConstract.TABLE_COLUMN_OMOK_STONE
import woowacourse.omok.OmokBoardConstract.TABLE_NAME_OMOK_BOARD

class OmokBoardDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ark.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $TABLE_NAME_OMOK_BOARD (" +
                "  $TABLE_COLUMN_OMOK_ROW int," +
                "  $TABLE_COLUMN_OMOK_COL int," +
                "  $TABLE_COLUMN_OMOK_NEXT_TURN int," +
                "  $TABLE_COLUMN_OMOK_STONE int" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_OMOK_BOARD")
        onCreate(db)
    }
}
