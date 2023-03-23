package domain.result

import domain.player.Players

sealed class TurnResult(val players: Players) {
    class Success(players: Players): TurnResult(players)
    class Failure(players: Players): TurnResult(players)
}