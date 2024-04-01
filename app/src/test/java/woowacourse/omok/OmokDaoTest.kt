package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.OmokDAO

@RunWith(AndroidJUnit4::class)
class OmokDaoTest {
    private val applicationContext: Context by lazy { ApplicationProvider.getApplicationContext() }
    private lateinit var omokDAO: OmokDAO

    @Before
    fun setUp() {
        omokDAO = OmokDAO(applicationContext)
    }

    @After
    fun tearDown() {
        omokDAO.resetAllCoordinates()
    }

    @Test
    fun `초기상태에서는_기록은_빈리스트이다`() {
        // given

        // actual
        val actual = omokDAO.getAllCoordinates()

        // expected
        val expected = emptyList<Pair<Int, Int>>()
        Assert.assertTrue(actual == expected)
    }

    @Test
    fun `11을_둔다면_기록은_11을_반환한다`() {
        // given
        omokDAO.insertCoordinate(1, 1)

        // actual
        val actual = omokDAO.getAllCoordinates()

        // expected
        val expected = listOf<Pair<Int, Int>>((1 to 1))
        Assert.assertTrue(actual == expected)
    }

    @Test
    fun `11을_두고_22를_둔다면_기록은_11_22를_반환한다`() {
        // given
        omokDAO.insertCoordinate(1, 1)
        omokDAO.insertCoordinate(2, 2)

        // actual
        val actual = omokDAO.getAllCoordinates()

        // expected
        val expected = listOf<Pair<Int, Int>>((1 to 1), (2 to 2))
        Assert.assertTrue(actual == expected)
    }
}
