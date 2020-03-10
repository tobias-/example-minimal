package org.example.domain

import io.ebean.annotation.Where
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinColumns
import javax.persistence.ManyToOne

class Owned {
    constructor() {
        val bytes = byteArrayOf()
        val (x, y) = bytes.inputStream().use {
            1 to 2
        }
    }
}
