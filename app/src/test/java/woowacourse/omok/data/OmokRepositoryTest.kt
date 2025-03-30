package woowacourse.omok.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.STONE_1A_BLACK
import woowacourse.omok.STONE_1B_WHITE
import woowacourse.omok.STONE_1C_BLACK
import woowacourse.omok.STONE_1D_WHITE

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
    fun `데이터베이스를 초기화한다`() {
        omokRepository.insert(STONE_1A_BLACK)
        omokRepository.insert(STONE_1B_WHITE)
        omokRepository.insert(STONE_1C_BLACK)
        omokRepository.insert(STONE_1D_WHITE)

        omokRepository.removeAll()

        assertThat(stoneDataSource.stones.size).isZero()
    }

    @Test
    fun `데이터베이스에 존재하는 모든 돌을 가져온다`() {
        omokRepository.insert(STONE_1A_BLACK)
        omokRepository.insert(STONE_1B_WHITE)
        omokRepository.insert(STONE_1C_BLACK)
        omokRepository.insert(STONE_1D_WHITE)
        val expected = 4

        assertThat(omokRepository.findAllStone().size).isEqualTo(expected)

        omokRepository.removeAll()
        assertThat(omokRepository.findAllStone().size).isZero()
    }
}
