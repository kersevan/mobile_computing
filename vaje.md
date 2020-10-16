## VAJE 1 28.9.2020

4 komponente:
- activities -> user interface + koda za odziv na dogodke
- services -> nima user interfacea, procesiranje podatkov
- broadcast receivers -> odzivajo se na globalne dogodke v sistemu, imajo samo metodo za ta odziv, npr. ko je baterija skoraj prazna to oznani
- content providers -> ko aplikacija procesira podatke, kolekcija podatkov, podpira CRUD operacije, npr. kontakti v telefonu

Vsaka aplikacija ima **manifest.xml**, kjer so deklarirane komponente.

JetPack in androidx so neki extensioni od Googla za Android - knjižnice

Activity je odgovoren za samo en screen.

Buildanje source kode:
- .java (sources) --javac--> .class (java byte code) --dex--> .dex (dalvik files) --aapk--> .apk
- .xml (resources) describe elements of the app, ikone, slike ... --> .apk
- binarni fajli (assets), aplikacija jih lahko prebere ob runtime-u, zvok, video ... --> .apk 

Gradle je engine, ki zbilda aplikacijo (verjetno je že vključeno v Android Studio).

EKSEKUCIJA APLIKACIJE (IoC - Inversion of Control pattern):
- main thread = looper thread
- message queue 
- call  -> callback (life-cycle)
        -> event listener (handler)
- call izvede main thread

(ANR box - Android not responding - če se callback izvaja preveč časa)

----------------MOJE-------------------  
**Android Debug Bridge (adb)** is a versatile command-line tool that lets you communicate with a device. The adb command facilitates a variety of device actions, such as installing and debugging apps, and it provides access to a Unix shell that you can use to run a variety of commands on a device. It is a client-server program that includes three components:
* A client, which sends commands. The client runs on your development machine. You can invoke a client from a command-line terminal by issuing an adb command.
* A daemon (adbd), which runs commands on a device. The daemon runs as a background process on each device.
* A server, which manages communication between the client and the daemon. The server runs as a background process on your development machine.
---------------------------------------  



## VAJE 2 12.10.2020 (en teden nazaj ni bilo zaradi praznika)

DIALOG BOXES:
- implemented in classes inheriting from android.app.Dialog
- predefined dialog boxes:  
        * AlertDialog  
        * ProgressDialog  
        * DatePickerDialog  
- dialog reuse pattern -> startDialog(ID)
        * je hitrejše, a na modernih procesorjih se ne opazi več  
        * porabiš več RAM-a za vzdrževanje podatkov  

APPLICATION PREFERENCES:
- se preberejo iz .xml datoteke in se ob onCreate() shranijo v podatkovno bazo
- addPreferencesFromResource()
- types of preferences:
        * ListPreferences       = mutually exclusive  
        * CheckBoxPreferences   = boolean  
        * EditTextPreferences   = string   
        * IntentPreferences     = launch an activity   
        * RingTonePreferences   = choose a ringtine  
- preferences (settings) se avtomatsko shranijo v datoteko in se ob začetku aplikacije preberejo

STATE PRESERVATION:
- ko se landscape orientation spremeni Android uniči current activity object in ustvari novega
- ko se to zgodi se vsi podatki iz activity-ja spremenijo (na default)
- zato moramo biti pazljivi na te dogodke
1. onSaveInstanceState(Bundle) overridamo in v metodo damo state.putInt(...) ter na koncu onSaveInstanceState(state)
2. kliče se nov activity: onCreate()
3. onRestoreInstanceState(Bundle) overridamo in v metodo damo state.getInt(...) ter na koncu onRestoreInstanceState(state)

FEATURES AND PERMISSIONS:
FEAUTURES:
- declared in the manifest file
- when an app needs certain hardware characteristic that can be unavailable
- class PackageManager

PERMISSIONS:
- declared in the manifest file and asked during installation
- to use certain API functionalities that need permission from the user
- class Manifest.permission
- after marshmallow (6.0) 'dangerous' permissions should be obtained at runtime
