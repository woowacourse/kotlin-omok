import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.omok.database.OmokGameContract
import woowacourse.omok.database.OmokGameDao
import woowacourse.omok.database.SavedStone
import woowacourse.omok.model.StoneColor

class FakeDbHelper(
    context: Context,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(OmokGameContract.SQL_CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL(OmokGameContract.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "FakeOmokGame.db"
    }
}

class OmokGameDaoTest {
    private lateinit var dbHelper: FakeDbHelper
    private lateinit var omokGameDao: OmokGameDao
    private val context: Context = ApplicationProvider.getApplicationContext()

    @BeforeEach
    fun setUp() {
        dbHelper = FakeDbHelper(context)
        omokGameDao = OmokGameDao(context)
    }

    @AfterEach
    fun cleanUp() {
        dbHelper.close()
        omokGameDao.clearGameData()
    }

    @Test
    fun saveStoneTest() {
        val x = 3
        val y = 4
        val turn = StoneColor.BLACK

        omokGameDao.saveStone(x, y, turn)
        val savedStones = omokGameDao.loadGameState()

        assertThat(savedStones).hasSize(1)
        assertThat(savedStones[0].x).isEqualTo(x)
        assertThat(savedStones[0].y).isEqualTo(y)
        assertThat(savedStones[0].color).isEqualTo(turn)
    }

    @Test
    fun loadGameStateTest() {
        val stones =
            listOf(
                SavedStone(1, 1, StoneColor.BLACK),
                SavedStone(2, 2, StoneColor.WHITE),
                SavedStone(3, 3, StoneColor.BLACK),
            )

        stones.forEach { omokGameDao.saveStone(it.x, it.y, it.color) }
        val savedStones = omokGameDao.loadGameState()

        assertThat(savedStones).hasSize(3)
    }

    @Test
    fun clearGameDataTest() {
        omokGameDao.saveStone(1, 1, StoneColor.BLACK)
        omokGameDao.saveStone(2, 2, StoneColor.WHITE)

        omokGameDao.clearGameData()
        val savedStones = omokGameDao.loadGameState()

        assertThat(savedStones).isEmpty()
    }
}
