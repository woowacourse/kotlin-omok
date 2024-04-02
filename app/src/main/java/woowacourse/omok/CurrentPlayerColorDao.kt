package woowacourse.omok

import android.content.Context
import androidx.core.content.contentValuesOf
import omok.model.Color

class CurrentPlayerColorDao(context: Context) {
    private val dbHelper = OmokDbHelper(context, TABLE_NAME, sql)

    fun insertColor(color: Color) {
        val wb = dbHelper.writableDatabase
        val content =
            contentValuesOf(
                CURRENT_PLAYER to if (color == Color.BLACK) BLACK else WHITE,
            )

        wb.insert(
            TABLE_NAME,
            null,
            content,
        )

        wb.close()
    }

    fun currentPlayerColor(): Color {
        val currentPlayers = mutableListOf<CurrentPlayerEntity>()
        val rd = dbHelper.readableDatabase
        val sql = "select * from $TABLE_NAME"
        val cursor = rd.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val currentPlayer = cursor.getInt(cursor.getColumnIndexOrThrow(CURRENT_PLAYER))
            currentPlayers.add(CurrentPlayerEntity(currentPlayer))
        }

        check(currentPlayers.size <= 1) {
            "현재 오목 게임에서 Turn 상태인 플레이어의 수가 두 명 이상입니다."
        }

        cursor.close()
        rd.close()
        return currentPlayers.map { it.toColor() }.lastOrNull() ?: throw IllegalStateException("현재 진행 중인 플레이어가 없습니다.")
    }

    fun isEmpty(): Boolean {
        val currentPlayers = mutableListOf<CurrentPlayerEntity>()
        val rd = dbHelper.readableDatabase
        val sql = "select * from $TABLE_NAME"
        val cursor = rd.rawQuery(sql, null)

        while (cursor.moveToNext()) {
            val currentPlayer = cursor.getInt(cursor.getColumnIndexOrThrow(CURRENT_PLAYER))
            currentPlayers.add(CurrentPlayerEntity(currentPlayer))
        }

        cursor.close()
        rd.close()
        return currentPlayers.isEmpty()
    }

    fun deleteAll() {
        val wb = dbHelper.writableDatabase
        wb.delete(TABLE_NAME, null, null)
        wb.close()
    }

    companion object {
        private const val BLACK = 1
        private const val WHITE = 0

        private const val TABLE_NAME = "CURRENT_PLAYER"
        private const val CURRENT_PLAYER = "current_player"

        private val sql =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (\n" +
                "$CURRENT_PLAYER int not null\n" +
                ")"
    }
}
