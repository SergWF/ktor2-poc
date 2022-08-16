Small POC project to learn Ktor 2.0

### Notes:
 
#### Testing:
##### JUnit
To use Junit5 you should exclude every dependency of **junit4** and **kotlin-test-junit**
example:
```kotlin
// build.gradle.kt
configurations.testImplementation {
    exclude(group = "org.jetbrains.kotlin", module = "kotlin-test-junit")
    exclude(group = "junit")
}
```

##### Mockito
To mock kotlin classes with Mockito:
1. Create a new folder to the project's **src/test/resources/mockito-extensions**
2. Inside folder make a new file **org.mockito.plugins.MockMaker**
3. Add a single line inside of the file: 
```
mock-maker-inline
```