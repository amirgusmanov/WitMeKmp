package kz.witme.project.common_ui.permission

import androidx.compose.runtime.Composable

interface PermissionHandler {
    @Composable
    fun askPermission(permission: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()
}

interface PermissionCallback {
    fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus)
}

expect class PermissionsManager(callback: PermissionCallback) : PermissionHandler

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager

