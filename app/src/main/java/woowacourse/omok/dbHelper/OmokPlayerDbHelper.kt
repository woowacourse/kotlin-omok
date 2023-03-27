package woowacourse.omok.dbHelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import woowacourse.omok.data.Player
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_DRAW
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_ID
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_LOSE
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_NAME
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_PROFILE
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_COLUMN_WIN
import woowacourse.omok.dbHelper.OmokPlayerConstract.TABLE_NAME_PLAYER

class OmokPlayerDbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, "ark.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME_PLAYER (" +
                "$TABLE_COLUMN_ID INTEGER UNIQUE ON CONFLICT REPLACE PRIMARY KEY," +
                "$TABLE_COLUMN_NAME TEXT UNIQUE," +
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

    fun insert(player: Player): Long {
        val wDb = this.writableDatabase
        val values = getPlayerContentValues(player)
        return wDb.insert(TABLE_NAME_PLAYER, null, values)
    }

    fun update(player: Player) {
        val wDb = this.writableDatabase
        val values = getPlayerContentValues(player)
        wDb.update(TABLE_NAME_PLAYER, values, "$TABLE_COLUMN_NAME = ?", arrayOf(player.name))
    }

    fun getPlayer(playerId: Long): Player? {
        val rDb = this.readableDatabase
        val cursor = rDb.customQuery(
            table = TABLE_NAME_PLAYER,
            columns = arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE, TABLE_COLUMN_DRAW, TABLE_COLUMN_PROFILE),
            selection = "$TABLE_COLUMN_ID = ?",
            selectionArgs = arrayOf(playerId.toString()),
        )

        var player: Player? = null
        if (cursor.moveToNext()) { player = getPlayerFrom(cursor) }

        cursor.close()
        return player
    }

    fun getPlayer(name: String): Player? {
        val rDb = this.readableDatabase
        val cursor = rDb.customQuery(
            table = TABLE_NAME_PLAYER,
            columns = arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE, TABLE_COLUMN_DRAW, TABLE_COLUMN_PROFILE),
            selection = "$TABLE_COLUMN_NAME = ?",
            selectionArgs = arrayOf(name),
        )

        var player: Player? = null
        if (cursor.moveToNext()) { player = getPlayerFrom(cursor) }

        cursor.close()
        return player
    }

    fun getPlayers(): List<Player> {
        val rDb = this.readableDatabase

        val cursor = rDb.customQuery(
            table = TABLE_NAME_PLAYER,
            columns = arrayOf(TABLE_COLUMN_ID, TABLE_COLUMN_NAME, TABLE_COLUMN_WIN, TABLE_COLUMN_LOSE, TABLE_COLUMN_DRAW, TABLE_COLUMN_PROFILE),
        )

        val players = mutableListOf<Player>()
        while (cursor.moveToNext()) { players.add(getPlayerFrom(cursor)) }

        cursor.close()
        return players
    }

    private fun getPlayerContentValues(player: Player) = ContentValues().apply {
        put(TABLE_COLUMN_NAME, player.name)
        put(TABLE_COLUMN_WIN, player.win)
        put(TABLE_COLUMN_LOSE, player.lose)
        put(TABLE_COLUMN_DRAW, player.draw)
        put(TABLE_COLUMN_PROFILE, player.profile)
    }

    private fun SQLiteDatabase.customQuery(
        table: String,
        columns: Array<String> = arrayOf(),
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        groupBy: String? = null,
        having: String? = null,
        orderBy: String? = null,
        limit: String? = null,
    ): Cursor = query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit)

    private fun getPlayerFrom(cursor: Cursor) = Player(
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TABLE_COLUMN_ID)),
        name = cursor.getString(cursor.getColumnIndexOrThrow(TABLE_COLUMN_NAME)),
        win = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_WIN)),
        lose = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_LOSE)),
        draw = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_DRAW)),
        profile = cursor.getInt(cursor.getColumnIndexOrThrow(TABLE_COLUMN_PROFILE)),
    )
}
