package woowacourse.omok.domain.model.database

import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class OmokTurnDao(private val sqLiteOpenHelper: SQLiteOpenHelper) {
    fun save(omokTurn: OmokTurn): OmokTurn {
        val db = sqLiteOpenHelper.writableDatabase
        val id =
            db.insert(
                OmokTurnContract.TABLE_NAME,
                null,
                contentValuesOf(
                    OmokTurnContract.COLUMN_POSITION_ROW to omokTurn.row,
                    OmokTurnContract.COLUMN_POSITION_COLUMN to omokTurn.column,
                    OmokTurnContract.COLUMN_STONE_COLOR to omokTurn.stoneColor,
                ),
            )
        return omokTurn.copy(id = id)
    }

    fun drop() {
        val db = sqLiteOpenHelper.writableDatabase
        db.execSQL(OmokTurnContract.DROP_OMOK_TURN_TABLE)
    }
}
