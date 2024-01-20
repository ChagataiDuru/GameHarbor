package com.ozyegin.project.data

class ListEntity {
    var id: String = ""
    var name: String = ""
    var type: String = ""
    constructor()
    constructor(id: String, name: String, type: String) {
        this.id = id
        this.name = name
        this.type = type
    }
}