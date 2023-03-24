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

    private fun checkAlreadyExist(playerName: String): Boolean {
        val rDb = this.readableDatabase

        val cursor = rDb.query(
            TABLE_NAME_PLAYER,
            arrayOf(TABLE_COLUMN_NAME),
            "$TABLE_COLUMN_NAME = ?",
            arrayOf(playerName),
            null,
            null,
            null,
        )

        return cursor.count > 0
    }

    fun insertOrReplace(player: Player) = when {
        checkAlreadyExist(player.name) -> update(player)
        else -> insert(player)
    }

    private fun getPlayerContentValues(player: Player) = ContentValues().apply {
        put(TABLE_COLUMN_NAME, player.name)
        put(TABLE_COLUMN_WIN, player.overallRecord.win)
        put(TABLE_COLUMN_LOSE, player.overallRecord.lose)
        put(TABLE_COLUMN_DRAW, player.overallRecord.draw)
        put(TABLE_COLUMN_PROFILE, player.profile)
    }

    private fun insert(player: Player) {
        val wDb = this.writableDatabase

        if (checkAlreadyExist(player.name)) {
            update(player)
            return
        }

        val values = getPlayerContentValues(player)

        wDb.insertWithOnConflict(TABLE_NAME_PLAYER, null, values, SQLiteDatabase.CONFLICT_REPLACE)
    }

    private fun update(player: Player) {
        val wDb = this.writableDatabase

        val values = getPlayerContentValues(player)

        wDb.update(TABLE_NAME_PLAYER, values, "$TABLE_COLUMN_NAME = ?", arrayOf(player.name))
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
                player = Player(
                    id = getInt(getColumnIndexOrThrow(TABLE_COLUMN_ID)),
                    name = getString(getColumnIndexOrThrow(TABLE_COLUMN_NAME)),
                    overallRecord = OverallRecord(
                        win = getInt(getColumnIndexOrThrow(TABLE_COLUMN_WIN)),
                        lose = getInt(getColumnIndexOrThrow(TABLE_COLUMN_LOSE)),
                        draw = getInt(getColumnIndexOrThrow(TABLE_COLUMN_DRAW)),
                    ),
                    profile = getInt(getColumnIndexOrThrow(TABLE_COLUMN_PROFILE)),
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
                players.add(
                    Player(
                        id = getInt(getColumnIndexOrThrow(TABLE_COLUMN_ID)),
                        name = getString(getColumnIndexOrThrow(TABLE_COLUMN_NAME)),
                        overallRecord = OverallRecord(
                            win = getInt(getColumnIndexOrThrow(TABLE_COLUMN_WIN)),
                            lose = getInt(getColumnIndexOrThrow(TABLE_COLUMN_LOSE)),
                            draw = getInt(getColumnIndexOrThrow(TABLE_COLUMN_DRAW)),
                        ),
                        profile = getInt(getColumnIndexOrThrow(TABLE_COLUMN_PROFILE)),
                    ),
                )
            }
        }

        return players
    }
}
