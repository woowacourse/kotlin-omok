package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.stone.OmokStone
import woowacourse.omok.domain.model.stone.StoneType

class OmokDaoImpl(private val database: SQLiteDatabase) : OmokDao {
    override fun saveStone(
        position: Position,
        stoneType: StoneType,
    ) {
        val values =
            ContentValues().apply {
                put(OmokContract.GameState.COLUMN_X, position.column.value)
                put(OmokContract.GameState.COLUMN_Y, position.row.value)
                put(OmokContract.GameState.COLUMN_STONE_TYPE, stoneType.name)
            }
        database.insert(OmokContract.GameState.TABLE_NAME, null, values)
    }

    override fun loadStones(): List<OmokStone> {
        return database.rawQuery(
            "SELECT " + OmokContract.GameState.COLUMN_X + ", ${OmokContract.GameState.COLUMN_Y}, " +
                "${OmokContract.GameState.COLUMN_STONE_TYPE} FROM ${OmokContract.GameState.TABLE_NAME}",
            null,
        ).use { cursor ->
            generateSequence { if (cursor.moveToNext()) cursor else null }
                .map {
                    OmokStone(
                        Position.of(it.getInt(0), it.getInt(1), 15),
                        StoneType.valueOf(it.getString(2)),
                    )
                }.toList()
        }
    }

    override fun saveGameFinished(isFinished: Boolean) {
        database.execSQL("DELETE FROM game_info WHERE " + OmokContract.GameInfo.COLUMN_KEY + " = 'finished'")
        database.execSQL("INSERT INTO game_info (${OmokContract.GameInfo.COLUMN_KEY}, value) VALUES ('finished', '$isFinished')")
    }

    override fun isGameFinished(): Boolean {
        return database.rawQuery(
            "SELECT ${OmokContract.GameInfo.COLUMN_VALUE} FROM ${OmokContract.GameInfo.TABLE_NAME} " +
                "WHERE " + OmokContract.GameInfo.COLUMN_KEY + " = 'finished'",
            null,
        ).use { cursor ->
            cursor.moveToFirst() && cursor.getString(0).toBoolean()
        }
    }

    override fun clearGameData() {
        database.execSQL("DELETE FROM ${OmokContract.GameState.TABLE_NAME}")
        database.execSQL("DELETE FROM ${OmokContract.GameInfo.TABLE_NAME}")
    }
}
