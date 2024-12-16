@file:OptIn(ExperimentalPermissionsApi::class)

package kz.witme.project.common_ui.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {

    @Suppress("NonSkippableComposable")
    @Composable
    override fun askPermission(permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current
        when (permission) {
            PermissionType.Camera -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                LaunchedEffect(cameraPermissionState) {
                    val permissionResult = cameraPermissionState.status
                    if (!permissionResult.isGranted) {
                        if (permissionResult.shouldShowRationale) {
                            callback.onPermissionStatus(
                                permission, PermissionStatus.ShowRational
                            )
                        } else {
                            lifecycleOwner.lifecycleScope.launch {
                                cameraPermissionState.launchPermissionRequest()
                            }
                        }
                    } else {
                        callback.onPermissionStatus(
                            permission, PermissionStatus.Granted
                        )
                    }
                }
            }

            PermissionType.Gallery -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                callback.onPermissionStatus(
                    permission, PermissionStatus.Granted
                )
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.Camera -> {
                val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
                cameraPermissionState.status.isGranted
            }

            PermissionType.Gallery -> {
                // Granted by default because in Android GetContent API does not require any
                // runtime permissions, and i am using it to access gallery in my app
                true
            }
        }
    }

    @Suppress("NonSkippableComposable")
    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }
}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager = remember {
    PermissionsManager(callback)
}