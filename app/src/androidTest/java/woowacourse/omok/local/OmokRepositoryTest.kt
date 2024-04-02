package woowacourse.omok.local

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.omok.local.db.OmokDao
import woowacourse.omok.local.repository.OmokRepository
import woowacourse.omok.local.repository.OmokRepositoryImpl
import woowacourse.omok.presentation.model.Omok

@RunWith(AndroidJUnit4::class)
class OmokRepositoryTest {
    private lateinit var repository: OmokRepository

    @Before
    fun setUp() {
        val omokDao = OmokDao(ApplicationProvider.getApplicationContext())
        repository = OmokRepositoryImpl(omokDao)
    }

    @After
    fun tearDown() {
        repository.deleteAllOmok()
    }

    @Test
    fun `데이터베이스에_오목_좌표를_저장하면_오목_좌표가_저장된다`() {
        val omok = makeOmok("1A")
        repository.insertOmok(omok)

        val actual = repository.selectOmok().getOrThrow()

        assertEquals(
            actual,
            makeOmoks("1A"),
        )
    }

    @Test
    fun `데이터베이스에_오목_좌표를_저장하지_않으면_데이터베이스에는_아무것도_없다`() {
        val actual = repository.selectOmok().getOrThrow()

        assertEquals(
            actual,
            listOf<Omok>(),
        )
    }

    @Test
    fun `데이터베이스에_오목_좌표를_저장하고_동일한_오목_좌표를_제거하면_데이터베이스에는_아무것도_없다`() {
        val omok = makeOmok("1A")

        repository.insertOmok(omok)

        val actual1 = repository.selectOmok().getOrThrow()

        assertEquals(actual1, makeOmoks("1A"))

        repository.deleteOmok(omok)

        val actual2 = repository.selectOmok().getOrThrow()

        assertEquals(
            actual2,
            listOf<Omok>(),
        )
    }

    @Test
    fun `데이터베이스에_5개의_오목_좌표를_저장하고_동일한_오목_좌표를_모두_제거하면_데이터베이스에는_아무것도_없다`() {
        val omoks = makeOmoks("1A", "2A", "3A", "4A", "5A")

        omoks.forEach { omok ->
            repository.insertOmok(omok)
        }

        val actual1 = repository.selectOmok().getOrThrow()

        assertEquals(actual1, omoks)

        repository.deleteAllOmok()

        val actual2 = repository.selectOmok().getOrThrow()

        assertEquals(actual2, listOf<Omok>())
    }
}

fun makeOmoks(vararg omoks: String): List<Omok> {
    return omoks.map { omok -> makeOmok(omok) }
}

fun makeOmok(omok: String): Omok {
    return Omok(omok[0].toString(), omok[1].toString())
}
