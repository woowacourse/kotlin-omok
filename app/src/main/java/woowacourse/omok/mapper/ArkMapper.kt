package woowacourse.omok.mapper

import woowacourse.omok.model.Color
import woowacourse.omok.model.Position
import woowacourse.omok.model.Rows

private const val BOARD_SIZE = 15
private const val BLANK_SIGNATURE_NUMBER = 0
private const val BLACK_SIGNATURE_NUMBER = 1
private const val WHITE_SIGNATURE_NUMBER = 2

fun Rows.toArkOmokBoard(): List<List<Int>> {
    val arkBoard = MutableList(BOARD_SIZE) { MutableList(BOARD_SIZE) { BLANK_SIGNATURE_NUMBER } }
    for (i in 1..<values.size) {
        for (j in 1..<values[i].placementData.size) {
            arkBoard[i - 1][j - 1] = values[i].placementData[j].toSignatureNumber()
        }
    }
    return arkBoard
}

private fun Color?.toSignatureNumber() =
    when (this) {
        Color.BLACK -> BLACK_SIGNATURE_NUMBER
        Color.WHITE -> WHITE_SIGNATURE_NUMBER
        else -> BLANK_SIGNATURE_NUMBER
    }

fun Position.toArkOmokPoint(): Pair<Int, Int> {
    return Pair(this.verticalCoordinate - 1, this.horizontalCoordinate - 1)
}
