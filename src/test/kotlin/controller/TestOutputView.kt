package controller

import domain.OmokBoard
import domain.State
import domain.Stone
import view.OutputView

class TestOutputView(
    val onPrintResult: (String) -> Unit
) : OutputView {
    override fun printStart() {}

    override fun printDuplicate() {
        onPrintResult("해당 위치에 돌이 존재합니다.")
    }

    override fun printForbidden() {
        onPrintResult("금수입니다.")
    }

    override fun printOmokState(omokBoard: OmokBoard, state: State, stone: Stone) {}

    override fun printWinner(state: State) {
        if (state == State.BLACK) onPrintResult("흑 승")
        if (state == State.WHITE) onPrintResult("백 승")
    }
}
