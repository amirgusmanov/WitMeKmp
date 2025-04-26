import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension

internal val Project.libs: LibrariesForLibs
    get() = extensions.getByType<LibrariesForLibs>()

internal val Project.compose: ComposeExtension
    get() = extensions.getByType<ComposeExtension>()