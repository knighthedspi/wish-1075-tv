**Wish 107.5 Demo**

Wish 107.5 Harmony OS demo app.

**Structure**

There are 3 modules:

- entry: to build the hybric package
- famain: to run on smartwatch
- fascheduler: Scheduler module, used to call with Android app and HarmonyOS app

**How to deploy**

- Deploy the **entry** module to the HarmonyOS device such as mobile phone or tablet.
- Deploy the **fascheduler** module with the [Android demo app](https://codehub-g.huawei.com/HarmonyOS/Wish/Wish-TV/home) on the target device such as tablet or TV
- Open the **Android** app on the all devices then click cast button and grant multi-device collaboration permission
- Connect all devices in the same wifi network
- Login with the same Huawei ID on all devices
- Set up Bluetooth connection between all devices
- You can use cast button to distribute the application to different devices

**Demo result**

![](screenshot/wish_demo.gif)

