package io.github.melkyfb.workshare.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import io.github.melkyfb.workshare.data.model.LoggedInUser

class DBHelper(context: Context): SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            generateCreateSqlFor(
                LoggedInUser.TABLE_NAME,
                LoggedInUser.TABLE_COLUMNS,
                LoggedInUser.TABLE_TYPES
            )
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, old: Int, new: Int) {
        db.execSQL(generateDropFor(LoggedInUser.TABLE_NAME))
        onCreate(db)
    }

    fun generateCreateSqlFor(tableName: String, tableColumns: Array<String>, tableTypes: Array<String>): String {
        var SQLFields = ""
        var amountOfFields = Math.min(tableColumns.size, tableTypes.size) - 1
        for (i in 0..amountOfFields) {
            SQLFields += "${tableColumns.get(i)} ${tableTypes.get(i)}"
            if (i!==(tableColumns.size - 1)) SQLFields += ", "
        }
        return "CREATE TABLE $tableName ($SQLFields)"
    }

    fun generateDropFor(tableName: String): String {
        return "DROP TABLE IF EXISTS $tableName"
    }

    companion object {
        const val DATABASE_NAME = "WorkShare.db"
        const val DATABASE_VERSION = 1
    }
}