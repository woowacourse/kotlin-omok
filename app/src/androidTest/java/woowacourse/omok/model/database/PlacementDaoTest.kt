package woowacourse.omok.model.database

import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertAll

class PlacementDaoTest {
    private lateinit var dao: PlacementDao

    @Before
    fun setUp() {
        dao = PlacementDao(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        dao.drop()
    }

    @Test
    fun saveTest() {
        val placement =
            Placement(
                gameId = 1,
                color = "BLACK",
                index = 0,
            )
        val actual = dao.save(placement)
        assertAll(
            { assertEquals(actual.gameId, 1) },
            { assertEquals(actual.color, "BLACK") },
            { assertEquals(actual.index, 0) },
        )
    }

    @Test
    fun findAllTest() {
        val actual = dao.findAll(1)
        assertEquals(actual, emptyList<Placement>())
    }

    @Test
    fun saveAndFindAllTest() {
        val placements =
            listOf(
                Placement(gameId = 1, color = "BLACK", index = 0),
                Placement(gameId = 1, color = "WHITE", index = 196),
            )
        placements.forEach { dao.save(it) }
        val actual = dao.findAll(1)
        assertAll(
            {
                assertEquals(
                    actual[0],
                    Placement(placementId = 1, gameId = 1, color = "BLACK", index = 0),
                )
            },
            {
                assertEquals(
                    actual[1],
                    Placement(placementId = 2, gameId = 1, color = "WHITE", index = 196),
                )
            },
        )
    }
}
