package kz.witme.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform