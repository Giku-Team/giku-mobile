package com.mobile.giku.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

class DecimalTypeAdapter : TypeAdapter<Double>() {
    @Throws(IOException::class)
    override fun read(reader: JsonReader): Double {
        val value = reader.nextString()
        return value.replace(",", ".").toDouble()
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Double?) {
        out.value(value?.toString())
    }
}