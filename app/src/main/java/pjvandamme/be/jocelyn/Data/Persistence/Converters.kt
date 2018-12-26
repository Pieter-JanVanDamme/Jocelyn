package pjvandamme.be.jocelyn.Data.Persistence

import android.arch.persistence.room.TypeConverter
import java.util.*

class Converters {

    companion object {
        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long): Date? = if (value == null) null else Date(value)

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? = date?.time
    }
}