IRKit4J
=================

IRKit4J is a library for accessing IRKit which is send IR from remote.

# What's IRKit

IRKit - Open Source WiFi Connected Infrared Remote Controller
* [http://getirkit.com/en/](http://getirkit.com/en/)  (in English)
* [http://getirkit.com/](http://getirkit.com/) (in Japanese)

# Quick start
`build.gradle`
```gradle
repositories {
  mavenCentral()
  maven { url 'https://raw.github.com/tomorrowkey/IRKit4J/master/repository'}
}

dependencies {
  ...
  compile 'jp.tomorrowkey.irkit4j:irkit4j:0.0.1'
}
```

`Main.java`
```java
InetAddress inetAddress = getInetAddressFromArguments(args);
if (inetAddress == null) {
    usage();
    return;
}

try {
    // Acquire Client Token from IRKit in the same network
    String clientToken = LocalIRKit.getKeys(inetAddress);

    // Acquire Device ID and Client Key from https://api.getirkit.com/
    StringKeyValue authenticationToken = RemoteIRKit.postKeys(clientToken);
    String deviceId = authenticationToken.get("deviceid");
    String clientKey = authenticationToken.get("clientkey");

    // Record a signal of your IRKit.
    Messages messages = RemoteIRKit.getMessages(clientKey, true);

    if (messages == null) {
        System.out.println("Can not record any signals.");
        return;
    }
    // Command send the signal to your IRKit via internet.
    RemoteIRKit.postMessage(clientKey, deviceId, messages.getMessage());
} catch (IOException e) {
    e.printStackTrace();
}
```

# License

```
Copyright 2014 Tomoki Yamashita

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