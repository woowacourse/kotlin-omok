package woowacourse.omok

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import androidx.test.core.app.ApplicationProvider
import omok.model.entity.Point
import omok.model.entity.Stone
import omok.model.entity.StoneColor
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class DBTest {
    private lateinit var omokDao: OmokDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        omokDao = OmokDao(context)
    }

    @Test
    fun storeOneStone() {
        val point = Point(1, 1)
        val expected = Stone(point, StoneColor.BLACK)
        omokDao.save(expected)
        val actual = omokDao.findStoneAt(point)
        assertThat(actual).isEqualTo(expected)
    }
}

object StoneContract {
    object StoneEntry : BaseColumns {
        const val TABLE_NAME = "stones"
        const val COLUMN_NAME_X = "x"
        const val COLUMN_NAME_Y = "y"
        const val COLUMN_NAME_STONECOLOR = "stonecolor"
    }
}

class OmokDao(private val context: Context) {
    private val dbHelper: OmokDbHelper = OmokDbHelper(context)

    fun save(stone: Stone) {
        val db = dbHelper.writableDatabase
        db.insert(
            StoneContract.StoneEntry.TABLE_NAME,
            null,
            contentValuesOf(
                StoneContract.StoneEntry.COLUMN_NAME_X to stone.point.x,
                StoneContract.StoneEntry.COLUMN_NAME_Y to stone.point.y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR to stone.stoneColor.ordinal,
            ),
        )
    }

    fun findStoneAt(point: Point): Stone? {
        val db = dbHelper.writableDatabase
        val projection =
            arrayOf(
                BaseColumns._ID,
                StoneContract.StoneEntry.COLUMN_NAME_X,
                StoneContract.StoneEntry.COLUMN_NAME_Y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR,
            )
        val selection = "${StoneContract.StoneEntry.COLUMN_NAME_X} = ${point.x} AND ${StoneContract.StoneEntry.COLUMN_NAME_Y} = ${point.y}"
        val cursor =
            db.query(
                StoneContract.StoneEntry.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                null,
            )
        while (cursor.moveToNext()) {
            val colorOrdinal = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            cursor.close()
            val color = ordinalToStoneColor(colorOrdinal)
            return Stone(point, color)
        }
        cursor.close()
        return null
    }

    private fun ordinalToStoneColor(ordinal: Int) =
        when (ordinal) {
            StoneColor.WHITE.ordinal -> StoneColor.WHITE
            else -> StoneColor.BLACK
        }
}

class OmokDbHelper(context: Context) : SQLiteOpenHelper(context, DBNAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_STONES)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
    }

    override fun onDowngrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        super.onDowngrade(db, oldVersion, newVersion)
    }

    companion object {
        private const val SQL_CREATE_STONES =
            """
                create table ${StoneContract.StoneEntry.TABLE_NAME}
                (${BaseColumns._ID} integer primary key, 
                ${StoneContract.StoneEntry.COLUMN_NAME_X} integer, 
                ${StoneContract.StoneEntry.COLUMN_NAME_Y} integer,
                ${StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR} integer)
            """
        private const val SQL_DROP_STONES = "drop table if exists ${StoneContract.StoneEntry.TABLE_NAME}"
        private const val DBNAME = "Omok"
    }
}
