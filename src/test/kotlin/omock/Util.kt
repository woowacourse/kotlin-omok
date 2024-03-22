package omock

import omock.model.Board
import omock.model.Column
import omock.model.Player
import omock.model.Row
import omock.model.Stone

fun Board.makeStones(
    player: Player,
    vararg coordinates: String,
) {
    coordinates.forEach { coordinate ->
        this.setStoneState(
            player = player,
            Stone.from(Row(coordinate.substring(0, coordinate.length - 1)), Column(coordinate.substring(coordinate.length - 1))),
        )
    }
}
