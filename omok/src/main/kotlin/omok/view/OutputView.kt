package omok.view

import omok.model.ErrorType.AlreadyExistStone
import omok.model.ErrorType.RanjuRuleException
import omok.model.OMokBoard
import omok.model.state.Stone

object OutputView {
    fun outputUserLocation() {
        print("위치를 입력하세요:")
    }

    fun outputSuccessOMock() {
        println("오목~!~!~!~!~!~")
    }

    fun outputUserTurn(name: String) {
        print("${name}의 차례입니다.")
    }

    fun outputPrintLine() {
        println()
    }

    fun outputLastStone(lastStone: Stone) {
        println("(마지막 돌의 위치: ${lastStone.column.comma}${lastStone.row.comma})")
    }

    fun outputGameStart() {
        println("오목 게임을 시작합니다.")
    }

    fun outputBoard() {
        OMokBoard.boardForm.forEachIndexed { index, s ->
            if (index == OMokBoard.boardForm.size - 1) {
                println(s)
            } else {
                println(s.format(*OMokBoard.boardTable[index].toTypedArray()))
            }
        }
    }

    fun outputErrorMessage(e: Throwable) {
        val errorMessage =
            when (e.cause) {
                is RanjuRuleException -> ErrorMessage.RANJU_RULE_ERROR.message
                is AlreadyExistStone -> ErrorMessage.ALREADY_EXIST_STONE_ERROR
                else -> e.message
            }

        println(errorMessage)
    }
}
