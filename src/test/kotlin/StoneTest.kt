import domain.stone.Position
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Test

class StoneTest {

    @Test
    fun `현재 돌이 놓인 위치의 x좌표를 알 수 있다`() {
        val stone = Stone(10, 11)
        val actual = stone.position
        val expected = Position(10 - 1, 11 - 1)

        assertThat(actual).isEqualTo(expected)
    }
}
