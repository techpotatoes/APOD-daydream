package com.lbbento.daydreamnasa

import java.net.URL
import java.security.InvalidParameterException

class AppUtil {

    companion object {
        @Throws(Exception::class)
        fun extractYoutubeId(url: String): String {
            val query = URL(url).getQuery()
            val param = query.split("&".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            var id: String = ""
            param
                    .map { row -> row.split("=".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray() }
                    .filter { it[0] == "v" }
                    .forEach { id = it[1] }
            if (id != "")
                return id
            else
                throw InvalidParameterException("Parameter url must contain an youtube video ID.")
        }
    }
}