package omok.mapper

import omok.model.Color
import omok.model.Position

fun Array<Array<Color?>>.toArkOmokBoard(): List<List<Int>> {
    val arkBoard = MutableList(15) { MutableList(15) { 0 } }

    for (i in 1..<this.size) {
        for (j in 1..<this[i].size) {
            arkBoard[i - 1][j - 1] =
                when (this[i][j]) {
                    Color.BLACK -> 1
                    Color.WHITE -> 2
                    else -> 0
                }
        }
    }

    return arkBoard
}

fun Position.toArkOmokPoint(): Pair<Int, Int> {
    return Pair(this.col.value - 1, this.row.value - 1)
}
