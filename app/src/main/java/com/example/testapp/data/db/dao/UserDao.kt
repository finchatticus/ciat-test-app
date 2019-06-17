package com.example.testapp.data.db.dao

import androidx.room.*
import com.example.testapp.data.db.COLUMN_ID
import com.example.testapp.data.db.TABLE_USER
import com.example.testapp.data.db.dto.UserDto

@Dao
interface UserDao {

    @Query("SELECT * FROM $TABLE_USER WHERE $COLUMN_ID = :id")
    fun getById(id: Int): UserDto

    @Query("SELECT * FROM $TABLE_USER LIMIT (:page - 1) * :perPage, :perPage")
    fun getPerPage(page: Int, perPage: Int = 3): List<UserDto>

    @Query("SELECT COUNT($COLUMN_ID) FROM $TABLE_USER")
    fun getRowCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserDto>)

    @Delete
    fun delete(user: UserDto)
}