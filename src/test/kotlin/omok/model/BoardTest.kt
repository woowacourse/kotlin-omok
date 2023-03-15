package omok.model

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `입력받은 위치에 이미 바둑돌이 있다면 오류가 발생한마`() {
        val board = Board()
        board.addStone(GoStoneColor.BLACK) { Coordinate("H8") }

        assertThatIllegalArgumentException()
            .isThrownBy { board.addStone(GoStoneColor.WHITE) { Coordinate("H8") } }
            .withMessageContaining("해당 위치에 이미 바둑돌이 있습니다.")
    }

    @Test
    fun `마지막으로 놓은 돌을 가져온다`() {
        val board = Board()
        board.addStone(GoStoneColor.BLACK) { Coordinate("H8") }
        board.addStone(GoStoneColor.WHITE) { Coordinate("H9") }

        val actual = Coordinate("H9")

        assertThat(board.lastPutCoordinate).isEqualTo(actual)
    }
}
