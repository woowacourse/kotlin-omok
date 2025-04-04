package woowacourse.omok.domain.model.state

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.domain.model.Game
import woowacourse.omok.domain.repository.GameRepository

class GameRepositoryTest {
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository = FakeGameRepository(mutableMapOf(1L to Game(1L, "방1")))
    }

    @Test
    fun `게임을 추가한다`() {
        gameRepository.insert("방2")
        assertThat(gameRepository.getAll()).isEqualTo(listOf(Game(1L, "방1"), Game(2L, "방2")))
    }

    @Test
    fun `전체 게임을 가져온다`() {
        assertThat(gameRepository.getAll()).isEqualTo(listOf(Game(1L, "방1")))
    }

    @Test
    fun `게임을 제거한다`() {
        gameRepository.delete(1L)
        assertThat(gameRepository.getAll()).isEqualTo(listOf<Game>())
    }

    class FakeGameRepository(private val games: MutableMap<Long, Game>) : GameRepository {
        override fun insert(name: String) {
            val newId = games.size.toLong() + 1
            games[newId] = Game(newId, name)
        }

        override fun getAll(): List<Game> = games.values.toList()

        override fun delete(id: Long) {
            games.remove(id)
        }
    }
}
