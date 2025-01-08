package kz.witme.project.navigation

import kz.witme.project.common.serializable.Serializable

class CreateBookArgs(
    val bookName: String,
    val authorName: String,
    val bookListCount: Int,
    val imageByteArray: ByteArray?
) : Serializable