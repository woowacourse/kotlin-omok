package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import omok.Board
import omok.HorizontalAxis
import omok.OmokGame
import omok.Player
import omok.Position
import omok.Stone
import omok.judgement.RenjuJudgement
import omok.state.Turn

class OmokDBHelper(context: Context?) : SQLiteOpenHelper(context, "Omok.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE ${OmokContract.TABLE_NAME} (${OmokContract.POSITION_X} int, ${OmokContract.POSITION_Y} int, ${OmokContract.TURN} varchar(10));"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE if exists ${OmokContract.TABLE_NAME}"
        db?.execSQL(sql)
        onCreate(db)
    }

    fun setOmokGame(): OmokGame {
        val db = readableDatabase
        val sql = "SELECT * FROM ${OmokContract.TABLE_NAME}"
        val cursor: Cursor = db.rawQuery(sql, null)
        val blackPlayer = Player()
        val whitePlayer = Player()
        var turn: String? = null

        while (cursor.moveToNext()) {
            with(cursor) {
                turn = getString(getColumnIndexOrThrow(OmokContract.TURN))
                if (turn == "black") {
                    blackPlayer.put(
                        Stone(
                            Position(
                                HorizontalAxis.getHorizontalAxis(
                                    (
                                        getInt(
                                            getColumnIndexOrThrow(
                                                OmokContract.POSITION_X
                                            )
                                        )
                                        )
                                ),
                                getInt(getColumnIndexOrThrow(OmokContract.POSITION_Y))
                            )
                        )
                    )
                }
                if (turn == "white") {
                    whitePlayer.put(
                        Stone(
                            Position(
                                HorizontalAxis.getHorizontalAxis(
                                    (
                                        getInt(
                                            getColumnIndexOrThrow(
                                                OmokContract.POSITION_X
                                            )
                                        )
                                        )
                                ),
                                getInt(getColumnIndexOrThrow(OmokContract.POSITION_Y))
                            )
                        )
                    )
                }
            }
        }
        cursor.close()
        db.close()
        return OmokGame(Board(RenjuJudgement(), blackPlayer, whitePlayer))
    }

    fun deleteAll() {
        val db = writableDatabase
        db.delete(OmokContract.TABLE_NAME, null, null)
        db.close()
    }

    fun insertStone(position: Position, turn: Turn) {
        val db = writableDatabase
        val values = ContentValues()
        with(values) {
            put(OmokContract.POSITION_X, position.horizontalAxis.axis)
            put(OmokContract.POSITION_Y, position.verticalAxis)
            put(OmokContract.TURN, turn.toString())
        }
        db.insert(OmokContract.TABLE_NAME, null, values)
        db.close()
    }
}
