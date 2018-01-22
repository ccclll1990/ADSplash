package net.virous.adsplash.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseManager {
	private static DatabaseManager instance;
	private static SQLiteOpenHelper mDbHelper;
	private AtomicInteger mOpenCounter = new AtomicInteger();
	private SQLiteDatabase mDatabase;

	public static synchronized void initialize(SQLiteOpenHelper helper) {
		if (instance == null) {
			instance = new DatabaseManager();
			mDbHelper = helper;
		}
	}

	public static synchronized DatabaseManager getinstance() {
		if (instance == null) {
			throw new IllegalStateException("DatabaseManager未初始化");
		}
		return instance;
	}

	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mDatabase = mDbHelper.getWritableDatabase();
		}
		return mDatabase;
	}

	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			mDatabase.close();
		}
	}

}
