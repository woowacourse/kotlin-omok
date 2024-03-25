package omock.view

import omock.model.OMockBoard
import omock.model.state.Stone
import omock.model.turn.ErrorType.FourToFourCount
import omock.model.turn.ErrorType.IsClearFourToFourCount
import omock.model.turn.ErrorType.IsReverseTwoAndThree
import omock.model.turn.ErrorType.ThreeToThreeCount

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
        OMockBoard.boardForm.forEachIndexed { index, s ->
            if (index == OMockBoard.boardForm.size - 1) {
                println(s)
            } else {
                println(s.format(*OMockBoard.boardTable[index].toTypedArray()))
            }
        }
    }

    fun outputErrorMessage(e: Throwable) {
        val errorMessage =
            when (e.cause) {
                is ThreeToThreeCount -> ErrorMessage.THREE_TO_THREE_COUNT_ERROR.message
                is FourToFourCount -> ErrorMessage.FOUR_TO_FOUR_COUNT_ERROR.message
                is IsReverseTwoAndThree -> ErrorMessage.IS_RESERVE_TWO_AND_THREE_ERROR.message
                is IsClearFourToFourCount -> ErrorMessage.IS_CLEAR_FOUR_TO_FOUR_COUNT_ERROR.message
                else -> e.message
            }

        println(errorMessage)
    }
}
