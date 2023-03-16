package study

import domain.stone.Board
import domain.stone.Stone
import domain.stone.StonePosition
import domain.stone.StoneType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

fun isOmokCondition2(board: StoneBoard, stone: Stone): Boolean {
    val x: Int = stone.position.x
    val y: Int = stone.position.y

    if (checkHorizontal(board, x, y)) return true
    if (checkVertical(board, x, y)) return true
    if (checkDiagonal1(board, x, y)) return true
    if (checkDiagonal2(board, x, y)) return true
    return false
}

private fun checkHorizontal(board: StoneBoard, x: Int, y: Int): Boolean {
    if ((0..4).all { i -> x + i <= Board.BOARD_SIZE && board.board[y][x + i] != null }) return true
    if ((0..4).all { i -> x - i > 0 && board.board[y][x + i] != null }) return true
    return false
}

private fun checkVertical(board: StoneBoard, x: Int, y: Int): Boolean {
    if ((0..4).all { i -> y + i <= Board.BOARD_SIZE && board.board[y + i][x] == board.board[y][x] }) return true
    if ((0..4).all { i -> y - i > 0 && board.board[y + i][x] != null }) return true
    return false
}

private fun checkDiagonal1(board: StoneBoard, x: Int, y: Int): Boolean {
    if ((0..4).all { i -> y + i <= Board.BOARD_SIZE && x + i <= Board.BOARD_SIZE && board.board[y + i][x + i] == board.board[y][x] }) return true
    if ((0..4).all { i -> y - i > 0 && x - i > 0 && board.board[y + i][x + i] == board.board[y][x] }) return true
    return false
}

private fun checkDiagonal2(board: StoneBoard, x: Int, y: Int): Boolean {
    if ((0..4).all { i -> y + i < Board.BOARD_SIZE && x - i >= 0 && board.board[y + i][x - i] == board.board[y][x] }) return true
    if ((0..4).all { i -> y + i < Board.BOARD_SIZE && x - i >= 0 && board.board[y + i][x - i] == board.board[y][x] }) return true
    return false
}

class StoneBoardTest {

    @Test
    fun `board`() {
        val stoneBoard = StoneBoard()
        val stone1 = Stone(StonePosition.from(1, 1)!!, StoneType.BLACK)
        val stone2 = Stone(StonePosition.from(2, 1)!!, StoneType.BLACK)
        val stone3 = Stone(StonePosition.from(3, 1)!!, StoneType.BLACK)
        val stone4 = Stone(StonePosition.from(4, 1)!!, StoneType.BLACK)
        val stone5 = Stone(StonePosition.from(5, 1)!!, StoneType.BLACK)
        stoneBoard.putStone(stone1)
        stoneBoard.putStone(stone2)
        stoneBoard.putStone(stone3)
        stoneBoard.putStone(stone4)

        assertThat(isOmokCondition2(stoneBoard, stone5)).isTrue()
    }
}

enum class SType {
    EMPTY, BLACK, WHITE
}

class StoneBoard(
    val board: Array<Array<SType>> = Array(16) { Array(16) { SType.EMPTY } },
) {

    fun putStone(stone: Stone) {
        board[stone.position.y][stone.position.x] = SType.BLACK
    }
}
