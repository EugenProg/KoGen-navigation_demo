# KoGen Navigation: User Guide

**KoGen Navigation** is a library for Jetpack Compose that uses code generation (KSP) to create a type-safe and convenient navigation system, saving you from boilerplate code.

---

## 🚀 Installation and Setup

The library is published on **Maven Central**. To start using it, you need to follow a few simple setup steps.

### Step 1: Apply the KSP Plugin

First, make sure the KSP plugin is applied to your project.

**In your root `build.gradle.kts` file:**
```kotlin
plugins {
    // ...
    // The KSP version must match your Kotlin version
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}
```
**Important:** *Be mindful of the versions. A KSP version of `2.1.0-1.0.29` means you need Kotlin version `2.1.0` in your project. Always refer to the [official KSP compatibility table](https://github.com/google/ksp/releases).*

**In your module's `build.gradle.kts` file:**
```kotlin
plugins {
    // ...
    id("com.google.devtools.ksp")
}
```

### Step 2: Add Dependencies

In your module's `dependencies` block, add all the necessary dependencies: the Jetpack Navigation library itself, KoGen Navigation, and its KSP processor.

```kotlin
dependencies {
    // The Jetpack Navigation library itself
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // KoGen Navigation
    implementation("io.github.eugenprog:navigation-compose:1.0.0")
    ksp("io.github.eugenprog:navigation-compose:1.0.0")
}
```
**Important:** *Always check for the latest library versions. The versions for your library's `implementation` and `ksp` configurations must match.*

### Step 3: Configure Code Generation

In your module's `build.gradle.kts`, add a `ksp` block to configure the generator.

```kotlin
ksp {
    arg("packageName", "com.myawesome.project")
    arg("defaultAnimation", "slideLeft")
    arg("viewModelInjector", "koin")
}
```
* `packageName` (**required**) – Needed so that the generated classes are placed in the correct namespace of your project.
* `defaultAnimation` (optional) – The default animation for all transitions. Possible values: `slideLeft`, `slideRight`, `slideUp`, `slideDown`, `fade`, `none`.
* `viewModelInjector` (optional) – For automatic provision of a ViewModel. Possible values: `koin`, `hilt`.

---

## ⚙️ Basic Usage

### 1. Creating a Screen

All you need to do is annotate your Composable function with `@KoGenScreen`. The function's parameters will become the navigation arguments.

```kotlin
@KoGenScreen
@Composable
fun MyAwesomeScreen(
    // NavController will be provided automatically
    navController: NavHostController, 
    // This parameter will be turned into a required navigation argument
    myArgument: String,
    // The ViewModel will be processed and excluded from the list of arguments
    viewModel: MyViewModel = koinViewModel()
) {
    // ... your UI ...
}
```
After building the project, KSP will generate everything necessary for this screen.

### 2. Performing Navigation

For each screen, the generator creates a special `ActionTo[ScreenName]` class for type-safe navigation. For example, for `MyAwesomeScreen`, an `ActionToMyAwesome` class will be generated.

To navigate to this screen, use the generated `MapsSafety` extension function:

```kotlin
// Example of a navigation call from another screen
Button(onClick = {
    navController.navigateSafety(
        ActionToMyAwesome(myArgument = "Hello from another screen!")
    )
}) {
    Text("Go to Awesome Screen")
}
```
You no longer need to remember argument names or construct URLs by hand—if you forget a parameter or provide the wrong type, the project will not compile.

### 3. Displaying the Navigation Graph

The library will generate a ready-to-use `AppNavHost` Composable function for you (or a different name if specified in `navHostName`), which you simply need to place in your UI.

```kotlin
@Composable
fun MainScreen() {
    // ...
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
```

---

## ✨ Advanced Features

### Transition Animations

You can easily manage animations:

* **Globally:** via the `defaultAnimation` parameter in the `ksp` block in `build.gradle.kts`.
* **For a specific screen:** by overriding the global setting with a parameter in the annotation: `@KoGenScreen(animation = NavigationAnimation.Fade)`.

### Returning a Result from a Screen

The library provides a convenient way to return data to the previous screen.

#### Step 1: Define a Key for the Result
```kotlin
sealed class NavigationResultValues<T>(override val key: String, override val defaultValue: T) :
    NavigationResultKey<T> {
    data object ShowToast : NavigationResultValues<Boolean>("showToast", false)
}
```

#### Step 2: Return the Result When Leaving the Screen
Use the generated `popBackSafety` function with the `backStackData` parameter.

```kotlin
// On the screen that returns a result
Button(onClick = {
    navController.popBackSafety(
        backStackData = BackStackData(NavigationResultValues.ShowToast, true)
    )
}) { ... }
```

#### Step 3: Receive the Result on the Previous Screen
Use `getResultData` inside a `LaunchedEffect` to "listen" for the result.
```kotlin
// On the screen that expects a result
LaunchedEffect(Unit) {
    if (navController.getResultData(NavigationResultValues.ShowToast) == true) {
        Toast.makeText(context, "It's a toast from nav result", Toast.LENGTH_SHORT).show()
    }
}
```

[README.ru](https://github.com/EugenProg/KoGen-navigation_demo/blob/master/README.ru.md)
