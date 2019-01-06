package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import pjvandamme.be.jocelyn.Domain.Models.Jotting
import pjvandamme.be.jocelyn.Domain.Models.Mention
import pjvandamme.be.jocelyn.Domain.Models.Relation
import android.os.AsyncTask
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.TypeConverters
import android.util.Log
import java.util.Calendar


@Database(entities= [Jotting::class, Mention::class, Relation::class], version = 1)
@TypeConverters(Converters::class)
abstract class JocelynDatabase: RoomDatabase() {
    abstract fun jottingDao(): JottingDao
    abstract fun mentionDao(): MentionDao
    abstract fun relationDao(): RelationDao

    // companion object gives static access to the method getJocelynDatabase, which gives us a singleton instance
    companion object {
        private var INSTANCE: JocelynDatabase? = null

        fun getJocelynDatabase(context: Context): JocelynDatabase? {
            if(INSTANCE == null){
                synchronized(JocelynDatabase::class){
                    INSTANCE = Room
                        .databaseBuilder(context.applicationContext, JocelynDatabase::class.java, "jocelynDB")
                        // callback allows us to populate the database, see below
                        .addCallback(rdbc)
                        .build()
                }
            }
            return INSTANCE
        }
        fun destroyDatabase(){
            INSTANCE = null
        }
    }


    // override onopen to populate the database
    // override RoomDatabase.Callback()#onCreate to only populate the database when its created for the first time
    private object rdbc: RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            // Delete previous data and repopulate database
            PopulateDbAsync(INSTANCE!!).execute()
        }
    }

    // populate the database
    private class PopulateDbAsync internal constructor(db: JocelynDatabase) : AsyncTask<Void, Void, Void>() {

        private val rDao: RelationDao
        private val mDao: MentionDao
        private val jDao: JottingDao

        init {
            rDao = db.relationDao()
            mDao = db.mentionDao()
            jDao = db.jottingDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
            // delete all previous data
            mDao.deleteAll()
            rDao.deleteAll()
            jDao.deleteAll()

            var relations: List<Relation> = listOf(
                Relation(0, "An Haes", "anneke", "Studied economics, loves to go skiing.", 25),
                Relation(0, "Anne Vleeschouwer", "alsikjouzie", "Best ballroom dancer I know.",26),
                Relation(0,"Annelies Schoup", "anneliesschoup", "", 2),
                Relation(0,"Antoon ?", "antoonbontenos", "", 7),
                Relation(0,"Frederik De Backer", "fredje", "Awesome dude, been friends since primary school.",37),
                Relation(0,"Anton Van Haever", "austirol", "Terrific web developer I've worked with for 9 years.", 51),
                Relation(0,"Annabel Vertonghen", "annabelvrouwtheo", "Conniving.",4)
            )
            for(rel in relations) {
                rDao.insert(rel)
            }

            var jottings: List<Jotting> = listOf(
                Jotting(
                    0,
                    Calendar.getInstance().time,
                    "@Anneke is planning to go to Ibiza in summer."
                ),
                Jotting(
                    0,
                    Calendar.getInstance().time,
                    "@AnnabelVrouwTheo is having an affair with @Fredje, according to @AlsIkJouZie"
                )
            )
            for(jot in jottings) {
                jDao.insert(jot)
            }

            var mentions: List<Mention> = listOf(
                Mention(0,1,1),
                Mention(0,2,7),
                Mention(0,2,5),
                Mention(0,2,2)
            )
            for(men in mentions) {
                mDao.insert(men)
            }

            return null
        }
    }
}