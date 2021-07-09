# wish-1075-tv
TV App for Wish 107.5

Mockup: https://www.figma.com/file/E8cbUpuCHZBpntbK2BwG3S/Wish-TV-App

<br /><br />

# Installation
1. Go to <project_path>/Wish1075
2. yarn install
- do not use npm as there are dependency problem

<br /><br />
# Running the application
1. Go to <project_path>/Wish1075
2. `yarn start`
3. Open <project_path>/Wish1075/android with Android Studio
4. Run to the target device

<br /><br />

# Distribution
On the fascheduler DeviceListDialog, call the following after remote start activity:

```
String packageName = "com.wish1075";
String activityName = "com.wishfm.tvapp.ExitActivity";
String actionName = "com.huawei.activity.action.ExitActivity";//"android.intent.action.Exit";
AZUtil.startLocalActivity(mContext, packageName, activityName, actionName, true);
```

<br /><br />


