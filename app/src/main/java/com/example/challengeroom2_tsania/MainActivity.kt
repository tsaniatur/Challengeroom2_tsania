package com.example.challengeroom2_tsania

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengeroom2_tsania.room.Constant
import com.example.challengeroom2_tsania.room.dbperpustakaan
import com.example.challengeroom2_tsania.room.tbbuku
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    val db by lazy { dbperpustakaan(this) }
    lateinit var bukuAdapter: BukuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpListener()
        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        loadData()

        }
        fun loadData() {
            CoroutineScope(Dispatchers.IO).launch {
                val perpustakaan = db.perpusDAO().tampil()
                Log.d("MainActivity","dbResponse:$perpustakaan")
                withContext(Dispatchers.Main){
                    bukuAdapter.setData(perpustakaan)
                }
            }
        }

    fun setUpListener(){
        btnInput.setOnClickListener {
            intentEdit(0, Constant.TYPE_CREATE)
        }
    }
    fun intentEdit(bukusiswa:Int, intentType:Int){
        startActivity(
            Intent(applicationContext,EditActivity::class.java)
                .putExtra("intent_id", bukusiswa)
                .putExtra("intent_type", intentType)
        )
    }
    private fun setUpRecyclerView(){
        bukuAdapter = BukuAdapter(arrayListOf(), object: BukuAdapter.OnAdapterlistener{
            override fun onClick(tbBook: tbbuku) {
                intentEdit(tbBook.id_buku, Constant.TYPE_READ)

            }

            override fun onUpdate(tbBook: tbbuku) {
                intentEdit(tbBook.id_buku, Constant.TYPE_UPDATE)
            }

            override fun onDelete(tbBook: tbbuku) {
                hapusBuku(tbBook)
            }

        })
        rvListbuku.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = bukuAdapter
        }
    }
    fun hapusBuku(tbBook: tbbuku){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${tbBook.kategori}")
            setNegativeButton("Cancel") {dialogInterface,i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Ya") { dialogInterface,i ->
                dialogInterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.perpusDAO().deletetbbuku(tbBook)
                    dialogInterface.dismiss()
                    loadData()
                }
            }
        }
        alertDialog.show()
    }
}
