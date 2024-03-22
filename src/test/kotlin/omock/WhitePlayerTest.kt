package omock

import omock.model.Board
import omock.model.Column
import omock.model.Player
import omock.model.Row
import omock.model.Stone
import omock.model.WhitePlayer
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WhitePlayerTest {
    private lateinit var player: Player
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        player = WhitePlayer()
        board = Board.from()
    }

    @Test
    fun `플레이어가 돌을 놓을 때, Board는 오목인 경우 true를 반환한다`() {
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

        Assertions.assertThat(player.judgementResult(visited)).isTrue()
    }

    @Test
    fun `플레이어가 돌을 놓을 때, Board는 오목이 아닌 경우 false 반환한다`() {
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

        Assertions.assertThat(player.judgementResult(visited)).isFalse()
    }
}
