package omok.model.game

import omok.model.stone.Coordinate
import omok.model.stone.GoStone
import omok.model.stone.GoStoneColor
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `입력받은 위치에 이미 바둑돌이 있다면 오류가 발생한다`() {
        val board = Board()
        board.addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))

        assertThatIllegalArgumentException()
            .isThrownBy { board.canAdd(Coordinate.createWithMark("H8")) }
            .withMessageContaining("해당 위치에 이미 바둑돌이 있습니다.")
    }

    @Test
    fun `마지막으로 놓은 돌을 가져온다`() {
        val board = Board().apply {
            addStone(GoStone(GoStoneColor.BLACK, Coordinate.createWithMark("H8")))
            addStone(GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H9")))
        }

        val actual = GoStone(GoStoneColor.WHITE, Coordinate.createWithMark("H9"))

        assertThat(board.lastPlacedStone).isEqualTo(actual)
    }
}
