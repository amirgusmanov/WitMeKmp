package kz.witme.project.profile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform