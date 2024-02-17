package com.peterchege.apiplaygroundapp.core.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorObject(
    @SerialName("error")
    val error:String,

    val errordummy:String = "errrrrrrrrrrrrrrrrrrrrr"
)