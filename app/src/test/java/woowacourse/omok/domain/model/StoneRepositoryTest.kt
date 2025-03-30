package woowacourse.omok.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.stone.Stone
import woowacourse.omok.domain.model.stone.StoneType
import woowacourse.omok.domain.model.stone.Stones
import woowacourse.omok.domain.repository.StoneRepository
import woowacourse.omok.stoneOneAndOne
import woowacourse.omok.stoneSixAndSix
import woowacourse.omok.whiteStoneOneAndOne

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
    fun `마지막 돌의 타입을 가져온다`() {
        stoneRepository.insert(whiteStoneOneAndOne)
        val stone = stoneRepository.lastStoneType()
        assertThat(stone).isEqualTo(StoneType.WHITE)
    }

    @Test
    fun `모든 돌을 제거한다`() {
        stoneRepository.clear()
        val stones = stoneRepository.allInBoardSize(15).typeStones(StoneType.BLACK)
        assertThat(stones).isEqualTo(listOf<Stone>())
    }

    class FakeStoneRepository(private val stones: MutableList<Stone>) : StoneRepository {
        override fun insert(stone: Stone) {
            stones.add(stone)
        }

        override fun lastStoneType(): StoneType = stones.last().stoneType

        override fun allInBoardSize(size: Int): Stones = Stones(stones)

        override fun clear() = stones.clear()
    }
}
