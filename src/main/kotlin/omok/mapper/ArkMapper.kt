package omok.mapper

import omok.model.Color
import omok.model.Position

private const val BOARD_SIZE = 15
private const val BLANK_SIGNATURE_NUMBER = 0
private const val BLACK_SIGNATURE_NUMBER = 1
private const val WHITE_SIGNATURE_NUMBER = 2

fun Array<Array<Color>>.toArkOmokBoard(): List<List<Int>> {
    val arkBoard = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { BLANK_SIGNATURE_NUMBER } }
    for (i in 1 until this.size) {
        changeToArk(i, arkBoard)
    }
    return arkBoard
}

private fun Array<Array<Color>>.changeToArk(
    i: Int,
    arkBoard: MutableList<MutableList<Int>>,
) {
    for (j in 1 until this[i].size) {
        arkBoard[i - 1][j - 1] = this[i][j].toSignatureNumber()
    }
}

private fun Color.toSignatureNumber() =
    when (this) {
        Color.BLACK -> BLACK_SIGNATURE_NUMBER
        Color.WHITE -> WHITE_SIGNATURE_NUMBER
        else -> BLANK_SIGNATURE_NUMBER
    }

fun Position.toArkOmokPoint(): Pair<Int, Int> {
    return Pair(this.col.value - 1, this.row.value - 1)
}
