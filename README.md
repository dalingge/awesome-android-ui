# awesome-android-ui

## 2016年9月20日

    android 7.0 使用 DiffUtil 高效更新 RecyclerView
    修复了部分BUG
## 2016年9月19日

### 解决了Java8和Butterknife8冲突问题

#### /build.gradle
```groovy
buildscript {
    dependencies {
          classpath 'com.android.tools.build:gradle:2.2.0-rc2'
    }
}
```
 注意使用2.2.+需要最新版本[Gradle](http://www.javadoc.io/doc/com.android.tools.build/gradle/)插件
#### /app/build.gradle
```groovy
android {
     compileSdkVersion 24
     buildToolsVersion "24.0.2"
    defaultConfig {
        targetSdkVersion 24
        jackOptions {
            enabled true
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

## Sample Dependencies
```groovy
dependencies {
    compile 'com.jakewharton:butterknife:8.4.0'
    annotationProcessor  'com.jakewharton:butterknife-compiler:8.4.0'
}
```