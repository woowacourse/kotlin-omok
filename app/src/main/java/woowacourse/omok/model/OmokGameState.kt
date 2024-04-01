package woowacourse.omok.model

import omok.model.Black
import omok.model.Empty
import omok.model.OmokStone

class OmokGameState(private val board: Array<Array<OmokStone>>) {
    fun serialize(): String {
        val stringBuilder = StringBuilder()
        for (row in board.indices) {
            for (col in board[row].indices) {
                val stone = board[row][col]
                if (stone !is Empty) {
                    val stoneType = if (stone is Black) "B" else "W"
                    stringBuilder.append("$row,$col,$stoneType;")
                }
            }
        }
        return stringBuilder.toString()
    }

}