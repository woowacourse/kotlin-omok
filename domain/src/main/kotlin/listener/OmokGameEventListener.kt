package listener

import domain.player.Players
import domain.point.Point
import domain.result.TurnResult
import domain.stone.StoneColor

interface OmokGameEventListener {
    fun onStartTurn(stoneColor: StoneColor, point: Point?)
    fun onEndTurn(result: TurnResult)
    fun onStartGame()
    fun onEndGame(players: Players)
}
