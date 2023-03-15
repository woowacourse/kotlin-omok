package domain.stone

import domain.stone.Board.Companion.BOARD_SIZE

class OmokRule {

    fun isOmokCondition(board: List<List<Stone?>>, stone: Stone): Boolean {
        val x: Int = stone.position.x
        val y: Int = stone.position.y

        if (checkHorizontal(board, x, y)) return true
        if (checkVertical(board, x, y)) return true
        if (checkDiagonal1(board, x, y)) return true
        if (checkDiagonal2(board, x, y)) return true
        return false
    }

    private fun checkHorizontal(board: List<List<Stone?>>, x: Int, y: Int): Boolean {
        var count: Int = 0
        for (i in -4..4) {
            if (x + i in 1..BOARD_SIZE && board[y][x + i] != null) count++
        }
        return count >= 5
    }

    private fun checkVertical(board: List<List<Stone?>>, x: Int, y: Int): Boolean {
        var count: Int = 0
        for (i in -4..4) {
            if (y + i in 1..BOARD_SIZE && board[y + i][x] != null) count++
        }
        return count >= 5
    }

    private fun checkDiagonal1(board: List<List<Stone?>>, x: Int, y: Int): Boolean {
        var count: Int = 0
        for (i in -4..4) {
            if (x + i in 1..BOARD_SIZE &&
                y + i in 1..BOARD_SIZE &&
                board[y + i][x + i] != null
            ) {
                count++
            }
        }
        return count >= 5
    }

    private fun checkDiagonal2(board: List<List<Stone?>>, x: Int, y: Int): Boolean {
        var count: Int = 0
        for (i in -4..4) {
            if (x + i in 1..BOARD_SIZE &&
                y + i in 1..BOARD_SIZE &&
                board[y + i][x - i] != null
            ) {
                count++
            }
        }
        return count >= 5
    }

//    private fun checkHorizontal(board: List<List<Stone?>>, x: Int, y: Int): Boolean =
//        (0..4).all { i -> x + i < BOARD_SIZE && board[y][x + i] == board[y][x] }
//
//    private fun checkVertical(board: List<List<Stone?>>, x: Int, y: Int): Boolean =
//        (0..4).all { i -> y + i < BOARD_SIZE && board[y + i][x] == board[y][x] }
//
//    private fun checkDiagonal1(board: List<List<Stone?>>, x: Int, y: Int): Boolean =
//        (0..4).all { i -> y + i < BOARD_SIZE && x + i < BOARD_SIZE && board[y + i][x + i] == board[y][x] }
//
//    private fun checkDiagonal2(board: List<List<Stone?>>, x: Int, y: Int): Boolean =
//        (0..4).all { i -> y + i < BOARD_SIZE && x - i >= 0 && board[y + i][x - i] == board[y][x] }
}
