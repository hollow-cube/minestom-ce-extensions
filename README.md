# minestom-ce-extensions
A library for bringing extensions back to [minestom-ce](https://github.com/hollow-cube/minestom-ce).

This library is not quite a drop in replacement for Minestom extensions, but it is pretty close. For many extensions,
it should work out of the box. If an extension references `MinecraftServer.getExtensionManager()` this will have to be
updated, see [Usage](#usage) for more information.

## Install

> **Warning:** The library is not actually published yet, it could still be imported with Jitpack though (i think).

`minestom-ce-extensions` is published on maven central, and can be imported like the following:
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation("dev.hollowcube:minestom-ce-extensions:<release version>")
}
```

## Usage

Once installed, using the library is as simple as replacing `MinestomServer` with `ExtensionBootstrap` during initialization.

```java
// Without minestom-ce-extensions
var server = MinecraftServer.init();
// do something
server.start("0.0.0.0", 25565);

// With minestom-ce-extensions
var server = ExtensionBootstrap.init();
// do something
server.start("0.0.0.0", 25565);
```

If you need to access the `ExtensionManager` from your code, it can be done using `ExtensionBootstrap.getExtensionManager()`.

## License
This project is licensed under the [Apache License Version 2.0](../LICENSE).
