package listener

import domain.player.Players
import domain.position.Position
import domain.stone.StoneColor

interface OmokGameEventListener {
    fun onStartTurn(stoneColor: StoneColor, position: Position)
    fun onEndTurn(players: Players)
    fun onStartGame()
    fun onEndGame(winnerStoneColor: StoneColor)
}
