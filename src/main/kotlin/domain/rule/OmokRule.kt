package domain.rule

import domain.stone.Board.Companion.BOARD_SIZE
import domain.stone.Stone
import domain.stone.StoneType

class OmokRule {

    fun isOmokCondition(board: List<List<StoneType>>, stone: Stone): Boolean {
        if (checkHorizontal(board, stone)) return true
        if (checkVertical(board, stone)) return true
        if (checkDiagonal1(board, stone)) return true
        if (checkDiagonal2(board, stone)) return true
        return false
    }

    private fun checkHorizontal(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (board[y][x + i] != stone.type) count = 0
            if (board[y][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkVertical(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x] != stone.type) count = 0
            if (board[y + i][x] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal1(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x + i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x + i] != stone.type) count = 0
            if (board[y + i][x + i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }

    private fun checkDiagonal2(board: List<List<StoneType>>, stone: Stone): Boolean {
        var count: Int = 0
        val x: Int = stone.position.x
        val y: Int = stone.position.y
        for (i in -4..4) {
            if (x - i !in 1..BOARD_SIZE) continue
            if (y + i !in 1..BOARD_SIZE) continue
            if (board[y + i][x - i] != stone.type) count = 0
            if (board[y + i][x - i] == stone.type) {
                count++
                if (count >= 5) break
            }
        }
        return count >= 5
    }
}
