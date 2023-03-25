package domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class StoneTest {
    @Test
    fun `좌표를 저장한다`() {
        assertDoesNotThrow { Stone(1, 3) }
    }

    @Test
    fun `15, 0이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException> { Stone(15, 0) }
    }

    @Test
    fun `0, 15이면 예외가 발생한다`() {
        assertThrows<IllegalArgumentException>("잘못된 위치입니다.") { Stone(0, 15) }
    }

    @Test
    fun `create을 사용하면 변환된 인덱스로 Stone이 생성된다`() {
        val stone = Stone.create('A', 3)
        assertThat(stone.row).isEqualTo(12)
        assertThat(stone.column).isEqualTo(0)
    }
}
