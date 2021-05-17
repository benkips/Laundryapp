package com.mamafua.app.models

import com.mamafua.app.Databasestuff.Washitems

data class Endorder (
    var email: String = "",
    var location: String = "",
    var orderitem: ArrayList<Washitems>? = null,
    var locationinstruction: String = "",
    var cordinates: String = "",

)
