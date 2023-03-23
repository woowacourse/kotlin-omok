package view

import domain.OmokGame
import domain.event.FinishEventListener
import domain.event.PlaceStoneEventListener
import domain.event.StartEventListener

object OutputView : StartEventListener, PlaceStoneEventListener, FinishEventListener {

    private fun printBoard(omokGame: OmokGame) {
        return println(BoardView(omokGame))
    }

    override fun notifyStartEventHasOccurred(omokGame: OmokGame) {
        println("오목 게임을 시작합니다.")
        printBoard(omokGame)
        println("${if (omokGame.isBlackTurn()) "흑" else "백"}의 차례입니다.")
    }

    override fun notifyPlaceStoneEventHasOccurred(omokGame: OmokGame) {
        printBoard(omokGame)
        print("${if (omokGame.isBlackTurn()) "흑" else "백"}의 차례입니다. ")
        val lastPoint = omokGame.getPointOfLastStonePlaced()
        check(lastPoint != null) { "돌을 하나도 두지 않았을 때 이 메서드가 실행되어선 안됩니다." }
        println("(마지막 돌의 위치: ${lastPoint.x + lastPoint.y.toString()})")
    }

    override fun notifyFinishEventHasOccurred(omokGame: OmokGame) {
        printBoard(omokGame)
        println("${if (omokGame.isBlackWin()) "흑" else "백"}의 승리입니다.")
    }

    fun printStoneViolateRuleMessage() {
        println("해당 돌을 두면 규칙에 어긋납니다.")
    }
}
