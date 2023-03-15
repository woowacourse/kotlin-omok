package listener

import domain.Position
import domain.StoneColor
import domain.player.Players

interface OmokStartEndEventListener {
    fun onStartTurn(stoneColor: StoneColor, position: Position)
    fun onEndTurn(players: Players)
    fun onStartGame()
    fun onEndGame(stoneColor: StoneColor)
}
