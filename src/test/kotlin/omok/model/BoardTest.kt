package omok.model

import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `입력받은 위치에 이미 바둑돌이 있다면 오류가 발생한다`() {
        val board = Board()
        board.addStone(GoStoneColor.BLACK, Coordinate("H8"))

        assertThatIllegalArgumentException()
            .isThrownBy { board.canAdd(Coordinate("H8")) }
            .withMessageContaining("해당 위치에 이미 바둑돌이 있습니다.")
    }
}
