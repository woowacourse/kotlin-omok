package woowacourse.omok.data.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import domain.domain.Board
import domain.domain.Position
import domain.view.AlphabetCoordinate
import woowacourse.omok.data.SerializableBoard
import woowacourse.omok.data.db.entity.Game
import woowacourse.omok.data.db.entity.User
import woowacourse.omok.data.db.table.GameTable
import woowacourse.omok.data.db.table.UserTable

class OmokDbHelper(
    context: Context,
    private val tables: List<SQLiteTable> = listOf(GameTable, UserTable)
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

    fun selectUserByName(name: String): User? {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM ${UserTable.name} WHERE ${UserTable.NAME} = \"$name\"",
            null
        )
        val users = mutableListOf<User>()
        if (cursor != null) {
            with(cursor) {
                while (moveToNext()) {
                    users.add(
                        User(
                            getInt(getColumnIndexOrThrow(UserTable.ID)),
                            getString(getColumnIndexOrThrow(UserTable.NAME))
                        )
                    )
                }
            }
        }
        cursor.close()
        return users.firstOrNull()
    }

    fun insertUser(name: String) {
        val contentValues = ContentValues().apply {
            put(UserTable.NAME, name)
        }
        writableDatabase.insert(UserTable.name, null, contentValues)
    }

    fun selectAllGameByUser(userId: Int): List<Game> {
        val cursor = readableDatabase.rawQuery(
            "SELECT ${GameTable.ID},${GameTable.TURN},${GameTable.LAST_POSITION},${GameTable.BOARD} FROM ${GameTable.name} WHERE ${GameTable.USERID} = $userId",
            null
        )
        val games = mutableListOf<Game>()
        with(cursor) {
            while (moveToNext()) {
                games.add(
                    Game(
                        getInt(getColumnIndexOrThrow(GameTable.ID)),
                        GameTable.numberToCoordinateState(getInt(getColumnIndexOrThrow(GameTable.TURN))),
                        stringToPosition(getString(getColumnIndexOrThrow(GameTable.LAST_POSITION))),
                        SerializableBoard.stringToBoardState(
                            getString(
                                getColumnIndexOrThrow(
                                    GameTable.BOARD
                                )
                            )
                        ),
                        userId
                    )
                )
            }
        }
        return games
    }

    fun insertGame(game: Game): Game {
        val contentValues = ContentValues().apply {
            put(GameTable.TURN, GameTable.coordinateStateToNumber(game.turn))
            put(GameTable.LAST_POSITION, positionToString(game.lastPosition))
            put(GameTable.BOARD, SerializableBoard.boardStateToString(game.Board))
            put(GameTable.USERID, game.userId)
        }
        writableDatabase.insert(GameTable.name, null, contentValues)
        val cursor = readableDatabase.rawQuery("SELECT last_insert_rowid()", null)

        cursor.moveToFirst()
        val id = cursor.getInt(0)

        return Game(id, game.turn, game.lastPosition, game.Board, game.userId)
    }

    fun updateGame(game: Game) {
        writableDatabase.execSQL(
            "UPDATE ${GameTable.name}" +
                "SET ${GameTable.TURN} = ${GameTable.coordinateStateToNumber(game.turn)}," +
                "${GameTable.LAST_POSITION} = ${positionToString(game.lastPosition)}," +
                "${GameTable.BOARD} = ${SerializableBoard.boardStateToString(game.Board)} " +
                "WHERE ${GameTable.ID} = ${game.gameId} "
        )
    }

    fun deleteGame(game: Game) {
        writableDatabase.execSQL("DELETE FROM ${GameTable.name} WHERE ${GameTable.ID} = ${game.gameId}")
    }

    private fun positionToString(position: Position): String =
        AlphabetCoordinate.convertAlphabet(position.coordinateX).name + ((Board.BOARD_SIZE - position.coordinateY).toString())

    private fun stringToPosition(string: String): Position {
        val x: Int = AlphabetCoordinate.convertX(string.first().toString().uppercase()) ?: 0
        val y: Int = string.substring(1, string.length).toIntOrNull() ?: 0
        return Position(Board.BOARD_SIZE - y, x)
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
