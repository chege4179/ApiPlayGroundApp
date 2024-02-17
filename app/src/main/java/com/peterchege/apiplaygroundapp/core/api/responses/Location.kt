package com.peterchege.apiplaygroundapp.core.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val url: String
)