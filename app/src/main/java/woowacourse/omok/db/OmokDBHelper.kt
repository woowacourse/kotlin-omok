package woowacourse.omok.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import dto.ColorDTO
import dto.StoneDTO
import dto.VectorDTO
import woowacourse.omok.db.table.StageStonesTable
import woowacourse.omok.db.table.StageTable
import woowacourse.omok.db.table.StoneTable
import woowacourse.omok.db.table.UserStagesTable
import woowacourse.omok.db.table.UserTable
import woowacourse.omok.domain.Stage
import woowacourse.omok.domain.StageStones
import woowacourse.omok.domain.Stages
import woowacourse.omok.domain.User
import woowacourse.omok.domain.Users

class OmokDBHelper(context: Context, private val tables: List<SQLiteTable>) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        tables.forEach {
            val columns = it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type}" }
            p0?.execSQL("CREATE TABLE ${it.name} ($columns)")
        }
        p0?.execSQL("INSERT INTO STAGE VALUES(0)")
        p0?.execSQL("INSERT INTO STAGE VALUES(1)")
        p0?.execSQL("INSERT INTO STAGE VALUES(2)")

        p0?.execSQL("INSERT INTO USER VALUES(0, \"dy\")")
        p0?.execSQL("INSERT INTO USER VALUES(1, \"corgan\")")

        p0?.execSQL("INSERT INTO USERSTAGES VALUES(0, 0)")
        p0?.execSQL("INSERT INTO USERSTAGES VALUES(0, 1)")
        p0?.execSQL("INSERT INTO USERSTAGES VALUES(1, 0)")
        p0?.execSQL("INSERT INTO USERSTAGES VALUES(1, 1)")
        p0?.execSQL("INSERT INTO USERSTAGES VALUES(1, 2)")

        p0?.execSQL("INSERT INTO STONE VALUES(0, 0, 0, 0)")
        p0?.execSQL("INSERT INTO STONE VALUES(1, 2, 2, 1)")
        p0?.execSQL("INSERT INTO STONE VALUES(2, 1, 1, 0)")
        p0?.execSQL("INSERT INTO STONE VALUES(3, 3, 4, 1)")
        p0?.execSQL("INSERT INTO STONE VALUES(4, 5, 2, 1)")

        p0?.execSQL("INSERT INTO STAGESTONES VALUES(0, 0)")
        p0?.execSQL("INSERT INTO STAGESTONES VALUES(0, 1)")
        p0?.execSQL("INSERT INTO STAGESTONES VALUES(1, 2)")
        p0?.execSQL("INSERT INTO STAGESTONES VALUES(1, 3)")
        p0?.execSQL("INSERT INTO STAGESTONES VALUES(2, 4)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        tables.forEach {
            p0?.execSQL("DROP TABLE IF EXISTS ${it.name}")
        }
        onCreate(p0)
    }

    fun selectAllUsers(): Users {
        val cursor = readableDatabase.rawQuery("SELECT * FROM ${UserTable.name}", null)
        val users = mutableListOf<User>()
        with(cursor) {
            while (moveToNext()) {
                users.add(
                    User(
                        getInt(getColumnIndexOrThrow(UserTable.ID)),
                        getString(getColumnIndexOrThrow(UserTable.NAME))
                    )
                )
            }
        }
        cursor.close()
        return Users(users)
    }

    fun selectAllStagesByUser(user: User): Stages {
        val cursor = readableDatabase.rawQuery(
            "SELECT ${UserStagesTable.STAGE_ID} FROM ${UserStagesTable.name} WHERE ${UserStagesTable.USER_ID} = ${user.id}",
            null
        )
        val stages = mutableListOf<Pair<Int, List<StoneDTO>>>()
        with(cursor) {
            while (moveToNext()) {
                val stageId = getInt(getColumnIndexOrThrow(UserStagesTable.STAGE_ID))
                stages.add(stageId to selectAllStonesByStage(stageId))
            }
        }
        cursor.close()
        return Stages(stages.map { Stage(it.first, StageStones(it.second)) })
    }

    fun selectAllStonesByStage(stageId: Int): List<StoneDTO> {
        val stones: MutableList<StoneDTO> = mutableListOf()
        val stoneCursor = readableDatabase.rawQuery(
            "SELECT b.* FROM ${StageStonesTable.name} a, ${StoneTable.name} b on a.stoneId = b.id WHERE stageId = $stageId",
            null
        )
        with(stoneCursor) {
            while (stoneCursor.moveToNext()) {
                val color = getInt(getColumnIndexOrThrow(StoneTable.COLOR))
                val vector = VectorDTO(
                    getInt(getColumnIndexOrThrow(StoneTable.X)),
                    getInt(getColumnIndexOrThrow(StoneTable.Y))
                )
                val stone = StoneDTO(ColorDTO.values()[color], vector)
                stones.add(stone)
            }
        }
        return stones
    }

    fun insertStoneToStage(stone: StoneDTO, stage: Stage) {
        val contentValues = ContentValues()
        contentValues.put(StoneTable.X, stone.coordinate.x)
        contentValues.put(StoneTable.Y, stone.coordinate.y)
        contentValues.put(StoneTable.COLOR, stone.color.ordinal)
        val id = writableDatabase.insert(StoneTable.name, null, contentValues)
        contentValues.clear()
        contentValues.put(StageStonesTable.STAGE_ID, stage.id)
        contentValues.put(StageStonesTable.STONE_ID, id)
        writableDatabase.insert(StageStonesTable.name, null, contentValues)
    }

    fun insertUser(userName: String) {
        val contentValues = ContentValues()
        contentValues.put(UserTable.NAME, userName)
        writableDatabase.insert(UserTable.name, null, contentValues)
    }

    fun insertStageByUser(user: User): Int {
        val cursor = readableDatabase.rawQuery("SELECT MAX(ID) FROM ${StageTable.name}", null)
        cursor.moveToNext()
        val id = cursor.getInt(0) + 1
        val contentValues = ContentValues()
        contentValues.put(StageTable.ID, id)
        writableDatabase.insert(StageTable.name, null, contentValues)
        contentValues.clear()
        contentValues.put(UserStagesTable.USER_ID, user.id)
        contentValues.put(UserStagesTable.STAGE_ID, id)
        writableDatabase.insert(UserStagesTable.name, null, contentValues)
        cursor.close()
        return id
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
