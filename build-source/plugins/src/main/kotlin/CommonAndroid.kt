import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val START_ACTIVITY = "tech.gelab.cardiograph.singleactivity.impl.SingleActivity"
private const val START_ACTIVITY_KEY = "startActivity"

fun BaseExtension.commonAndroid(target: Project) {
    configureDefaultConfig()
    configureBuildTypes()
    configureBuildFeatures()
    configureCompileOptions()

    target.suppressOptIn()
}

private fun BaseExtension.configureDefaultConfig() {
    compileSdkVersion(ApkConfig.COMPILE_SDK)
    defaultConfig{
        minSdk = ApkConfig.MIN_SDK
        targetSdk = ApkConfig.TARGET_SDK
        versionCode = ApkConfig.VERSION_CODE
        versionName = ApkConfig.VERSION_NAME

        consumerProguardFiles(
            "consumer-rules.pro"
        )

        testOptions {
            unitTests {
                isIncludeAndroidResources = true
            }
        }
    }
}

private fun BaseExtension.configureBuildTypes() {
    buildTypes{
        defaultConfig {
            manifestPlaceholders[START_ACTIVITY_KEY] = START_ACTIVITY
        }
        maybeCreate("debug").apply {
            isDebuggable = true
        }
        maybeCreate("internal").apply {
            setMatchingFallbacks("debug")
            sourceSets.getByName(this.name).setRoot("src/debug")
            signingConfig = signingConfigs.getByName("debug")
        }
        maybeCreate("release")
    }
}

private fun BaseExtension.configureBuildFeatures(){
    buildFeatures.buildConfig = true
    buildFeatures.viewBinding = false
    buildFeatures.aidl = false
    buildFeatures.compose = false
    buildFeatures.prefab = false
    buildFeatures.renderScript = false
    buildFeatures.resValues = false
    buildFeatures.shaders = false
}

private fun BaseExtension.configureCompileOptions() {
    compileOptions.sourceCompatibility = JavaVersion.VERSION_11
    compileOptions.targetCompatibility = JavaVersion.VERSION_11
}

private fun Project.suppressOptIn() {
    tasks.withType<KotlinCompile>()
        .configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_11)
                freeCompilerArgs.addAll(
                    "-opt-in=kotlin.time.ExperimentalTime",
                    "-opt-in=kotlin.RequiresOptIn"
                )
            }
        }
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.debug(
    action: BuildTypeT.() -> Unit
) {
    maybeCreate("debug").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.internal(
    action: BuildTypeT.() -> Unit
) {
    maybeCreate("debug").action()
}

fun <BuildTypeT> NamedDomainObjectContainer<BuildTypeT>.release(
    action: BuildTypeT.() -> Unit
) {
    maybeCreate("release").action()
}