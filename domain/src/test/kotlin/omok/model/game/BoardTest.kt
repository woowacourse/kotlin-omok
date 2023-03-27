package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `입력받은 위치에 이미 바둑돌이 있다면 false를 반환한다`() {
        val board = Board()
        board.addStone(GoStone(GoStoneColor.BLACK, Coordinate.of("H8")))

        assertThat(board.canAdd(Coordinate.of("H8"))).isFalse
    }

    @Test
    fun `마지막으로 놓은 돌을 가져온다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.of("H8")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.of("H9")))
        }

        val actual = GoStone(GoStoneColor.WHITE, Coordinate.of("H9"))

        assertThat(board.lastPlacedStone).isEqualTo(actual)
    }
}
