package woowacourse.omok.db

interface BoardDao {
    val dbHelper: DatabaseHelper

    fun insertStone(boardDto: BoardDto)

    fun getAllStones(): List<BoardDto>

    fun clearBoard()
}
