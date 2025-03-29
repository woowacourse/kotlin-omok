package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.position.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.stoneOneAndOne
import woowacourse.omok.stoneSixAndSix

class StoneRepositoryTest {
    private lateinit var stoneRepository: StoneRepository

    @BeforeEach
    fun setUp() {
        stoneRepository = FakeStoneRepository(listOf(stoneOneAndOne).toMutableList())
    }

    @Test
    fun `돌을 추가한다`() {
        stoneRepository.insert(stoneSixAndSix)
        assertThat(stoneRepository.allInBoardSize(15).hasStone(stoneSixAndSix)).isTrue()
    }

    @Test
    fun `전체 돌을 가져온다`() {
        val stones = stoneRepository.allInBoardSize(15).typeStones(StoneType.BLACK)
        assertThat(stones).isEqualTo(listOf(stoneOneAndOne))
    }

    @Test
    fun `모든 돌을 제거한다`() {
        stoneRepository.clear()
        val stones = stoneRepository.allInBoardSize(15).typeStones(StoneType.BLACK)
        assertThat(stones).isEqualTo(listOf<Stone>())
    }

    class FakeStoneRepository(val stones: MutableList<Stone>) : StoneRepository {
        override fun insert(stone: Stone) {
            stones.add(stone)
        }

        override fun allInBoardSize(size: Int): Stones = Stones(stones)

        override fun clear() = stones.clear()
    }
}
