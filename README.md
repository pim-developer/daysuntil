# Days Until: Countdown App

*Hi!* Days Until is a Modern Android App which displays the *days, hours, minutes, and seconds* till your event ⏳.

![](https://github.com/pim-developer/daysuntil/blob/main/demo_2.gif)

## Architecture

Follows the official Google best practises [guide](https://developer.android.com/topic/architecture):

- **Activity Structure:** Single Activity Architecture
- **Design Pattern:** MVVM (Model-View-ViewModel)
- **Modularization:** 1 Module

## Folder Structure

The project structure is organized as follows (seperation of concerns):

- `app/`
    - `src/`
        - `main/`
            - `java/com/dhaen/daysuntil`
                - `ui/` - Contains all UI-related classes and components
                - `presentation/` - ViewModels for handling UI data
                - `model/` - Data models
                - `data/` - Data management classes
                - `di/` - Dependency Injection class modules
            - `res/` - Resources like layouts, drawables, and strings
        - `test/` - (PLANNED) Unit testing files
        - `androidTest/` - (PLANNED) Android specific Instrumentation testing files

## Dependencies

- [**MongoDB**](https://www.mongodb.com/docs/atlas/device-sdks/sdk/kotlin/)  (Database) - MongoDB offers a nice free tier for [BAAS](https://www.mongodb.com/products/platform/atlas-database). Easily replace with [Room](https://developer.android.com/jetpack/androidx/releases/room) if you prefer.
- [**Hilt**](https://developer.android.com/training/dependency-injection/hilt-android) (Dependency Injection) - For a future proof maintainable codebase.
- **Compose Navigation** - To handle in-app navigation using Jetpack Compose.
- **Jetpack Compose** - For modern UI development.
- **Material 3** - The latest version of Material Design.
- **Firebase Crashlytics** (Planned) - For crash reporting.
- **In-App Updates** (Planned) - For updating app code automatically.


### Version Catalog

The project uses a version catalog file, `libs.versions.toml`, to manage all dependencies across the project. This file is a `.toml` format that helps manage dependencies and versions in a scalable, organized, and modern way. Learn more here: [Migrate to Version Catalogs](https://developer.android.com/build/migrate-to-catalogs).

## Contributing

Please feel free to contribute! There are TODO's (see Issues Tab)

## License

This project is licensed under the [GPL-3.0 license](https://github.com/pim-developer/daysuntil?tab=GPL-3.0-1-ov-file).
