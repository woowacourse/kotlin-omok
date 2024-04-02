package omok.model.stone

import omok.model.position.Position
import omok.model.resetBoard
import omok.model.result.PutResult
import omok.model.stone.WhiteStone.changeStone
import omok.model.stone.WhiteStone.value
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BlackStoneTest {
    @BeforeEach
    fun setUp() {
        resetBoard()
    }

    @Test
    fun `오목인 경우 true를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position.of('A', 1))
        stone.putStone(Position.of('A', 2))
        stone.putStone(Position.of('A', 3))
        stone.putStone(Position.of('A', 4))
        stone.putStone(Position.of('A', 5))

        val actual = stone.findOmok(Position.of('A', 5))
        val expected = true

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `오목이 아닌 경우 false를 반환한다`() {
        val stone = BlackStone
        stone.putStone(Position.of('A', 1))
        stone.putStone(Position.of('A', 2))
        stone.putStone(Position.of('A', 3))
        stone.putStone(Position.of('A', 4))
        stone.putStone(Position.of('A', 6))

        val actual = stone.findOmok(Position.of('A', 6))
        val expected = false

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 색을 바꾼다`() {
        val stone = BlackStone

        val actual = stone.changeStone()
        val expected = WhiteStone

        assertThat(actual).isSameAs(expected)
    }

    @Test
    fun `돌의 색이 흑돌일 경우 '흑' 이라는 문자를 반환한다`() {
        val stone = BlackStone

        val actual = stone.value()
        val expected = "흑"

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `돌의 색이 백돌일 경우 '백' 이라는 문자를 반환한다`() {
        val stone = WhiteStone

        val actual = stone.value()
        val expected = "백"

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `이미 돌이 놓인 자리에 돌을 놓을 경우 그에 따른 결과 상태를 반환한다`() {
        val stone = BlackStone
        val position = Position(0, 0)

        val actual = stone.putStone(position)
        val expected = PutResult.Failure

        assertThat(actual).isEqualTo(expected)
    }
}
