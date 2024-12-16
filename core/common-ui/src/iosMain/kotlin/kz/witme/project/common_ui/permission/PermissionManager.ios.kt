package kz.witme.project.common_ui.permission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusDenied
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import platform.Foundation.NSURL
import platform.Photos.PHAuthorizationStatus
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIApplication
import platform.UIKit.UIApplicationOpenSettingsURLString

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {

    @Suppress("NonSkippableComposable")
    @Composable
    override fun askPermission(permission: PermissionType) {
        when (permission) {
            PermissionType.Gallery -> {
                askGalleryPermission(
                    status = remember {
                        AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                    },
                    permission = permission,
                    callback = callback
                )
            }

            PermissionType.Camera -> {
                askCameraPermission(
                    status = remember {
                        AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                    },
                    permission = permission,
                    callback = callback
                )
            }
        }
    }

    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean = when (permission) {
        PermissionType.Camera -> {
            val status: AVAuthorizationStatus =
                remember { AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) }
            status == AVAuthorizationStatusAuthorized
        }

        PermissionType.Gallery -> {
            val status: PHAuthorizationStatus =
                remember { PHPhotoLibrary.authorizationStatus() }
            status == PHAuthorizationStatusAuthorized
        }
    }

    @Suppress("NonSkippableComposable")
    @Composable
    override fun launchSettings() {
        NSURL.URLWithString(UIApplicationOpenSettingsURLString)?.let {
            UIApplication.sharedApplication.openURL(it)
        }
    }

    private fun askGalleryPermission(
        status: PHAuthorizationStatus,
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        when (status) {
            PHAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(
                    permissionType = permission,
                    status = PermissionStatus.Granted
                )
            }

            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.Companion.requestAuthorization { newStatus ->
                    askGalleryPermission(
                        status = newStatus,
                        permission = permission,
                        callback = callback
                    )
                }
            }

            PHAuthorizationStatusDenied -> {
                callback.onPermissionStatus(
                    permissionType = permission,
                    status = PermissionStatus.Denied
                )
            }

            else -> error("unknown gallery status $status")
        }
    }


    private fun askCameraPermission(
        status: AVAuthorizationStatus,
        permission: PermissionType,
        callback: PermissionCallback
    ) {
        when (status) {
            AVAuthorizationStatusAuthorized -> {
                callback.onPermissionStatus(
                    permissionType = permission,
                    status = PermissionStatus.Granted
                )
            }

            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.Companion.requestAccessForMediaType(AVMediaTypeVideo) { isGranted ->
                    if (isGranted) {
                        callback.onPermissionStatus(
                            permissionType = permission,
                            status = PermissionStatus.Granted
                        )
                    } else {
                        callback.onPermissionStatus(
                            permissionType = permission,
                            status = PermissionStatus.Denied
                        )
                    }
                }
            }

            AVAuthorizationStatusDenied -> {
                callback.onPermissionStatus(
                    permission,
                    PermissionStatus.Denied
                )
            }

            else -> error("unknown camera status $status")
        }
    }
}

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager = remember {
    PermissionsManager(callback)
}