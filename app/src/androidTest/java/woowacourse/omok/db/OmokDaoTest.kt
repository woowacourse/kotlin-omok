package woowacourse.omok.db

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.domain.model.Point
import woowacourse.omok.domain.model.Stone
import woowacourse.omok.domain.model.StoneType

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private lateinit var omokDao: OmokDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        omokDao = OmokDao(context)
    }

    @After
    fun tearDown() {
        omokDao.resetGame()
    }

    @Test
    fun `사용자가_둔_오목돌_저장_테스트`() {
        val actual = omokDao.saveStone(Stone(StoneType.BLACK, Point(0, 1)))
        assertThat(actual > 0).isTrue()
    }

    @Test
    fun `게임_정보_리셋_테스트`() {
        omokDao.saveStone(Stone(StoneType.BLACK, Point(0, 1)))
        omokDao.saveStone(Stone(StoneType.WHITE, Point(1, 1)))
        omokDao.saveStone(Stone(StoneType.BLACK, Point(3, 1)))
        omokDao.resetGame()
        val actual = omokDao.getStonesFromDatabase().getOrNull()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `저장된_오목돌_조회_테스트__비어_있는_경우`() {
        val actual = omokDao.getStonesFromDatabase().getOrNull()
        assertThat(actual).isEmpty()
    }

    @Test
    fun `저장된_오목돌_조회_테스트__저장된_데이터가_있는_경우`() {
        omokDao.saveStone(Stone(StoneType.BLACK, Point(0, 1)))
        val actual = omokDao.getStonesFromDatabase().getOrNull()
        assertThat(actual?.get(0)).isEqualTo(Stone(StoneType.BLACK, Point(0, 1)))
    }
}
