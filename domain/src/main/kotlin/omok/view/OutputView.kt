package omok.view

import omok.domain.OmokBoard

object OutputView {
    fun outputInit() {
        println("오목 게임을 시작합니다.")
    }

    fun outputBoard(omokBoard: OmokBoard) {
        val stringBuilder = StringBuilder()
        OmokMapUIModel.adaptMap(omokBoard).reversed().forEachIndexed { y, list ->
            stringBuilder.append("${omokBoard.ySize - y}".padStart(3, ' '))
            list.forEach { stoneState -> stringBuilder.append(stoneState) }
            stringBuilder.appendLine()
        }
        ('A'..'A' + (omokBoard.xSize - 1)).joinToString("  ").let { stringBuilder.append("    $it") }
        println(stringBuilder.toString())
    }
}
