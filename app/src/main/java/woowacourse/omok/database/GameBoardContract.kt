package woowacourse.omok.database

object GameBoardContract {
    object GameBoardEntry {
        const val TABLE_NAME = "GameBoard"
        const val COLUMN_ID = "id"
        const val COLUMN_ROW_INDEX = "rowIndex"
        const val COLUMN_COLUMN_INDEX = "columnIndex"
        const val COLUMN_STONE_TYPE = "stoneType"
    }

    object GameStatusEntry {
        const val TABLE_NAME = "GameStatus"
        const val COLUMN_ID = "id"
        const val COLUMN_CURRENT_STONE = "currentStone"
    }
}
