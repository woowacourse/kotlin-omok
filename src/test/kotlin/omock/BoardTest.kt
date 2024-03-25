package omock

import omock.model.Board
import omock.model.state.Stone
import omock.model.turn.Turn

fun Board.makeStones(
    player: Turn,
    vararg stones: Stone,
) {
    stones.forEach { stone ->
        this.setStoneState(player, stone)
    }
}
