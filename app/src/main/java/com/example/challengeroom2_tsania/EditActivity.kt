package com.example.challengeroom2_tsania

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.challengeroom2_tsania.room.Constant
import com.example.challengeroom2_tsania.room.dbperpustakaan
import com.example.challengeroom2_tsania.room.tbbuku
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {
    val db by lazy { dbperpustakaan(this) }
    private var bukusiswa: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
        bukusiswa = intent.getIntExtra("intent_id", 0)
        Toast.makeText(this, bukusiswa.toString(), Toast.LENGTH_SHORT).show()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    fun setupView(){
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btnUpdate.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btnUpdate.visibility = View.GONE
                btnSimpan.visibility = View.GONE
                et_idBuku.visibility = View.GONE
                tampilkan()
            }
            Constant.TYPE_UPDATE -> {
                btnSimpan.visibility = View.GONE
                tampilkan()
            }
        }
    }

    private fun setupListener(){
        btnSimpan.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.perpusDAO().addtbbuku(
                    tbbuku(0, et_Nama.text.toString(),
                        et_kategori.text.toString(),
                        et_pengarang.text.toString(),
                        et_penerbit.text.toString(),
                        et_jmlBuku.text.toString().toInt())
                )
                finish()
            }
        }
    }
    fun  tampilkan(){
        bukusiswa = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val bukuku = db.perpusDAO().tampilkan(bukusiswa)[0]
            val dataId : String = bukuku.id_buku.toString()
            val dataJml : String = bukuku.jml_buku.toString()
            et_idBuku.setText(dataId)
            et_Nama.setText(bukuku.judul)
            et_kategori.setText(bukuku.kategori)
            et_pengarang.setText(bukuku.pengarang)
            et_penerbit.setText(bukuku.penerbit)
            et_jmlBuku.setText(dataJml)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}