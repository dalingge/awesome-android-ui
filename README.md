# awesome-android-ui

## 2017年6月25日

### 截屏合并图片ScreenshotUtil

## 2017年6月7日

### 增加AppCompat v23.2 - 夜间模式
 
 https://kingideayou.github.io/2016/03/07/appcompat_23.2_day_night/

## 2017年4月6日

### 增加SplashActivity 使用SpringAnimation实现弹簧动画

## 2017年3月31日

### 练习约束布局
### SpringAnimation 类是最近（25.3.0版本）才添加在支持库中的一个类，它主要是为了让实现弹性动画变得更加方便，其实facebook在很久以前的Rebound库就实现了这样的动画，而且效果非常好，不过现在有官方支持肯定是更好了。本文先来看看SpringAnimation的基本用法，然后再将它和Rebound做一些比较。
    
## 2016年12月06日

### 在高粱指引页面增加了弧形layout布局

## 2016年11月30日

### 增加高亮指示控件

## 2016年10月10日

### 增加17种loading动画
     

## 2016年9月20日

###  增加了卡片左右滑动取消小控件
     通过ViewDragHelper实现左右滑动喜欢或不喜欢

## 2016年9月20日

### android 7.0 使用 DiffUtil 高效更新 RecyclerView
    DiffUtil是support-v7:24.2.0中的新工具类，它用来比较两个数据集，寻找出旧数据集-》新数据集的最小变化量。 
    说到数据集，相信大家知道它是和谁相关的了，就是我的最爱，RecyclerView。 就我使用的这几天来看，它最大的用处就是在RecyclerView刷新时，不再无脑mAdapter.notifyDataSetChanged()。 
    以前无脑mAdapter.notifyDataSetChanged()有两个缺点：
    1. 不会触发RecyclerView的动画（删除、新增、位移、change动画）
    2. 性能较低，毕竟是无脑的刷新了一遍整个RecyclerView , 极端情况下：新老数据集一模一样，效率是最低的。
### 修复了部分BUG

## 2016年9月19日

### 解决了Java8和Butterknife8冲突问题

#### /build.gradle
```groovy
buildscript {
    dependencies {
          classpath 'com.android.tools.build:gradle:2.2.0'
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