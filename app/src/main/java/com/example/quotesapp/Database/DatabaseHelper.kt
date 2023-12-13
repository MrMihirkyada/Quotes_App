package com.example.quotesapp.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.quotesapp.Model.Model
import com.example.quotesapp.Model.categoryModel
import com.example.quotesapp.Model.favoratshayri
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    private val mDataBase: SQLiteDatabase? = null
    private var mNeedUpdate = false
    private val mContext: Context

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

    //    TODO copy file
    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput = mContext.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }

    @Synchronized
    override fun close() {
        mDataBase?.close()
        super.close()
    }


    fun DisplaycategoryData(): ArrayList<categoryModel> {

        var list = ArrayList<categoryModel>()
        list.clear()
        var db = readableDatabase
        var sql = "select * from catageoryTb"
        var cursor: Cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst())
        {
            do
            {
                var id = cursor.getInt(0)
                var category = cursor.getString(1)

                Log.e("TAG", "displayRecord: " + id + category)
                list.add(categoryModel(id, category))

            } while (cursor.moveToNext())
        }
        return list
    }

    fun updateRecord(id : Int,status : Int)
    {
        var db = writableDatabase

        var c= ContentValues()

        c.put("status",status)

        Log.e(TAG, "updateRecord: "+status )

        var update = "update quotesTb set status = $status where id = $id"


        db.execSQL(update)
    }

    fun updatequotesRecord(id : Int,quotes : String)
    {
        var db = writableDatabase

        var c= ContentValues()

        c.put("quotes",quotes)


        val update = "update quotesTb set name = '$quotes' where id ='$id'"
        Log.e(TAG, "updatequotesRecord: "+id+" "+quotes )


        db.execSQL(update)
    }

    fun DisplayQuotesData(id: Int): ArrayList<Model> {

        var list = ArrayList<Model>()
        list.clear()
        var db = readableDatabase
        var sql = "select * from quotesTb where categoryId = $id"
        var cursor: Cursor = db.rawQuery(sql, null)

        if (cursor.moveToFirst()) {
            do {
                var id = cursor.getInt(0)
                var shayari = cursor.getString(1)
                var Cid = cursor.getInt(2)
                var status = cursor.getInt(3)

                Log.e("TAG", "displayRecord: " + id + shayari)
                list.add(Model(id, shayari,Cid,status))

            } while (cursor.moveToNext())
        }
        return list
    }

    fun FavoriteDisplayRecord() : ArrayList<favoratshayri>{
        var DisplayList = ArrayList<favoratshayri>()

        val dbF = readableDatabase
        val SqlF = "Select * from quotesTb where status = 1"
        val c = dbF.rawQuery(SqlF,null)
        if (  c.moveToFirst()){

            do {
                var Shayari_id = c.getInt(0)
                var Shayari = c.getString(1)
                var fav = c.getInt(2)

                Log.e("TAG", "FavoriteDisplayRecord: $Shayari_id $Shayari" )
                var shayarimodal = favoratshayri(Shayari_id,Shayari,fav)

                DisplayList.add(shayarimodal)
            }while (c.moveToNext())
        }
        return DisplayList
    }

    fun updateDataBase() {
        if (mNeedUpdate) {

            val dbFile = File(DB_PATH + DB_NAME)
            if (dbFile.exists()) dbFile.delete()
            copyDataBase()
            mNeedUpdate = false

        }

    }
        companion object {
            private const val TAG = "MyDatabase"
            private const val DB_NAME = "Myquotes.db"
            private var DB_PATH = ""
            private const val DB_VERSION = 1
        }

        init
        {
            DB_PATH =
                context.applicationInfo.dataDir + "/databases/"
            mContext = context
            copyDataBase()
            this.readableDatabase
        }
    }