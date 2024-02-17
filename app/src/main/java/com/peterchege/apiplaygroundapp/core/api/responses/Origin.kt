package com.peterchege.apiplaygroundapp.core.api.responses

import kotlinx.serialization.Serializable


@Serializable
data class Origin(
    val name: String,
    val url: String
)