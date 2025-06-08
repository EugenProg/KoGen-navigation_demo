# KoGen Navigation: Руководство пользователя

**KoGen Navigation** — это библиотека для Jetpack Compose, которая использует кодогенерацию (KSP) для создания типобезопасной (type-safe) и удобной системы навигации, избавляя вас от рутинного кода.

---

## 🚀 Установка и Настройка

Библиотека опубликована в **Maven Central**. Чтобы начать ей пользоваться, нужно выполнить несколько простых шагов.

### Шаг 1: Подключаем плагин KSP

Сначала убедитесь, что плагин KSP подключен к вашему проекту.

**В файле `build.gradle.kts` корневого проекта:**
```kotlin
plugins {
    // ...
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
}
```
**Важно:** *Будьте внимательны с версиями. Версия KSP `2.1.0-1.0.29` означает, что вам нужна версия Kotlin `2.1.0` в вашем проекте. Всегда сверяйтесь с [официальной таблицей совместимости KSP](https://github.com/google/ksp/releases).*

**В файле `build.gradle.kts` вашего модуля:**
```kotlin
plugins {
    // ...
    id("com.google.devtools.ksp")
}
```

### Шаг 2: Добавляем зависимости

В `dependencies` вашего модуля добавьте все необходимые зависимости: саму библиотеку Jetpack Navigation, KoGen Navigation и ее KSP-процессор.

```kotlin
dependencies {
    // Сама библиотека Jetpack Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // KoGen Navigation
    implementation("io.github.eugenprog:navigation-compose:1.0.0")
    ksp("io.github.eugenprog:navigation-compose:1.0.0")
}
```
**Важно:** *Всегда проверяйте последние актуальные версии библиотек. Версии для `implementation` и `ksp` должны совпадать.*

### Шаг 3: Настраиваем кодогенерацию

В `build.gradle.kts` вашего модуля добавьте блок `ksp` для настройки генератора.

```kotlin
ksp {
    arg("packageName", "com.myawesome.project")
    arg("defaultAnimation", "slideLeft")
    arg("viewModelInjector", "koin")
}
```
* `packageName` (**обязательный**) — нужен для того, чтобы сгенерированные классы находились в правильном пространстве имен вашего проекта.
* `defaultAnimation` (опциональный) — анимация по умолчанию для всех переходов. Возможные значения: `slideLeft`, `slideRight`, `slideUp`, `slideDown`, `fade`, `none`.
* `viewModelInjector` (опциональный) — для автоматической подстановки ViewModel. Возможные значения: `koin`, `hilt`.

---

## ⚙️ Основное Использование

### 1. Создание Экрана

Все, что вам нужно сделать, — это пометить вашу Composable-функцию аннотацией `@KoGenScreen`. Параметры функции станут аргументами для навигации.

```kotlin
@KoGenScreen
@Composable
fun MyAwesomeScreen(
    // NavController будет предоставлен автоматически
    navController: NavHostController, 
    // Этот параметр будет превращен в обязательный аргумент навигации
    myArgument: String,
    // ViewModel будет обработан и исключен из списка аргументов
    viewModel: MyViewModel = koinViewModel()
) {
    // ... ваш UI ...
}
```
После сборки проекта KSP сгенерирует все необходимое для этого экрана.

### 2. Осуществление навигации

Для каждого экрана генератор создает специальный класс `ActionTo[ScreenName]` для типобезопасного перехода.

Например, для `MyAwesomeScreen` будет сгенерирован класс `ActionToMyAwesome`. Чтобы перейти на этот экран, используйте сгенерированную `extension`-функцию `MapsSafety`:

```kotlin
// Вызов навигации с другого экрана
buttonClick = {
    navController.navigateSafety(
        ActionToMyAwesome(myArgument = "Hello from another screen!")
    )
}
```
Вам больше не нужно помнить имена аргументов или конструировать URL вручную — если вы забудете параметр или укажете неверный тип, проект не скомпилируется.

### 3. Отображение графа навигации

Библиотека сгенерирует для вас готовую Composable-функцию `AppNavHost` (или с другим именем, указанным в `navHostName`), которую нужно просто разместить в вашем UI.

```kotlin
@Composable
fun MainScreen() {
    // ...
    val navController = rememberNavController()
    AppNavHost(navController = navController)
}
```

---

## ✨ Продвинутые Возможности

### Анимации Переходов

Вы можете легко управлять анимациями:

* **Глобально:** через параметр `defaultAnimation` в блоке `ksp` в `build.gradle.kts`.
* **Для конкретного экрана:** через параметр в аннотации `@KoGenScreen(animation = NavigationAnimation.Fade)`.

### Возврат Результата с Экрана

Библиотека предоставляет удобный способ вернуть данные на предыдущий экран.

#### Шаг 1: Определяем ключ для результата
```kotlin
sealed class NavigationResultValues<T>(override val key: String, override val defaultValue: T) :
    NavigationResultKey<T> {
    data object ShowToast : NavigationResultValues<Boolean>("showToast", false)
}
```

#### Шаг 2: Возвращаем результат при уходе с экрана
Используйте сгенерированную функцию `popBackSafety` с параметром `backStackData`.

```kotlin
// На экране, который возвращает результат
buttonClick = {
    navController.popBackSafety(
        backStackData = BackStackData(NavigationResultValues.ShowToast, true)
    )
}
```

#### Шаг 3: Получаем результат на предыдущем экране
Используйте `getResultData` внутри `LaunchedEffect` для "прослушивания" результата.
```kotlin
// На экране, который ожидает результат
LaunchedEffect(Unit) {
    if (navController.getResultData(NavigationResultValues.ShowToast) == true) {
        Toast.makeText(context, "It's a toast from nav result", Toast.LENGTH_SHORT).show()
    }
}
```
---

Полный пример использования, документацию и саму библиотеку вы можете найти в этом репозитории.
