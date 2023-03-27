package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.SerializableBoard
import woowacourse.omok.data.db.entity.GameSimplify
import woowacourse.omok.data.db.table.GameTable
import woowacourse.omok.data.db.table.GameTableSimplify

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

    fun selectGameById(gameId: Int): GameSimplify? {
        val cursor = readableDatabase.rawQuery(
            "SELECT ${GameTableSimplify.TURN},${GameTableSimplify.BOARD} FROM ${GameTableSimplify.name} WHERE ${GameTableSimplify.ID} = \"$gameId\"",
            null
        )
        val games = mutableListOf<GameSimplify>()
        with(cursor) {
            while (moveToNext()) {
                games.add(
                    GameSimplify(
                        getInt(getColumnIndexOrThrow(GameTable.ID)),
                        GameTable.numberToCoordinateState(getInt(getColumnIndexOrThrow(GameTable.TURN))),
                        SerializableBoard.stringToBoardState(
                            getString(
                                getColumnIndexOrThrow(
                                    GameTable.BOARD
                                )
                            )
                        )
                    )
                )
            }
        }
        cursor.close()
        return games.firstOrNull()
    }

    fun insertGame(game: GameSimplify): GameSimplify {
        val contentValues = ContentValues().apply {
            put(GameTableSimplify.TURN, GameTableSimplify.coordinateStateToNumber(game.turn))
            put(GameTableSimplify.BOARD, SerializableBoard.boardStateToString(game.Board))
        }
        writableDatabase.insert(GameTableSimplify.name, null, contentValues)
        val cursor = readableDatabase.rawQuery("SELECT last_insert_rowid()", null)

        cursor.moveToFirst()
        val id = cursor.getInt(0)

        return GameSimplify(id, game.turn, game.Board)
    }

    fun updateGame(game: GameSimplify) {
        writableDatabase.execSQL(
            "UPDATE ${GameTableSimplify.name}" +
                "SET ${GameTableSimplify.TURN} = ${
                GameTableSimplify.coordinateStateToNumber(
                    game.turn
                )
                }," +
                "${GameTableSimplify.BOARD} = ${SerializableBoard.boardStateToString(game.Board)} " +
                "WHERE ${GameTableSimplify.ID} = ${game.gameId} "
        )
    }

    fun deleteGame(game: GameSimplify) {
        writableDatabase.execSQL("DELETE FROM ${GameTableSimplify.name} WHERE ${GameTableSimplify.ID} = ${game.gameId}")
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
