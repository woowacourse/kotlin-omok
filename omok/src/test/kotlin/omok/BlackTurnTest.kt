package omok

import omok.model.Board
import omok.model.Column
import omok.model.Row
import omok.model.state.Stone
import omok.model.turn.BlackTurn
import omok.model.turn.Turn
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackTurnTest {
    private lateinit var player: Turn
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        player = BlackTurn()
        board = Board.from()
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 3-3이라면, 예외를 발생시킨다 `() {
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("12"), Column("C")),
                    Stone.from(Row("12"), Column("E")),
                    Stone.from(Row("13"), Column("D")),
                    Stone.from(Row("14"), Column("D")),
                ),
        )

        val stone = Stone.from(Row("12"), Column("D"))
        board.setStoneState(player, stone)
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        assertThat(
            player.processTurn(board.stoneStates, row, column).isFailure,
        ).isTrue
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 장목이라면, 예외를 발생시킨다 `() {
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("15"), Column("C")),
                    Stone.from(Row("14"), Column("C")),
                    Stone.from(Row("12"), Column("C")),
                    Stone.from(Row("11"), Column("C")),
                    Stone.from(Row("10"), Column("C")),
                ),
        )

        val stone = Stone.from(Row("13"), Column("C"))
        board.setStoneState(player, stone)

        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        assertThat(
            player.processTurn(board.stoneStates, row, column).isFailure,
        ).isTrue
    }

    @Test
    fun `BlackPlayer가 돌을 놓을 때, 놓은 지점이 Board에서 열린 4라면, 예외를 발생시킨다 `() {
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("6"), Column("B")),
                    Stone.from(Row("5"), Column("C")),
                    Stone.from(Row("6"), Column("E")),
                    Stone.from(Row("5"), Column("E")),
                ),
        )

        val stone = Stone.from(Row("3"), Column("E"))
        board.setStoneState(player, stone)

        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        assertThat(
            player.processTurn(board.stoneStates, row, column).isFailure,
        ).isTrue
    }
}
