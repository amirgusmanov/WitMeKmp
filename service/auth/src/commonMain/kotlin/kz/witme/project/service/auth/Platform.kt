package kz.witme.project.service.auth

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform