package com.ozyegin.project.data

import com.google.firebase.database.Exclude

class UserEntity {
    @get:Exclude
    var id: String = ""
    var email: String = ""
}