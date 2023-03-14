package study

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class StudyTest() {

    @Test
    fun `인터페이스 리스트`() {
        val whiteStone: Stone = WhiteStone(5)
        assertThat(whiteStone is WhiteStone).isEqualTo(true)
    }
}

interface Stone

interface Stones {
    val stones: List<Stone>
}

class WhiteStone(val position: Int) : Stone
