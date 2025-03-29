package woowacourse.omok.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.omok.STONE_1A_BLACK
import woowacourse.omok.STONE_1B_WHITE
import woowacourse.omok.STONE_1C_BLACK
import woowacourse.omok.STONE_1D_WHITE
import woowacourse.omok.domain.position.Col
import woowacourse.omok.domain.position.Position
import woowacourse.omok.domain.position.Row

class OmokRepositoryTest {
    private lateinit var omokRepository: OmokRepository
    private lateinit var stoneDataSource: StoneFakeDataSource

    @BeforeEach
    fun setUp() {
        stoneDataSource = StoneFakeDataSource()
        omokRepository = OmokRepository(stoneDataSource)
    }

    @Test
    fun `데이터베이스에 돌을 추가한다`() {
        omokRepository.insert(STONE_1A_BLACK)
        val expected = 1

        assertThat(stoneDataSource.stones.size).isEqualTo(expected)
    }

    @Test
    fun `데이터베이스에 존재하는 위치인지 판단한다`() {
        omokRepository.insert(STONE_1A_BLACK)
        val existedPosition = Position(Row.from(1), Col.from(1))
        val notExistPosition = Position(Row.from(1), Col.from(2))

        assertAll(
            { assertThat(omokRepository.findStoneByPosition(existedPosition)).isNotNull() },
            { assertThat(omokRepository.findStoneByPosition(notExistPosition)).isNull() },
        )
    }

    @Test
    fun `데이터베이스를 초기화한다`() {
        omokRepository.insert(STONE_1A_BLACK)
        omokRepository.insert(STONE_1B_WHITE)
        omokRepository.insert(STONE_1C_BLACK)
        omokRepository.insert(STONE_1D_WHITE)

        omokRepository.removeAll()

        assertThat(stoneDataSource.stones.size).isZero()
    }
}
