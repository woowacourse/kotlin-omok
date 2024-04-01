package woowacourse.omok.model.database

import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

class PlacementDaoTest {
    private lateinit var dao: PlacementDao

    @BeforeEach
    fun setUp() {
        dao = PlacementDao(ApplicationProvider.getApplicationContext())
    }

    @AfterEach
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
            { assertThat(actual.gameId).isEqualTo(1) },
            { assertThat(actual.color).isEqualTo("BLACK") },
            { assertThat(actual.index).isEqualTo(0) },
        )
    }

    @Test
    fun findAllTest() {
        val actual = dao.findAll(1)
        assertThat(actual).isEmpty()
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
                assertThat(actual[0]).isEqualTo(
                    Placement(
                        placementId = 1,
                        gameId = 1,
                        color = "BLACK",
                        index = 0,
                    ),
                )
            },
            {
                assertThat(actual[1]).isEqualTo(
                    Placement(
                        placementId = 2,
                        gameId = 1,
                        color = "WHITE",
                        index = 196,
                    ),
                )
            },
        )
    }
}
