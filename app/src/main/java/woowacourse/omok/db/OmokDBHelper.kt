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
        p0?.execSQL("INSERT INTO STONE VALUES(1, 1, 1, 1)")
        p0?.execSQL("INSERT INTO STONE VALUES(2, 0, 0, 0)")
        p0?.execSQL("INSERT INTO STONE VALUES(3, 1, 1, 1)")
        p0?.execSQL("INSERT INTO STONE VALUES(4, 2, 2, 1)")

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
                        getInt(getColumnIndexOrThrow(UserTable.scheme[0].name)),
                        getString(getColumnIndexOrThrow(UserTable.scheme[1].name))
                    )
                )
            }
        }
        cursor.close()
        return Users(users)
    }

    fun selectAllStagesByUserId(user: User): Stages {
        val cursor = readableDatabase.rawQuery(
            "select a.id as StageID, b.* " + "from ${StageTable.name} a, ${StoneTable.name} b, ${StageStonesTable.name} c, ${UserStagesTable.name} d " + "on a.id = c.stageId and b.id = c.stoneId and d.stageId = a.id " + "where d.userId = ${user.id};",
            null
        )
        val stages = mutableListOf<Pair<Int, MutableList<StoneDTO>>>()
        with(cursor) {
            while (moveToNext()) {
                val stageId = getInt(0)
                val stone = StoneDTO(ColorDTO.values()[getInt(4)], VectorDTO(getInt(2), getInt(3)))
                val stage = stages.find { it.first == stageId }
                if (stage != null) stage.second.add(stone)
                else stages.add(stageId to mutableListOf(stone))
            }
        }
        cursor.close()
        return Stages(stages.map { Stage(it.first, StageStones(it.second)) })
    }

    fun insertStoneToStage(stone: StoneDTO, stage: Stage) {
        val contentValues = ContentValues()
        contentValues.put("x", stone.coordinate.x)
        contentValues.put("y", stone.coordinate.y)
        contentValues.put("color", stone.color.ordinal)
        val id = writableDatabase.insert(StoneTable.name, null, contentValues)
        contentValues.clear()
        contentValues.put("stageId", stage.id)
        contentValues.put("stoneId", id)
        writableDatabase.insert(StageStonesTable.name, null, contentValues)
    }

    fun insertUser(userName: String) {
        val contentValues = ContentValues()
        contentValues.put("name", userName)
        writableDatabase.insert(UserTable.name, null, contentValues)
    }

    fun insertStage(userId: Int) {
        val cursor = readableDatabase.rawQuery("SELECT MAX(ID) FROM ${StageTable.name}", null)
        cursor.moveToNext()
        val id = cursor.getInt(0) + 1
        val contentValues = ContentValues()
        contentValues.put("id", id)
        writableDatabase.insert(StageTable.name, null, contentValues)
        contentValues.clear()
        contentValues.put("userId", userId)
        contentValues.put("stageId", id)
        writableDatabase.insert(UserStagesTable.name, null, contentValues)
        cursor.close()
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}
