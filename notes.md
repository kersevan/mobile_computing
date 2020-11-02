## CONFIGURE THE PROJECT
### NAME
Name is the name of your application. The project directory, and the name visible in Android Studio are
composed from this name, removing the spaces. If it is composed by several words, capitalize them. For
instance: My First App
### PACKAGE NAME
Package Name is the unique identification of your application and usually contains also your organization and personal id inside it. It should be specified as a domain name in reverse: for instance, starting with org or com (non-profit or commercial organizations), organization name, developer or team name, and finally the app name. In my case, for instance, it could be: org.feup.apm.myfirstapp.
### DELETE TEST FRAMEWORKS/FILES
You can see in Android Studio, under the app/java project directory, three namespaces: one is the chosen project namespace, and the other two are for testing (using TDD) with two different frameworks (android test (espresso) and unit tests). For now, you can delete those two projects (delete first the files inside and then the namespaces). Also you can delete the corresponding subdirs in the file system.

Also, going to the File → Project Structure → Dependencies → app module (or the corresponding button on Android Studio toolbar) dialog box, you should delete the espresso-core, and the 2 junit external dependencies (libraries). This will lower the app size.

## ADD AN ACTIVITY
From the Android Studio menu select File
→ New → Activity → Gallery … . A ‘New Android Activity’ wizard appears. Select ‘Empty Activity’ (the simplest one) and click the Next button at the bottom. 
The activity that is added is empty. But It has already an icon, a style and a layout. For our app we will need to delete or modify the generated layout (it is generated with only a ConstraintLayout on it). In the form that is now presented, we need to specify the activity name (the class name, which is also added to the manifest), generate a layout file (maintain this selected) and its name, and if it is our launcher activity (the one that is executed when the user starts the app). Select this option and maintain the next ones. For the class name choose FirstActivity. We will use the ‘Up Navigation’ functionality that was automatically available after API level 16. As we are supporting Android versions with API 21 or higher, we don´t need to use a support library for that.

### LINEAR LAYOUT
1. Open the res/layout/activity_first.xml file in design mode. You can see that it only contains a ConstraintLayout. These layouts allow complex positioning of their children through conditions.

2. In the Component Tree select the constraintLayout and in the context menu (mouse right button) use Convert View … to convert it to the standard and simple LinearLayout

3. You can now delete (because it takes space in the APK), in the Project Structure Dependencies tab, the constraint layout support library, as we do not need it anymore

### ADD A TEXT FIELD
1. Add a text field (PlainText, implemented as EditText view with the id edit_message) empty but with a hint (“Enter a message”) inside the Linear Layout. All the interface strings should be first defined in the strings.xml resource file (res\values), and referenced with the syntax @string/<string name>.

2. Add a button, also inside the Linear Layout but following the text field, with a label “Send” (put it also in the strings resource) and an id of button_send. Also remove the layout-weight attribute value.

3. The property layout_width of the EditText can be defined as zero: 0dp, but with the property layout_weight as 1. Android tries to allocate space to interface elements proportional to their weights, but using the minimum to be visible. When a weight is not specified it is assumed to be 0

Strings:
<resources>
    <string name="app_name">My First App</string>
    <string name="edit_hint">Enter a message</string>
    <string name="button_send">Send</string>
</resources>

Layout:
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal"
    tools:context=".FirstActivity">
    <EditText
    android:id="@+id/edit_message"
    android:layout_width="0dp"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:ems="10"
    android:hint="@string/edit_hint"
    android:inputType="text"/>
    <Button
    android:id="@+id/button_send"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="@string/button_send"/>
</LinearLayout>

### ADD ICON ON THE MAIN SCREEN
Copy the rest_icon.png file to the drawable resource directory. In the activity onCreate() method add this icon to the activity Action Bar (the top header bar):
```
ActionBar bar = getSupportActionBar();
if (bar != null) {
    bar.setIcon(R.drawable.rest_icon);
    bar.setDisplayShowHomeEnabled(true);
}
```