package com.example.challengeroom2_tsania.room

import androidx.room.*

@Dao
interface DAO {
    @Insert
    fun addtbbuku(tbbook:tbbuku)

    @Update
    fun updatetbbuku(tbbook:tbbuku)

    @Delete
    fun deletetbbuku(tbbook:tbbuku)

    @Query("SELECT * FROM tbbuku")
    fun tampil(): List<tbbuku>

    @Query("SELECT * FROM tbbuku WHERE id_buku =:idBuku")
    fun tampilkan(idBuku:Int): List<tbbuku>
}