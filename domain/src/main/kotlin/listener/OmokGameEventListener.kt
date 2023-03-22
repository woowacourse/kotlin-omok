package listener

import domain.player.Players
import domain.point.Point
import domain.stone.StoneColor

interface OmokGameEventListener {
    fun onStartTurn(stoneColor: StoneColor, point: Point?)
    fun onEndTurn(players: Players)
    fun onStartGame()
    fun onEndGame(winnerStoneColor: StoneColor)
}
