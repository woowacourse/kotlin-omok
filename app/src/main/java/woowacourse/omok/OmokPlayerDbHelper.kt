package woowacourse.omok

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_DRAW
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_ID
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_LOSE
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_NAME
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_PROFILE
import woowacourse.omok.OmokPlayerConstract.TABLE_COLUMN_WIN
import woowacourse.omok.OmokPlayerConstract.TABLE_NAME_PLAYER
import woowacourse.omok.data.OverallRecord
import woowacourse.omok.data.Player

class OmokPlayerDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ark.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_PLAYER (" +
                "$TABLE_COLUMN_ID INTEGER UNIQUE ON CONFLICT REPLACE PRIMARY KEY," +
                "$TABLE_COLUMN_NAME TEXT," +
                "$TABLE_COLUMN_WIN INTEGER," +
                "$TABLE_COLUMN_LOSE INTEGER," +
                "$TABLE_COLUMN_DRAW INTEGER," +
                "$TABLE_COLUMN_PROFILE INTEGER" +
                ");",
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_PLAYER")
        onCreate(db)
    }

    fun insert(player: Player) {
        val wDb = this.writableDatabase
        val values = ContentValues()
        values.put(TABLE_COLUMN_NAME, player.name)
        values.put(TABLE_COLUMN_WIN, player.overallRecord.win)
        values.put(TABLE_COLUMN_DRAW, player.overallRecord.draw)
        values.put(TABLE_COLUMN_LOSE, player.overallRecord.lose)
        values.put(TABLE_COLUMN_PROFILE, player.profile)

        wDb.insertWithOnConflict(TABLE_NAME_PLAYER, null, values, SQLiteDatabase.CONFLICT_IGNORE)
    }

    fun getPlayer(playerId: Int): Player? {
        val rDb = this.readableDatabase
        val cursor = rDb.query(
            TABLE_NAME_PLAYER,
            arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE, TABLE_COLUMN_DRAW, TABLE_COLUMN_PROFILE),
            "$TABLE_COLUMN_ID = ?",
            arrayOf(playerId.toString()),
            null,
            null,
            null,
            null,
        )

        var player: Player? = null

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(TABLE_COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(TABLE_COLUMN_NAME))
                val win = getInt(getColumnIndexOrThrow(TABLE_COLUMN_WIN))
                val lose = getInt(getColumnIndexOrThrow(TABLE_COLUMN_LOSE))
                val draw = getInt(getColumnIndexOrThrow(TABLE_COLUMN_DRAW))
                val profile = getInt(getColumnIndexOrThrow(TABLE_COLUMN_PROFILE))

                player = Player(
                    id = id,
                    name = name.toString(),
                    overallRecord = OverallRecord(
                        win = win,
                        lose = lose,
                        draw = draw,
                    ),
                    profile = profile,
                )
            }
        }

        return player
    }

    fun getPlayers(): List<Player> {
        val rDb = this.readableDatabase
        val cursor = rDb.query(
            TABLE_NAME_PLAYER,
            arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE, TABLE_COLUMN_DRAW, TABLE_COLUMN_PROFILE),
            null,
            null,
            null,
            null,
            null,
        )

        val players = mutableListOf<Player>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(TABLE_COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(TABLE_COLUMN_NAME))
                val win = getInt(getColumnIndexOrThrow(TABLE_COLUMN_WIN))
                val lose = getInt(getColumnIndexOrThrow(TABLE_COLUMN_LOSE))
                val draw = getInt(getColumnIndexOrThrow(TABLE_COLUMN_DRAW))
                val profile = getInt(getColumnIndexOrThrow(TABLE_COLUMN_PROFILE))

                players.add(
                    Player(
                        id = id,
                        name = name.toString(),
                        overallRecord = OverallRecord(
                            win = win,
                            lose = lose,
                            draw = draw,
                        ),
                        profile = profile,
                    ),
                )
            }
        }

        return players
    }
}
