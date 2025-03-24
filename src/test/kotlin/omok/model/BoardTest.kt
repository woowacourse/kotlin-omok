package omok.model

import omok.model.adapter.BlackRenjuRuleAdapter
import omok.model.stone.Stone
import omok.model.stone.StoneColor
import omok.model.stone.StoneColor.BLACK
import omok.model.stone.Stones
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BoardTest {
    @Test
    fun `보드에 돌을 착수할 수 있다`() {
        val board = Board()
        val stone = Stone.of(8, 8, StoneColor.BLACK)
        board.place(stone)

        val actual = stone in board.stones

        val expected = true
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드에 쌍삼 금수를 착수할 수 없다`() {
        val board =
            Board(
                blackStones =
                    Stones(
                        setOf(
                            Stone.of(8, 8, BLACK),
                            Stone.of(9, 8, BLACK),
                            Stone.of(10, 6, BLACK),
                            Stone.of(10, 7, BLACK),
                        ),
                        ruleAdapter = BlackRenjuRuleAdapter(),
                    ),
            )

        assertThrows<IllegalArgumentException> {
            board.place(Stone.of(10, 8, BLACK))
        }
    }

    @Test
    fun `보드에 쌍사 금수를 착수할 수 없다`() {
        val board =
            Board(
                blackStones =
                    Stones(
                        setOf(
                            Stone.of(8, 8, BLACK),
                            Stone.of(9, 8, BLACK),
                            Stone.of(10, 8, BLACK),
                            Stone.of(11, 7, BLACK),
                            Stone.of(11, 6, BLACK),
                            Stone.of(11, 5, BLACK),
                        ),
                        ruleAdapter = BlackRenjuRuleAdapter(),
                    ),
            )

        assertThrows<IllegalArgumentException> {
            board.place(Stone.of(11, 8, BLACK))
        }
    }

    @Test
    fun `보드에 장목 금수를 착수할 수 없다`() {
        val board =
            Board(
                blackStones =
                    Stones(
                        setOf(
                            Stone.of(8, 8, BLACK),
                            Stone.of(9, 8, BLACK),
                            Stone.of(10, 8, BLACK),
                            Stone.of(11, 8, BLACK),
                            Stone.of(13, 8, BLACK),
                        ),
                        ruleAdapter = BlackRenjuRuleAdapter(),
                    ),
            )

        assertThrows<IllegalArgumentException> {
            board.place(Stone.of(12, 8, BLACK))
        }
    }

    @Test
    fun `보드에 오목이 있는지 알 수 있다`() {
        val board =
            Board(
                blackStones =
                    Stones(
                        setOf(
                            Stone.of(8, 8, BLACK),
                            Stone.of(9, 8, BLACK),
                            Stone.of(10, 8, BLACK),
                            Stone.of(11, 8, BLACK),
                        ),
                        ruleAdapter = BlackRenjuRuleAdapter(),
                    ),
            )

        val actual = board.hasOmok(Stone.of(12, 8, BLACK))

        val expected = true
        assertThat(actual).isEqualTo(expected)
    }
}
