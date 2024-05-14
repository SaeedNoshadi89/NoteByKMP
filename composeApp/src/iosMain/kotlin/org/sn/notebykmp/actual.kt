@file:OptIn(ExperimentalForeignApi::class)

package org.sn.notebykmp

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSUUID.Companion.UUID

actual fun randomUUID(): String = UUID().UUIDString()
