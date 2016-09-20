# awesome-android-ui

## 2016年9月20日

    android 7.0 使用 DiffUtil 高效更新 RecyclerView
    DiffUtil是support-v7:24.2.0中的新工具类，它用来比较两个数据集，寻找出旧数据集-》新数据集的最小变化量。 
    说到数据集，相信大家知道它是和谁相关的了，就是我的最爱，RecyclerView。 就我使用的这几天来看，它最大的用处就是在RecyclerView刷新时，不再无脑mAdapter.notifyDataSetChanged()。 
    以前无脑mAdapter.notifyDataSetChanged()有两个缺点：
1. 不会触发RecyclerView的动画（删除、新增、位移、change动画）
2. 性能较低，毕竟是无脑的刷新了一遍整个RecyclerView , 极端情况下：新老数据集一模一样，效率是最低的。
    
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