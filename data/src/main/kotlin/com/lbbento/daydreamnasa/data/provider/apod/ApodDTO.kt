package com.lbbento.daydreamnasa.data.provider.apod

import java.util.*

data class ApodDTO(val copyright : String = "",
                   val date: Date,
                   val explanation : String,
                   val hdurl : String,
                   val media_type : String,
                   val service_version : String,
                   val title : String,
                   val url : String)