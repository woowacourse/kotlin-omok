package woowacourse.omok

import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import woowacourse.omok.db.OmokEntry
import woowacourse.omok.db.OmokEntryDao

@RunWith(AndroidJUnit4::class)
class OmokEntryDaoTest {
    private val dao = OmokEntryDao(getApplicationContext())

    @DisplayName("사용자가 돌을 두었다면 돌의 유형과 위치를 데이터베이스에 저장한다")
    @Test
    fun save() {
        val entry = OmokEntry("흑", 2)
        val actual = dao.save(entry)

        assertThat(actual.id).isGreaterThan(0)
        assertThat(actual.stoneType).isEqualTo("흑")
        assertThat(actual.position).isEqualTo(2)
    }

    @DisplayName("앱 실행 시 모든 게임 데이터를 데이터베이스로부터 가지고 온다.")
    @Test
    fun findAll() {
        dao.save(OmokEntry("흑", 4))
        dao.save(OmokEntry("백", 5))

        val actual = dao.findAll()
        assertThat(actual.size).isEqualTo(2)
    }
}
