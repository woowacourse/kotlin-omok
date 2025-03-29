package woowacourse.omok.domain.model.usecase

import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import woowacourse.omok.domain.model.game.OmokGameEntity
import woowacourse.omok.domain.model.omokboard.OmokBoard
import woowacourse.omok.domain.model.omokboard.PointState
import woowacourse.omok.domain.model.omokboard.Position
import woowacourse.omok.domain.model.player.StoneColor
import woowacourse.omok.domain.repository.OmokGameRepository
import woowacourse.omok.domain.usecase.GetOmokGameUseCase

class GetOmokGameUseCaseTest {
    private lateinit var omokGameRepository: OmokGameRepository
    private lateinit var getOmokGameUseCase: GetOmokGameUseCase

    @BeforeEach
    fun setup() {
        omokGameRepository = mock()
        getOmokGameUseCase = GetOmokGameUseCase(omokGameRepository)
    }

    @Test
    fun `저장된 게임 데이터로 OmokGame을 생성한다`() {
        runBlocking {
            // given
            val position = Position(1, 1)
            val board = OmokBoard(mutableMapOf(position to PointState.OCCUPIED_BLACK))
            val lastTurn = StoneColor.BLACK
            val entity = OmokGameEntity(lastTurn, board)

            whenever(omokGameRepository.fetchGame()).thenReturn(entity)

            // when
            val omokGame = getOmokGameUseCase()

            // then
            assertThat(omokGame.board.find(position)).isEqualTo(PointState.OCCUPIED_BLACK)
            assertThat(omokGame.currentTurn).isEqualTo(StoneColor.BLACK)
        }
    }
}
