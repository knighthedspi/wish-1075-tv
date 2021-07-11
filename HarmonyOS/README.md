**Wish 107.5 Demo**

Wish 107.5 Harmony OS demo app.

**Structure**

There are 2 modules:

- entry: handler module, need to deploy on mobile phone to control the game
- pascheduler: Scheduler module, used to call with Android app and HarmonyOS app

**How to deploy**

- Deploy the **entry** module to the HarmonyOS device such as mobile phone or tablet.
- Deploy the **fascheduler** module with the [Android demo app](https://github.com/AALA-DTSE-Projects/Android-WishDemo) on the target device such as tablet or TV
- Open the **fascheduler** app on the target device then grant multi-device collaboration permission
- Open the **Android** app on the target device then grant multi-device collaboration permission
- Open the **entry** app on the control device then grant multi-device collaboration permission
- Connect all devices in the same wifi network
- Login with the same Huawei ID on all devices
- Set up Bluetooth connection between all devices
- Open the **entry** app and click on cast button then click the device name to start the app on target device
- You can use cast button to distribute the application to different devices

**Demo result**

![](screenshot/wish_demo.gif)

