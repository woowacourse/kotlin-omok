package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.domain.BoardState
import domain.domain.CoordinateState
import woowacourse.omok.data.SerializableBoard
import woowacourse.omok.data.db.entity.GameSimplify
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

    fun selectGame(): GameSimplify? {
        val cursor = writableDatabase.rawQuery(
            "SELECT * FROM ${GameTableSimplify.name}",
            null
        )
        val games = mutableListOf<GameSimplify>()
        if (cursor != null) {
            with(cursor) {
                while (moveToNext()) {
                    games.add(
                        GameSimplify(
                            getInt(getColumnIndexOrThrow(GameTableSimplify.ID)),
                            GameTableSimplify.numberToCoordinateState(
                                getInt(
                                    getColumnIndexOrThrow(
                                        GameTableSimplify.TURN
                                    )
                                )
                            ),
                            SerializableBoard.stringToBoardState(
                                getString(
                                    getColumnIndexOrThrow(
                                        GameTableSimplify.BOARD
                                    )
                                )
                            )
                        )
                    )
                }
            }
        }
        cursor.close()
        return games.firstOrNull()
    }

    fun insertGame(): GameSimplify {
        val contentValues = ContentValues().apply {
            put(
                GameTableSimplify.TURN,
                GameTableSimplify.coordinateStateToNumber(CoordinateState.BLACK)
            )
            put(GameTableSimplify.BOARD, SerializableBoard.boardStateToString(BoardState()))
        }
        writableDatabase.insert(GameTableSimplify.name, null, contentValues)
        val cursor = readableDatabase.rawQuery("SELECT last_insert_rowid()", null)

        cursor.moveToFirst()
        val id = cursor.getInt(0)

        return GameSimplify(id, CoordinateState.BLACK, BoardState())
    }

    fun updateGame(game: GameSimplify) {
        val contentValues = ContentValues().apply {
            put(GameTableSimplify.TURN, GameTableSimplify.coordinateStateToNumber(game.turn))
            put(GameTableSimplify.BOARD, SerializableBoard.boardStateToString(game.board))
        }
        writableDatabase.update(
            GameTableSimplify.name,
            contentValues,
            "${GameTableSimplify.ID} = ${game.gameId}",
            null
        )
    }

    fun deleteGame(gameId: Int) {
        writableDatabase.execSQL("DELETE FROM ${GameTableSimplify.name} WHERE ${GameTableSimplify.ID} = $gameId")
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
