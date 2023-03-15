package listener

import Position
import StoneColor
import player.Player
import player.Players

interface OmokEventListener {
    fun onStartGame()
    fun onTakeTurn(stoneColor: StoneColor): Position
    fun onEndTurn(positions: Players)
    fun onEndGame(player: Player)
}
