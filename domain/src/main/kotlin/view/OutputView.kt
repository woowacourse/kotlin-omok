package view

import domain.OmokGame
import domain.event.GameEventListener
import domain.stone.Team

object OutputView : GameEventListener {

    private const val OMOK_GAME_START_MESSAGE = "오목 게임을 시작합니다."
    private const val TURN_CHANGE_MESSAGE = "%s의 차례입니다."
    private const val LAST_POINT_IS_NULL_ERROR = "돌을 하나도 두지 않았을 때 이 메서드가 실행되어선 안됩니다."
    private const val LAST_STONE_POINT_MESSAGE = "(마지막 돌의 위치: %s)"
    private const val WIN_MESSAGE = "%s의 승리입니다."
    private const val STONE_VIOLATE_RULE_MESSAGE = "해당 돌을 두면 규칙에 어긋납니다."

    private fun printBoard(omokGame: OmokGame) {
        println(BoardView(omokGame.getBoard()))
        println()
    }

    override fun onGameCreated(omokGame: OmokGame) {
        println(OMOK_GAME_START_MESSAGE)
        printBoard(omokGame)
        println(TURN_CHANGE_MESSAGE.format(omokGame.getTurn().toKorean()))
    }

    override fun onStonePlaced(omokGame: OmokGame) {
        printBoard(omokGame)
        print(TURN_CHANGE_MESSAGE.format(omokGame.getTurn().toKorean()))
        val lastPoint = omokGame.getLastPoint()
        check(lastPoint != null) { LAST_POINT_IS_NULL_ERROR }
        println(LAST_STONE_POINT_MESSAGE.format(lastPoint.x + lastPoint.y.toString()))
    }

    override fun onGameFinished(omokGame: OmokGame) {
        printBoard(omokGame)
        println(WIN_MESSAGE.format(omokGame.getWinner().toKorean()))
    }

    private fun Team.toKorean(): String =
        when (this) {
            Team.BLACK -> "흑"
            Team.WHITE -> "백"
        }

    fun printStoneViolateRuleMessage() {
        println(STONE_VIOLATE_RULE_MESSAGE)
    }
}
