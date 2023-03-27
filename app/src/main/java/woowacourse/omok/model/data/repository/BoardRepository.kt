package woowacourse.omok.model.data.repository

import android.content.ContentValues
import android.database.Cursor
import woowacourse.omok.model.data.BoardContract
import woowacourse.omok.model.data.BoardDbHelper

class BoardRepository(boardDbHelper: BoardDbHelper) {
    private val boardDbOnlyWriteable = boardDbHelper.writableDatabase
    private val _cursor: Cursor = boardDbOnlyWriteable.query(
        BoardContract.FeedEntry.TABLE_NAME,
        arrayOf(BoardContract.FeedEntry.TABLE_COLUMN_LOCATION),
        null,
        null,
        null,
        null,
        null,
    )
    val cursor: Cursor get() = _cursor

    fun saveGame(location: Int) {
        val values = ContentValues().apply {
            put(BoardContract.FeedEntry.TABLE_COLUMN_LOCATION, location)
        }

        boardDbOnlyWriteable?.insert(BoardContract.FeedEntry.TABLE_NAME, null, values)
    }

    fun deleteGame() {
        boardDbOnlyWriteable.delete(BoardContract.FeedEntry.TABLE_NAME, null, null)
    }
}
