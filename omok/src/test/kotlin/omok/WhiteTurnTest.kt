package omok

import omok.model.Board
import omok.model.Column
import omok.model.Row
import omok.model.state.Stone
import omok.model.turn.BlackTurn
import omok.model.turn.FinishedTurn
import omok.model.turn.Turn
import omok.model.turn.WhiteTurn
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhiteTurnTest {
    private lateinit var player: Turn
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        player = WhiteTurn()
        board = Board.from()
    }

    @Test
    fun `White Turn일 때 돌을 놓으면, Board가 오목인 경우 Finished Turn을 반환한다`() {
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("2"), Column("B")),
                    Stone.from(Row("1"), Column("B")),
                    Stone.from(Row("2"), Column("A")),
                    Stone.from(Row("3"), Column("A")),
                    Stone.from(Row("4"), Column("A")),
                    Stone.from(
                        Row("5"),
                        Column("A"),
                    ),
                ),
        )

        val stone = Stone.from(Row("1"), Column("A"))
        board.setStoneState(player, stone)

        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        val isOMock =
            player.processTurn(
                board.stoneStates,
                row,
                column,
            )
        Assertions.assertThat(isOMock.getOrThrow()::class.java).isEqualTo(FinishedTurn::class.java)
    }

    @Test
    fun `White Turn일 때 돌을 놓으면, Board가 오목이 아닌 경우 Black Turn을 반환한다`() {
        board.makeStones(
            player = player,
            stones =
                arrayOf(
                    Stone.from(Row("2"), Column("B")),
                    Stone.from(Row("1"), Column("B")),
                    Stone.from(Row("2"), Column("A")),
                    Stone.from(Row("3"), Column("A")),
                    Stone.from(Row("5"), Column("A")),
                ),
        )

        val stone = Stone.from(Row("1"), Column("A"))
        val row = stone.row.getIndex()
        val column = stone.column.getIndex()

        val isOMock =
            player.processTurn(
                board.stoneStates,
                row,
                column,
            )
        Assertions.assertThat(isOMock.getOrThrow()::class.java).isEqualTo(BlackTurn::class.java)
    }
}
