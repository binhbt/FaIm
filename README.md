# repo
build.gradle for allprojects
```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
add to app build.gradle
```gradle
dependencies {
    compile 'com.github.binhbt:FaBoiler:1.0.4'
}
```
Demo at:
https://github.com/binhbt/FaDemo
