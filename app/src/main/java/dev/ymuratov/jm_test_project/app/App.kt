package dev.ymuratov.jm_test_project.app

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.util.DebugLogger
import com.avito.android.blurlayout.BlurLayout
import com.google.android.renderscript.Toolkit
import dagger.hilt.android.HiltAndroidApp
import dev.ymuratov.jm_test_project.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application(), ImageLoaderFactory {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        BlurLayout.init(onApplyBlur = { bitmap, blurRadius -> Toolkit.blur(inputBitmap = bitmap, radius = blurRadius) })
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this).memoryCache {
            MemoryCache.Builder(this).maxSizePercent(0.5).build()
        }.diskCache {
            DiskCache.Builder().directory(cacheDir.resolve("image_cache")).maxSizeBytes(5 * 1024 * 1024).build()
        }.logger(DebugLogger()).respectCacheHeaders(false).build()
    }
}