package omock

import omock.model.Board
import omock.model.Column
import omock.model.Row
import omock.model.state.Stone
import omock.model.turn.BlackTurn
import omock.model.turn.FinishedTurn
import omock.model.turn.Turn
import omock.model.turn.WhiteTurn
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
        val visited = board.loadMap(stone)

        Assertions.assertThat(player.judgementResult(visited)::class.java).isEqualTo(FinishedTurn::class.java)
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
        val visited = board.loadMap(stone)

        Assertions.assertThat(player.judgementResult(visited)::class.java).isEqualTo(BlackTurn::class.java)
    }
}
