package kz.witme.project.data.network

import kz.witme.project.data.util.Constants

fun getImageUrl(url: String?): String = buildString {
    append(Constants.BASE_URL)
    append(url)
}