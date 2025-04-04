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
        stoneRepository = FakeStoneRepository(listOf(1L to stoneOneAndOne).toMutableList())
    }

    @Test
    fun `돌을 추가한다`() {
        stoneRepository.insert(1L, stoneSixAndSix)
        assertThat(stoneRepository.allInBoardSize(1L, Board(15)).hasStone(stoneSixAndSix)).isTrue()
    }

    @Test
    fun `한 게임의 전체 돌을 가져온다`() {
        stoneRepository.insert(1L, stoneSixAndSix)
        stoneRepository.insert(2L, stoneSixAndSix)
        val stones = stoneRepository.allInBoardSize(1L, Board(15)).typeStones(StoneType.BLACK)
        assertThat(stones).isEqualTo(listOf(stoneOneAndOne, stoneSixAndSix))
    }

    @Test
    fun `한 게임의 마지막 돌의 타입을 가져온다`() {
        stoneRepository.insert(1L, whiteStoneOneAndOne)
        stoneRepository.insert(2L, whiteStoneOneAndOne)
        val stone = stoneRepository.lastStoneType(1L, Board(15))
        assertThat(stone).isEqualTo(StoneType.WHITE)
    }

    @Test
    fun `한 게임의 모든 돌을 제거한다`() {
        stoneRepository.insert(2L, whiteStoneOneAndOne)
        stoneRepository.clear(1L)
        val stones = stoneRepository.allInBoardSize(2L, Board(15)).typeStones(StoneType.WHITE)
        assertThat(stones).isEqualTo(listOf(whiteStoneOneAndOne))
    }

    class FakeStoneRepository(private val stones: MutableList<Pair<Long, Stone>>) :
        StoneRepository {
        override fun insert(
            gameId: Long,
            stone: Stone,
        ) {
            stones.add(gameId to stone)
        }

        override fun lastStoneType(
            gameId: Long,
            board: Board,
        ): StoneType {
            return stones.findLast { (stoneGameId, _) -> stoneGameId == gameId }?.second?.stoneType
                ?: StoneType.BLACK
        }

        override fun allInBoardSize(
            gameId: Long,
            board: Board,
        ): Stones = Stones(stones.filter { (stoneGameId, _) -> stoneGameId == gameId }.map { it.second })

        override fun clear(gameId: Long) {
            stones.removeAll { it.first == gameId }
        }
    }
}
