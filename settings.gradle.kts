plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "KotlinCoroutineTestProject"
include("Sample1")
include("Sample2")
include("Sample4")
include("Sample5")
include("Test4DiskLruCache")
include("Test4Flow")
