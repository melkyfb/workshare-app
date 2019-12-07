package io.github.melkyfb.workshare.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import io.github.melkyfb.workshare.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource(val dbHelper: DBHelper) {

    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            // TODO: handle loggedInUser authentication
            val user = findUserByUsernameAndPassword(username, password)
            if (user == null) {
                if (findUserExists(username)) {
                    Result.Error(IOException("Senha inválidas"))
                } else {
                    Result.Error(IOException("Usuario não existe"))
                }
            }
            return Result.Success(user!!)
        } catch (e: Throwable) {
            return Result.Error(IOException("Erro ao acessar", e))
        }
    }

    fun insertUser(user: LoggedInUser): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("id", user.id)
            put("display_name", user.displayName)
            put("username", user.username)
            put("password", user.password)
        }
        return db.insert(LoggedInUser.TABLE_NAME, null, values)
    }

    fun findUserExists(username: String): Boolean {
        var db = dbHelper.readableDatabase
        val projection = LoggedInUser.TABLE_COLUMNS
        val selection = "username = ?"
        val selectionArgs = arrayOf(username)
        val cursor = db.query(
            LoggedInUser.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.count > 0
    }

    fun findUserByUsernameAndPassword(username: String, password: String): LoggedInUser? {
        var db = dbHelper.readableDatabase
        val projection = LoggedInUser.TABLE_COLUMNS
        val selection = "username = ? AND password = ?"
        val selectionArgs = arrayOf(username, password)
        val sortOrder = "username DESC"
        val cursor = db.query(
            LoggedInUser.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
        if (cursor.count > 0) {
            with(cursor){
                if (moveToFirst()) {
                    val id = getString(getColumnIndexOrThrow("id"))
                    val displayName = getString(getColumnIndexOrThrow("display_name"))
                    val username = getString(getColumnIndexOrThrow("username"))
                    val password = getString(getColumnIndexOrThrow("password"))
                    return LoggedInUser(id, displayName, username, password)
                }
            }
        }
        return null
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

