package woowacourse.omok.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import woowacourse.omok.domain.model.position.Position
import woowacourse.omok.domain.model.state.BlackStoneTurn
import woowacourse.omok.domain.model.state.OmokState
import woowacourse.omok.domain.model.state.WhiteStoneTurn
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

    override fun loadStones(): List<Pair<Position, StoneType>> {
        val stones = mutableListOf<Pair<Position, StoneType>>()
        val cursor =
            database.rawQuery(
                "SELECT " + OmokContract.GameState.COLUMN_X + ", ${OmokContract.GameState.COLUMN_Y}, " +
                    "${OmokContract.GameState.COLUMN_STONE_TYPE} FROM ${OmokContract.GameState.TABLE_NAME}",
                null,
            )
        while (cursor.moveToNext()) {
            val position = Position.of(cursor.getInt(0), cursor.getInt(1), 15)
            val stoneType = StoneType.valueOf(cursor.getString(2))
            stones.add(position to stoneType)
        }
        cursor.close()
        return stones
    }

    override fun saveGameTurn(state: OmokState) {
        database.execSQL("DELETE FROM ${OmokContract.GameInfo.TABLE_NAME} WHERE " + OmokContract.GameInfo.COLUMN_KEY + "= 'turn'")
        database.execSQL(
            "INSERT INTO ${OmokContract.GameInfo.TABLE_NAME} (${OmokContract.GameInfo.COLUMN_KEY}, " +
                "${OmokContract.GameInfo.COLUMN_VALUE}) VALUES ('turn', '${state.stoneType.name}')",
        )
    }

    override fun loadGameTurn(): OmokState {
        val cursor =
            database.rawQuery(
                "SELECT ${OmokContract.GameInfo.COLUMN_VALUE} FROM ${OmokContract.GameInfo.TABLE_NAME} WHERE " +
                    OmokContract.GameInfo.COLUMN_KEY + " = 'turn'",
                null,
            )
        val state =
            if (cursor.moveToFirst()) {
                when (StoneType.valueOf(cursor.getString(0))) {
                    StoneType.BLACK -> BlackStoneTurn
                    StoneType.WHITE -> WhiteStoneTurn
                    else -> BlackStoneTurn
                }
            } else {
                BlackStoneTurn
            }
        cursor.close()
        return state
    }

    override fun saveGameFinished(isFinished: Boolean) {
        database.execSQL("DELETE FROM game_info WHERE " + OmokContract.GameInfo.COLUMN_KEY + " = 'finished'")
        database.execSQL("INSERT INTO game_info (${OmokContract.GameInfo.COLUMN_KEY}, value) VALUES ('finished', '$isFinished')")
    }

    override fun isGameFinished(): Boolean {
        val cursor =
            database.rawQuery(
                "SELECT value FROM game_info WHERE " + OmokContract.GameInfo.COLUMN_KEY + " = 'finished'",
                null,
            )
        val isFinished = cursor.moveToFirst() && cursor.getString(0).toBoolean()
        cursor.close()
        return isFinished
    }

    override fun clearGameData() {
        database.execSQL("DELETE FROM ${OmokContract.GameState.TABLE_NAME}")
        database.execSQL("DELETE FROM ${OmokContract.GameInfo.TABLE_NAME}")
    }
}
