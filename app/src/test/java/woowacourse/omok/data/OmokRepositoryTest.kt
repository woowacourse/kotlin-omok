package woowacourse.omok.data

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.STONE_1A_BLACK

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
}
