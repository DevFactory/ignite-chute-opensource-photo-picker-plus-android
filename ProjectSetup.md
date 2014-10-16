
Introduction to Chute
====  

This document will help you successfully set up the basic things that are required for
getting started with Chute.

You can download the Chute SDK for Android at:
[https://github.com/chute/Chute-SDK-V2-Android](https://github.com/chute/Chute-SDK-V2-Android).

You can create a Chute developer account and make a new app in Chute at [http://apps.getchute.com/](http://apps.getchute.com/)

- For URL you can enter http://getchute.com/ if you don't have a site for your app.
- For Callback URL you can use http://getchute.com/oauth/callback if you don't need callbacks for another purpose.
	
	![appcredetials1](/screenshots/appcredentials1.png)![appcredentials2](/screenshots/appcredentials2.png)  

  
Adding PhotoPicker+ library to your project
====  

* Download the library and place it in your project's root folder. 
  Edit settings.gradle in order to include the dependency:
```groovy
  include ':library'
```  
  Add the dependency to your project by navigating from File -> Project Structure -> Modules -> Add Module Dependency
  
  ![moduledependency](/screenshots/moduledependency.png)
  
  Add the dependency in your project's build.gradle file
```groovy  
 dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:20.+'
    compile project(':library')
  }
``` 

or

* Add in PhotoPicker+ as a maven dependency.
``` xml
<dependency>
	<groupId>com.getchute.android.libs.photopickerplus</groupId>
	<artifactId>library</artifactId>
	<version>2.0.6</version>
</dependency>
```

or
 
* Add in PhotoPicker+ as a gradle dependency.   

``` groovy
compile 'com.getchute.android.libs.photopickerplus:library:2.0.6'
```

``` groovy 
repositories { 
    mavenCentral()
    }
```
 This way Gradle will download the needed resources automatically for you.
 
 Also don't put it in the top-level build.gradle file -- the repositories and dependencies blocks there tell Gradle where to find the Android-Gradle plugin for the build system itself. Put it in the build.gradle file in your application module directory.
    
Android manifest setup
====

* Add the following permissions that are required for the Chute SDK:

``` xml
        <!-- Standard Permission to access the internet -->
        <uses-permission android:name="android.permission.INTERNET" />  
        <!-- Permission for caching the images on the SD card -->
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />  
        <!-- The following permissions are used by chute to track the phone id and version to determine the device used for managing the uploads and users -->
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.READ_PHONE_STATE" />
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
        <!-- Used for the http request service for the long running uploads. -->
        <uses-permission android:name="android.permission.WAKE_LOCK" />
```

