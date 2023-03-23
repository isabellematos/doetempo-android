package br.senai.sp.jandira.doetempo.services.login

import androidx.room.*
import br.senai.sp.jandira.doetempo.model.Login

@Dao
interface EmployeeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(login: Login)

    @Query("SELECT * FROM login WHERE userId = :userId")
    fun findUserById(userId: String): Login

    @Query("SELECT * FROM login")
    fun getAllUsers(): List<Login>

    @Update
    suspend fun updateUserDetails(login: Login)

    @Delete
    suspend fun deleteUser(login: Login)
}