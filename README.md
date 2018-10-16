
## SquaresLoadingView
> A SquaresLoadingView based on android.View, nicely rotation、easy to use.

[![API](https://img.shields.io/badge/API-19%2B-brightgreen.svg)](https://android-arsenal.com/api?level=19) 
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/badge/Download-1.0.0-brightgreen.svg) ](https://github.com/fairytale110/SquaresLoadingView/archive/1.0.0.zip)

### Preview

![preview.gif](https://upload-images.jianshu.io/upload_images/1781452-f256a5f5da4f9026.gif?imageMogr2/auto-orient/strip)

### Features

Supported functions：

- [x] Optionally configure the colors of each square
- [x] Manually stop and start the animation
- [x] Configurable anim's speed

- [x] Support the padding settings


Support will be forthcoming:

- [ ] Manual rotation

- [ ] Refresh header view support

- [ ] Etc 

### How to 

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
  allprojects {
     repositories {
       ...
       maven { url 'https://jitpack.io' }
     }
  }
```
Step 2. Add the dependency
```
 dependencies {
     implementation 'com.github.fairytale110:SquaresLoadingView:1.0.0'
 }
```

### Usage

```java
  <tech.nicesky.libsquaresloadingview.SquaresLoadingView
        android:id="@+id/slv_loading"
        android:padding="10dp"
        app:slv_scale_speed="0.77"
        app:slv_squares_alpha="1"
        app:slv_background="@android:color/white"
        android:layout_width="80dp"
        android:layout_height="200dp" />
```
or
```java

  private void loading(){

        int[] colorsDefault = {
                Color.parseColor("#C5523F"),
                Color.parseColor("#F2B736"),
                Color.parseColor("#499255"),
                Color.parseColor("#F2B736"),
                Color.parseColor("#499255"),
                Color.parseColor("#1875E5"),
                Color.parseColor("#499255"),
                Color.parseColor("#1875E5"),
                Color.parseColor("#C5523F"),
        };

        SquaresLoadingView squaresLoadingView = new SquaresLoadingView(this);
        squaresLoadingView.setAnimSpeed(0.5F);
        squaresLoadingView.setSquareAlpha(0.8F);
        squaresLoadingView.setColors(colorsDefault);
        squaresLoadingView.start();
        //squaresLoadingView.stop();
    }
```

### Participate in the contribution
fairytale110@foxmail.com


### Author
fairytale110@foxmail.com
> 简书: http://jianshu.com/u/d95b27ffdd3c

> 掘金: https://juejin.im/user/596d91ee6fb9a06bb874a800

> MY WEB: https://nicesky.tech


## LICENSE

```
  Copyright 2018 fairytale110

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
```
