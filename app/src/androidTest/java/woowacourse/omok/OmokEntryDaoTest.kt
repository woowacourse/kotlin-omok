package woowacourse.omok

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao

@RunWith(AndroidJUnit4::class)
class OmokEntryDaoTest {
    private val dao = OmokEntryDao(getApplicationContext())

    @After
    fun tearDown() {
        dao.drop()
    }

    @DisplayName("사용자가 돌을 두었다면 돌의 유형과 위치를 데이터베이스에 저장한다")
    @Test
    fun save() {
        val entry = OmokEntry("흑", 2, 5)
        val actual = dao.save(entry)

        assertThat(actual.id).isGreaterThan(0)
        assertThat(actual.stoneType).isEqualTo("흑")
        assertThat(actual.row).isEqualTo(2)
        assertThat(actual.column).isEqualTo(5)
    }

    @DisplayName("앱 실행 시 모든 게임 데이터를 데이터베이스로부터 가지고 온다.")
    @Test
    fun findAll() {
        val omokEntries =
            listOf(
                OmokEntry("흑", 4, 6),
                OmokEntry("백", 5, 10),
            )
        saveEntries(omokEntries)

        val actual = dao.findAll()
        assertThat(actual.size).isEqualTo(2)
    }

    @DisplayName("오목일 때 사용자가 다시 시작 버튼을 누르면 데이터베이스의 모든 게임 데이터를 삭제한다.")
    @Test
    fun drop() {
        val omokEntries =
            listOf(
                OmokEntry("흑", 4, 5),
                OmokEntry("백", 5, 8),
                OmokEntry("흑", 10, 13),
                OmokEntry("백", 12, 6),
                OmokEntry("흑", 2, 4),
                OmokEntry("백", 7, 9),
            )
        saveEntries(omokEntries)
        dao.drop()
        val actual = dao.findAll()
        assertThat(actual).isEmpty()
    }

    private fun saveEntries(omokEntries: List<OmokEntry>) {
        omokEntries.forEach {
            dao.save(it)
        }
    }
}
