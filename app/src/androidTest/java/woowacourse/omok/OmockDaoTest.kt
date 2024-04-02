package woowacourse.omok

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.data.OmokDAO

@RunWith(AndroidJUnit4::class)
class OmokDAOTest {
    private val context: Context = ApplicationProvider.getApplicationContext()
    private val omokDAO: OmokDAO = OmokDAO(context)

    @After
    fun tearDown() {
        omokDAO.resetAllCoordinates()
    }

    @Test
    fun insertCoordinate() {
        // given
        val x = 1
        val y = 1

        // when
        omokDAO.insertCoordinate(x, y)

        // then
        val coordinates = omokDAO.getAllCoordinates()
        Assert.assertTrue(coordinates.contains(x to y))
    }

    @Test
    fun getAllCoordinates() {
        // given
        val expectedCoordinates = listOf(1 to 1, 2 to 2, 3 to 3)
        expectedCoordinates.forEach { (x, y) ->
            omokDAO.insertCoordinate(x, y)
        }

        // when
        val actualCoordinates = omokDAO.getAllCoordinates()

        // then
        Assert.assertEquals(expectedCoordinates, actualCoordinates)
    }

    @Test
    fun resetAllCoordinates() {
        // given
        val coordinates = listOf(1 to 1, 2 to 2, 3 to 3)
        coordinates.forEach { (x, y) ->
            omokDAO.insertCoordinate(x, y)
        }

        // when
        omokDAO.resetAllCoordinates()

        // then
        val actualCoordinates = omokDAO.getAllCoordinates()
        Assert.assertTrue(actualCoordinates.isEmpty())
    }
}
