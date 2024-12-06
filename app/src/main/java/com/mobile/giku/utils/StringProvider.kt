package com.mobile.giku.utils

import android.content.Context

interface StringProvider {
    fun getString(resId: Int): String
}

class StringProviderImpl(private val context: Context) : StringProvider {
    override fun getString(resId: Int): String = context.getString(resId)
}