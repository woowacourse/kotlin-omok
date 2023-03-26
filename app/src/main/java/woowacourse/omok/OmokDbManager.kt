package woowacourse.omok

import domain.* // ktlint-disable no-wildcard-imports

class OmokDbManager(private val omokDbHelper: OmokDbHelper) {
    fun getOmokGame(rule: Rule): OmokGame {
        val stones = getAllStonesInDatabase()
        return OmokGame(Board(stones, rule))
    }

    fun updateOmokDatabase(stone: Stone) {
        omokDbHelper.updateOmokDatabase(stone)
    }

    fun deleteOmokDatabase() {
        omokDbHelper.deleteOmokDatabase()
    }

    private fun getAllStonesInDatabase(): Stones {
        val cursor = omokDbHelper.getAllStonesSearchCursor()
        var stones = Stones(listOf())
        while (cursor.moveToNext()) {
            val color =
                cursor.getString(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_COLOR))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(OmokContract.TABLE_COLUMN_Y))
            stones = stones.addStone(Stone(stringToColorInDb(color), Position(x, y)))
        }
        return stones
    }

    private fun stringToColorInDb(message: String): Color {
        return when (message) {
            OmokDbHelper.BLACK_STONE_COLOR -> Color.BLACK
            OmokDbHelper.WHITE_STONE_COLOR -> Color.WHITE
            else -> throw IllegalArgumentException("잘못된 색")
        }
    }
}
