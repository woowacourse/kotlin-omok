package woowacourse.omok

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.model.Board
import woowacourse.omok.model.game.GameState
import woowacourse.omok.model.game.ViolationResult
import woowacourse.omok.model.stone.Point
import woowacourse.omok.model.stone.Stone
import woowacourse.omok.model.stone.StoneColor.BLACK
import woowacourse.omok.model.stone.StoneColor.WHITE
import woowacourse.omok.model.stone.Stones

class BoardTest {
    /*
 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 12 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
 11 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
 10 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  9 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  8 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     */
    @Test
    fun `보드에 오목이 있는지 알 수 있다`() {
        val board =
            Board(
                Stones(
                    setOf(
                        Stone(8, 8, BLACK),
                        Stone(9, 8, BLACK),
                        Stone(10, 8, BLACK),
                        Stone(11, 8, BLACK),
                    ),
                ),
            )

        val actual = board.gameState(Stone(12, 8, BLACK))

        val expected = GameState.BLACK_OMOK

        assertThat(actual).isEqualTo(expected)
    }

    /*
 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 10 ├──┼──┼──┼──┼──●──●──X──┼──┼──┼──┼──┼──┼──┤
  9 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  8 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     */
    @Test
    fun `보드에 쌍삼 금수를 착수할 수 없다`() {
        val board =
            Board(
                Stones(
                    setOf(
                        Stone(8, 8, BLACK),
                        Stone(9, 8, BLACK),
                        Stone(10, 6, BLACK),
                        Stone(10, 7, BLACK),
                    ),
                ),
            )

        val actual = board.checkViolation(Stone(10, 8, BLACK))

        val expected = ViolationResult.FoulConditionResult.DoubleThree()

        assertThat(actual).isEqualTo(expected)
    }

    /*
 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 11 ├──┼──┼──┼──●──●──●──X──┼──┼──┼──┼──┼──┼──┤
 10 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  9 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  8 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     */
    @Test
    fun `보드에 쌍사 금수를 착수할 수 없다`() {
        val board =
            Board(
                Stones(
                    setOf(
                        Stone(8, 8, BLACK),
                        Stone(9, 8, BLACK),
                        Stone(10, 8, BLACK),
                        Stone(11, 7, BLACK),
                        Stone(11, 6, BLACK),
                        Stone(11, 5, BLACK),
                    ),
                ),
            )

        val actual = board.checkViolation(Stone(11, 8, BLACK))

        val expected = ViolationResult.FoulConditionResult.DoubleFour()

        assertThat(actual).isEqualTo(expected)
    }

    /*
 15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
 14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
 13 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
 12 ├──┼──┼──┼──┼──┼──┼──X──┼──┼──┼──┼──┼──┼──┤
 11 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
 10 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  9 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  8 ├──┼──┼──┼──┼──┼──┼──●──┼──┼──┼──┼──┼──┼──┤
  7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
  1 └──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
    A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
     */
    @Test
    fun `보드에 장목 금수를 착수할 수 없다`() {
        val board =
            Board(
                Stones(
                    setOf(
                        Stone(8, 8, BLACK),
                        Stone(9, 8, BLACK),
                        Stone(10, 8, BLACK),
                        Stone(11, 8, BLACK),
                        Stone(13, 8, BLACK),
                    ),
                ),
            )

        val actual = board.checkViolation(Stone(12, 8, BLACK))

        val expected = ViolationResult.FoulConditionResult.Overline()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드가 가득 찬 상태인지 확인할 수 있다`() {
        val board =
            Board(
                Stones(
                    (1..15)
                        .flatMap { row ->
                            (1..15).flatMap { col ->
                                listOf(Stone(row, col, BLACK))
                            }
                        }.toSet(),
                ),
            )

        val actual = board.checkViolation(Stone(8, 8, BLACK))

        val expected = ViolationResult.InvalidMoveResult.FullBoard()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드의 특정 위치에 이미 돌이 있는 상태인지 확인할 수 있다`() {
        val board =
            Board(
                Stones(
                    setOf(
                        Stone(8, 8, BLACK),
                    ),
                ),
            )

        val actual = board.checkViolation(Stone(8, 8, WHITE))

        val expected = ViolationResult.InvalidMoveResult.OccupiedPoint()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `보드의 바깥에 돌을 두려는 상태인지 확인할 수 있다`() {
        val board = Board()

        val actual = board.checkViolation(Stone(16, 16, WHITE))

        val expected = ViolationResult.InvalidMoveResult.OutOfBoard()

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이번 차례에 두어야 하는 돌을 받을 수 있다`() {
        val board = Board()

        val actual = board.currentStone(Point(8, 8))

        val expected = Stone(Point(8, 8), BLACK)

        assertThat(actual).isEqualTo(expected)
    }
}
