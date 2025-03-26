package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BoardTest {
    private lateinit var board: Board

    @BeforeEach
    fun setUp() {
        board = Board(15)
    }

    @ParameterizedTest
    @ValueSource(ints = [0, 16, -1])
    fun `바둑판 범위안에 숫자인지 검증한다`(value: Int) {
        assertThat(board.inRange(value)).isFalse()
    }
}
