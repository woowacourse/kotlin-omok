package woowacourse.omok.domain.board

import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BoardSizeTest {
    @ValueSource(ints = [14, 26])
    @ParameterizedTest
    fun `바둑판의 크기가 15~25사이가 아니라면 예외가 발생한다`(size: Int) {
        assertThrows<IllegalArgumentException> { BoardSize(size) }
    }
}
