package com.lostark.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lostark.database.dao.EventsDAO
import com.lostark.database.dao.ItemsDAO
import com.lostark.database.dao.KeyDAO
import com.lostark.database.dao.UpdateDAO
import com.lostark.database.table.*

@Database(
    entities = [Key::class, LoaEvents::class,Items::class, UpdateT::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun keyDao(): KeyDAO
    abstract fun eventsDao(): EventsDAO
    abstract fun ItemsDAO(): ItemsDAO
    abstract fun UpdateDAO(): UpdateDAO
    companion object{
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase?{
            if(instance==null)
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "LoaHelper"
                    ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
                }
            return instance
        }
        fun destroyInstance() {
            instance = null
        }
        /*val migration_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE 'Events' ('ThumbnailUrl' TEXT NOT NULL, 'Title' TEXT NOT NULL, 'Link' TEXT NOT NULL, PRIMARY KEY('Link'))"
                )
            }
        }
        val migration_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE 'Items' ('Id' INT NOT NULL, 'Name' TEXT NOT NULL, 'IconUrl' TEXT NOT NULL, 'YDayAvgPrice' DOUBLE NOT NULL,PRIMARY KEY('Id'))"
                )/**/
                database.execSQL(
                    "CREATE TABLE 'Update' ('RecentUpdate' TEXT NOT NULL, PRIMARY KEY('RecentUpdate'))"
                )
            }
        }
        val migration_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE 'Update' RENAME 'UpdateLog'"
                )
            }
        }*/
    }

}
