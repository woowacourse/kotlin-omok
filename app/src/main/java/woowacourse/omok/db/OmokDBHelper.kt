package woowacourse.omok.db

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
import woowacourse.omok.domain.Stages
import woowacourse.omok.domain.User
import woowacourse.omok.domain.Users

class OmokDBHelper(context: Context, private val tables: List<SQLiteTable>) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(p0: SQLiteDatabase?) {
        tables.forEach {
            val columns = it.scheme.joinToString(",") { scheme -> "${scheme.name} ${scheme.type}" }
            p0?.execSQL("CREATE TABLE ${it.name} (${columns})")
        }
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
        return Users(users)
    }

    fun selectAllStagesByUser(user: User): Stages {
        val cursor = readableDatabase.rawQuery(
            "select a.id as StageID, b.* " +
                    "from ${StageTable.name} a, ${StoneTable.name} b, ${StageStonesTable.name} c, ${UserStagesTable.name} d " +
                    "on a.id = c.stageId and b.id = c.stoneId and d.stageId = a.id " +
                    "where d.userId = ${user.id};",
            null
        )
        val stages = mutableMapOf<Int, MutableList<StoneDTO>>()
        with(cursor) {
            while (moveToNext()) {
                val stageID = getInt(0)
                val stone = StoneDTO(ColorDTO.values()[getInt(4)], VectorDTO(getInt(2), getInt(3)))
                stages[stageID]?.add(stone) ?: { stages[stageID] = mutableListOf(stone) }
            }
        }
        return Stages(stages.asSequence().associate { it.key to Stage(it.value.toList()) })
    }

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "OMOK"
    }
}