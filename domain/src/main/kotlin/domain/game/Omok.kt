package domain.game

import domain.player.BlackPlayer
import domain.player.Players
import domain.player.WhitePlayer
import domain.point.Point
import domain.rule.OmokRule
import domain.stone.StoneColor

class Omok(blackRule: OmokRule, whiteRule: OmokRule) {
    private var players = Players(BlackPlayer(rule = blackRule), WhitePlayer(rule = whiteRule))
    val curPlayerColor: StoneColor
        get() = players.curPlayerColor
    val lastPoint: Point?
        get() = players.getLastPoint()

    fun put(onPut: () -> Point): PutResult {
        val newPoint = onPut()
        if (isPutOn(newPoint)) return PutFailed // 이미 놓여져 있다면 Fail 반환

        val latestStoneColor = players.curPlayerColor
        players = players.putStone(newPoint)

        if (players.isFinish) return GameFinish(latestStoneColor, gameEnd(players))
        return PutSuccess(latestStoneColor)
    }

    private fun isPutOn(point: Point): Boolean = players.isPutOn(point)

    private fun gameEnd(players: Players): StoneColor = when {
        players.isFoul -> players.curPlayerColor
        players.isFinish -> players.curPlayerColor.next()
        else -> throw IllegalStateException("아직 게임이 종료되지 않았습니다!")
    }

    companion object {
        const val OMOK_BOARD_SIZE = 15
    }
}
