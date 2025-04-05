package omok.view

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.Board
import woowacourse.omok.domain.Position
import woowacourse.omok.domain.StoneType
import woowacourse.omok.view.BoardRenderer

class BoardRendererTest {
    @Test
    fun test1() {
        val board = Board.initial()
        board.put(Position(1, 1), StoneType.WHITE)
        val actual = BoardRenderer.render(board)
        val expected =
            """
            15 ┌──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┬──┐
            14 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            13 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            12 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            11 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
            10 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             9 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             8 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             7 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             6 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             5 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             4 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             3 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             2 ├──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┼──┤
             1 ○──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┴──┘
               A  B  C  D  E  F  G  H  I  J  K  L  M  N  O
            """.trimIndent()
        assertThat(actual).isEqualTo(expected)
    }
}
