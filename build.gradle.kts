import com.android.build.api.variant.AndroidComponentsExtension
import java.util.Locale

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.dagp)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.plugin.compose) apply false
    alias(libs.plugins.compose) apply false
}

subprojects {
    // Workaround for https://youtrack.jetbrains.com/issue/CMP-7283
    if (project.hasProperty("cmpWorkaround")) {
        plugins.withId("com.android.base") {
            plugins.withId("org.jetbrains.kotlin.plugin.compose") {
                extensions.findByType(AndroidComponentsExtension::class.java)?.apply {
                    beforeVariants(selector().all()) { variant ->
                        val variantName =
                            variant.name.replaceFirstChar { it.titlecase(Locale.getDefault()) }
                        tasks.named { it == "explodeCodeSource$variantName" }.configureEach {
                            dependsOn("generateActualResourceCollectorsForAndroidMain")
                        }
                    }
                }
            }
        }
    }
}