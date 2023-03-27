package woowacourse.omok.db

object StoneConstract {
    const val TABLE_NAME = "stones"
    const val TABLE_COLUMN_ID = "id"
    const val TABLE_COLUMN_COLOR = "stone_color"
    const val TABLE_COLUMN_X = "x_position"
    const val TABLE_COLUMN_Y = "y_position"
    const val TABLE_COLUMN_ROOM_NUMBER = "room_number"

    fun allColumn(): Array<String> = arrayOf(
        TABLE_COLUMN_ID,
        TABLE_COLUMN_ROOM_NUMBER,
        TABLE_COLUMN_COLOR,
        TABLE_COLUMN_X,
        TABLE_COLUMN_Y
    )
}
