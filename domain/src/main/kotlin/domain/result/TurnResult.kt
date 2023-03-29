package domain.result

import domain.player.Players
import domain.stone.StoneColor

sealed class TurnResult(val players: Players) {
    class Playing(val isExistPoint: Boolean, players: Players) : TurnResult(players)
    class Foul(val winColor: StoneColor, players: Players) : TurnResult(players)
    class Win(val winColor: StoneColor, players: Players) : TurnResult(players)
}
