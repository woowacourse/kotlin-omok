package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.data.SerializableBoard
import woowacourse.omok.data.db.entity.GameSimplify
import woowacourse.omok.data.db.table.GameTableSimplify
import woowacourse.omok.data.db.table.GameTableSimplify.BOARD
import woowacourse.omok.data.db.table.GameTableSimplify.ID
import woowacourse.omok.data.db.table.GameTableSimplify.TURN

class OmokDbHelperSimplify(
    context: Context,
    private val tables: List<SQLiteTable> = listOf(GameTableSimplify)
) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        tables.forEach {
            val columns = it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type}" }
            db?.execSQL("CREATE TABLE IF NOT EXISTS ${it.name} ($columns)")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        tables.forEach {
            db?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(db)
    }

    fun selectGame(): GameSimplify? {
        val cursor = writableDatabase.rawQuery(
            "SELECT * FROM ${GameTableSimplify.name}",
            null
        )
        val games = mutableListOf<GameSimplify>()
        if (cursor != null) {
            surchCursor(cursor, games)
        }
        cursor.close()
        return games.firstOrNull()
    }

    private fun surchCursor(
        cursor: Cursor,
        games: MutableList<GameSimplify>
    ) {
        while (cursor.moveToNext()) {
            cursor.eachCursorTask(games)
        }
    }

    private fun Cursor.eachCursorTask(games: MutableList<GameSimplify>) {
        games.add(
            getGameSimplify()
        )
    }

    private fun Cursor.getGameSimplify() = GameSimplify(
        getInt(getColumnIndexOrThrow(ID)),
        GameTableSimplify.numberToCoordinateState(getInt(getColumnIndexOrThrow(TURN))),
        SerializableBoard.stringToBoardState(getString(getColumnIndexOrThrow(BOARD)))
    )

    fun insertGame(): GameSimplify {
        val contentValues = ContentValues().apply {
            put(TURN, GameTableSimplify.coordinateStateToNumber(CoordinateState.BLACK))
            put(BOARD, SerializableBoard.boardStateToString(BoardState()))
        }
        writableDatabase.insert(GameTableSimplify.name, null, contentValues)
        val cursor = readableDatabase.rawQuery("SELECT last_insert_rowid()", null)
        cursor.moveToFirst()
        val id = cursor.getInt(0)
        return GameSimplify(id, CoordinateState.BLACK, BoardState())
    }

    fun updateGame(game: GameSimplify) {
        val contentValues = ContentValues().apply {
            put(TURN, GameTableSimplify.coordinateStateToNumber(game.turn))
            put(BOARD, SerializableBoard.boardStateToString(game.board))
        }
        writableDatabase.update(
            GameTableSimplify.name,
            contentValues,
            "$ID = ${game.gameId}",
            null
        )
    }

    fun deleteGame(gameId: Int) {
        writableDatabase.execSQL("DELETE FROM ${GameTableSimplify.name} WHERE $ID = $gameId")
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
