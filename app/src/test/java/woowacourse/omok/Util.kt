package omock

import omock.model.board.Board
import omock.model.player.Player
import omock.model.position.Column
import omock.model.position.Row
import omock.model.stone.Stone

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
