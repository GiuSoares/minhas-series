package br.com.giugiu.seriesfilter.dataBase

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import br.com.giugiu.seriesfilter.dataBase.DBContract.SeriesEntry

object DBContract {
	class SeriesEntry : BaseColumns {
		companion object {

			val TABLE_NAME = "Series"
			val COLUMN_ID = "id"
			val COLUMN_NAME = "nome"
			val COLUMN_SERVICO = "servico"
			val COLUMN_ANO = "ano"
			val COLUMN_TEMPORADAS = "temporada"
			val COLUMN_EPISODIOS = "episodios"
			val COLUMN_GENERO = "genero"
		}
	}
}

class SeriesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
	override fun onCreate(db: SQLiteDatabase) = db.execSQL(SQL_CREATE_ENTRIES)

	override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
		db.execSQL(SQL_DELETE_ENTRIES)
		onCreate(db)
	}

	override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) =
		onUpgrade(db, oldVersion, newVersion)

	fun insertSerie(seriesModel: SeriesModel): Boolean {
		val db = writableDatabase

		val values = contentValuesOf(
			DBContract.SeriesEntry.COLUMN_NAME to seriesModel.nome,
			DBContract.SeriesEntry.COLUMN_SERVICO to seriesModel.servico,
			DBContract.SeriesEntry.COLUMN_ANO to seriesModel.ano,
			DBContract.SeriesEntry.COLUMN_TEMPORADAS to seriesModel.quantidadeTemporadas,
			DBContract.SeriesEntry.COLUMN_EPISODIOS to seriesModel.quantidadeEpisodios,
			DBContract.SeriesEntry.COLUMN_GENERO to seriesModel.genero
		)

		val newRowId = db.insert(DBContract.SeriesEntry.TABLE_NAME, null, values)

		return (newRowId != -1L)
	}

	fun updateSerie(seriesModel: SeriesModel): Boolean {
		val db = writableDatabase
		val update = db.update(
			DBContract.SeriesEntry.TABLE_NAME,
			contentValuesOf(
				DBContract.SeriesEntry.COLUMN_NAME to seriesModel.nome,
				DBContract.SeriesEntry.COLUMN_SERVICO to seriesModel.servico,
				DBContract.SeriesEntry.COLUMN_ANO to seriesModel.ano,
				DBContract.SeriesEntry.COLUMN_TEMPORADAS to seriesModel.quantidadeTemporadas,
				DBContract.SeriesEntry.COLUMN_EPISODIOS to seriesModel.quantidadeEpisodios,
				DBContract.SeriesEntry.COLUMN_GENERO to seriesModel.genero
			), "id = ?",
			arrayOf(seriesModel.id.toString())
		)

		return (update != 0)
	}

	@SuppressLint("Recycle")
	fun readAllSeries(): ArrayList<SeriesModel>? {
		val db = writableDatabase
		val list = ArrayList<SeriesModel>()
		var cursor: Cursor?

		try {
			cursor = db.rawQuery("SELECT * FROM " + DBContract.SeriesEntry.TABLE_NAME, null)

		} catch (e: SQLiteException) {
			db.execSQL(SQL_CREATE_ENTRIES)
			return arrayListOf()
		}

		if (cursor!!.moveToFirst()) { //caso o cursor não seja nulo
			while (!cursor.isAfterLast) {
				val params = arrayOf<String>(
					cursor.getInt(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_ID)).toString(),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_NAME)),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_SERVICO)),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_ANO)),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_TEMPORADAS)),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_EPISODIOS)),
					cursor.getString(cursor.getColumnIndex(DBContract.SeriesEntry.COLUMN_GENERO))
				)
				list.add(SeriesModel(params[1], params[2], params[3], params[4], params[5], params[6], params[0].toInt()))
				cursor.moveToNext()
			}
		}
		return list
	}

	fun delete (id : Int) : Boolean{
		val db = writableDatabase
		val delete = db.delete(DBContract.SeriesEntry.TABLE_NAME,"id = ?", arrayOf(id.toString()))
		return (delete != 0)
	}

	companion object {
		val DATABASE_NAME = "MinhasSeries.db"
		val DATABASE_VERSION = 1
		private val SQL_CREATE_ENTRIES = "CREATE TABLE " + DBContract.SeriesEntry.TABLE_NAME + "(" +
				DBContract.SeriesEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				DBContract.SeriesEntry.COLUMN_NAME + " VARCHAR(50)," +
				DBContract.SeriesEntry.COLUMN_SERVICO + " VARCHAR(50)," +
				DBContract.SeriesEntry.COLUMN_ANO + " VARCHAR(50)," +
				DBContract.SeriesEntry.COLUMN_TEMPORADAS + " VARCHAR(50)," +
				DBContract.SeriesEntry.COLUMN_EPISODIOS + " VARCHAR(50)," +
				DBContract.SeriesEntry.COLUMN_GENERO + " VARCHAR(50))"

		private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.SeriesEntry.TABLE_NAME
	}
}