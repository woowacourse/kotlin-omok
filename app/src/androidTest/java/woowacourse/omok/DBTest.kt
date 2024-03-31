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
    private lateinit var stoneDao: StoneDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        stoneDao = StoneDao(context)
        stoneDao.drop()
    }

    @Test
    fun storeOneStone() {
        val point = Point(1, 1)
        val expected = Stone(point, StoneColor.BLACK)
        stoneDao.save(expected)
        val actual = stoneDao.findAt(point)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun findAllStone() {
        val points = listOf(Point(2,2), Point(3,3), Point(4,4))
        val expected = points.map { Stone(it, StoneColor.BLACK) }.toSet()
        expected.forEach{ stoneDao.save(it)}
        val actual = stoneDao.findAll()
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

class StoneDao(context: Context) {
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

    fun findAt(point: Point): Stone? {
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

    fun findAll(): Set<Stone> {
        val db = dbHelper.writableDatabase
        val projection =
            arrayOf(
                BaseColumns._ID,
                StoneContract.StoneEntry.COLUMN_NAME_X,
                StoneContract.StoneEntry.COLUMN_NAME_Y,
                StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR,
            )
        val cursor =
            db.query(
                StoneContract.StoneEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
            )
        val stones = mutableSetOf<Stone>()
        while(cursor.moveToNext()) {
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_Y))
            val colorOrdinal = cursor.getInt(cursor.getColumnIndexOrThrow(StoneContract.StoneEntry.COLUMN_NAME_STONECOLOR))
            val color = ordinalToStoneColor(colorOrdinal)
            val stone = Stone(Point(x, y), color)
            stones.add(stone)
        }
        cursor.close()
        return stones
    }

    fun drop() {
        val db = dbHelper.writableDatabase
        db.delete(StoneContract.StoneEntry.TABLE_NAME, null, null)
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
