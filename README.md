# Storo

[![](https://jitpack.io/v/lowlevel-studios/storo.svg)](https://jitpack.io/#lowlevel-studios/storo)
[![Build Status](https://travis-ci.org/lowlevel-studios/storo.svg?branch=master)](https://travis-ci.org/lowlevel-studios/storo)

An Android library to cache any serializable objects to disk, using a LRU cache implementation, with the possibility to specify an expiry time for each entry and a maximum size that can be allocated.

## How to use

### Integrate the library in your project

Add the JitPack repository to your project's build.gradle file:

```
repositores {
    maven { url 'https://jitpack.io' }
}
```

And then add the following line into the 'dependencies' block:

```
compile('com.github.lowlevel-studios:storo:1.0.3') {
    transitive = true
}
```

### Initialize Storo

```java
StoroBuilder.configure(8192)  // maximum size to allocate in bytes
    .setDefaultCacheDirectory(this)
    .initialize();
```

### Put an object

```java
Storo.put("key", object).execute(); // store object without an expiry
Storo.put("key", object).setExpiry(2, TimeUnit.HOURS).execute();  // store object with an expiry of 2 hours
```

### Get an object

```java
MyObject object = Storo.get("key", MyObject.class).execute();
```

### Delete an object

```java
boolean result = Storo.delete("key");
```

### Check if an object exists

```java
boolean result = Storo.contains("key");
```

### Check if an object has expired

```java
Boolean result = Storo.hasExpired("key").execute();   // null if the object does not exist
```

### Clear the entire cache

```java
boolean result = Storo.clear();
```

### Async calls
```java
Storo.put("key", object, ...).async(new Callback<Boolean>);
Storo.get("key", MyObject.class).async(new Callback<MyObject>);
Storo.hasExpired("key").async(new CallBack<Boolean>);
```

### RxJava support

If no callback is passed to ```async()```, it will return an ```Observable``` that will be scheduled on a background thread and observed on the main thread.

## License

    Copyright 2016 Lowlevel Studios

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
       http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
