Small reproducer for https://github.com/autonomousapps/dependency-analysis-gradle-plugin/issues/912 showing a task wiring issue somewhere in KGP.

```
$ gradle projectHealth
...
> Task :app:explodeCodeSourceDebug FAILED

FAILURE: Build failed with an exception.

* What went wrong:
A problem was found with the configuration of task ':app:explodeCodeSourceDebug' (type 'CodeSourceExploderTask').
  - Gradle detected a problem with the following location: '/path/to/Multiplatform/app/build/generated/compose/resourceGenerator/kotlin/androidMainResourceCollectors/multiplatform/app/generated/resources/ActualResourceCollectors.kt'.
    
    Reason: Task ':app:explodeCodeSourceDebug' uses this output of task ':app:generateActualResourceCollectorsForAndroidMain' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.
    
    Possible solutions:
      1. Declare task ':app:generateActualResourceCollectorsForAndroidMain' as an input of ':app:explodeCodeSourceDebug'.
      2. Declare an explicit dependency on ':app:generateActualResourceCollectorsForAndroidMain' from ':app:explodeCodeSourceDebug' using Task#dependsOn.
      3. Declare an explicit dependency on ':app:generateActualResourceCollectorsForAndroidMain' from ':app:explodeCodeSourceDebug' using Task#mustRunAfter.
    
    For more information, please refer to https://docs.gradle.org/8.9/userguide/validation_problems.html#implicit_dependency in the Gradle documentation.

* Try:
> Run with --stacktrace option to get the stack trace.
> Run with --info or --debug option to get more log output.
> Run with --scan to get full insights.
> Get more help at https://help.gradle.org.

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.9/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.

BUILD FAILED in 1s
22 actionable tasks: 10 executed, 12 up-to-date
```

If executed with `-PcmpWorkaround`, the above error vanishes and another error surfaces, listed in
https://github.com/autonomousapps/dependency-analysis-gradle-plugin/issues/1345:

```
$ gradle -PcmpWorkaround projectHealth
...
> Task :lib:projectHealth
/path/to/Multiplatform/lib/build.gradle.kts
These transitive dependencies should be declared directly:
  api("androidx.compose.runtime:runtime:1.7.5")
  api("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
  implementation("androidx.compose.material3:material3:1.3.1")
  implementation("androidx.compose.ui:ui-text:1.7.5")
  implementation("androidx.compose.ui:ui:1.7.5")

Module structure advice
This project uses limited Android features and could be a JVM project.
* Has Android library dependencies.

> Task :app:projectHealth
/path/to/Multiplatform/app/build.gradle.kts
These transitive dependencies should be declared directly:
  implementation("androidx.compose.foundation:foundation-layout:1.7.5")
  implementation("androidx.compose.material3:material3:1.3.1")
  implementation("androidx.compose.runtime:runtime:1.7.5")
  implementation("androidx.compose.ui:ui-text:1.7.5")
  implementation("androidx.compose.ui:ui:1.7.5")
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(project(":lib"))

Deprecated Gradle features were used in this build, making it incompatible with Gradle 9.0.

You can use '--warning-mode all' to show the individual deprecation warnings and determine if they come from your own scripts or plugins.

For more on this, please refer to https://docs.gradle.org/8.9/userguide/command_line_interface.html#sec:command_line_warnings in the Gradle documentation.
```