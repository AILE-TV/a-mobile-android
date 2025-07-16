# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For navigation_more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.ailetv.mobile.data.model.** { *; }

#-dontwarn az.pulpal.**
-keepattributes Exceptions, Signature, InnerClasses

-dontoptimize
-dontobfuscate
-dontwarn com.google.**
-dontnote


-keep public class com.google.android.gms.analytics.**, com.google.android.gms.common.**, com.google.android.gms.location.** {
    public protected *;
}


# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>


-dontwarn com.squareup.okhttp.**
-dontwarn org.jetbrains.annotations.**

-keep class com.facebook.** {
   *;
}

-keepclassmembers class * implements java.io.Serializable {
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE

-keep class androidx.navigation.** { *; }
-keepclassmembers class * {
    @androidx.navigation.Navigator$Name *;
}
-keep @androidx.navigation.Navigator$Name class * {*;}

# --- REFLECTION SUPPORT ---

# Keep all members (fields/methods) for all classes (reflection-safe)
-keepclassmembers class * {
    *;
}

# Keep all annotations (prevents R8 from stripping annotated classes)
-keep @interface *
-keep @* class *

# Keep all public classes and their public members
-keep public class * {
    public *;
}

# Keep classes with @Keep annotation
-keepclassmembers class * {
   @androidx.annotation.Keep *;
}

# Keep Kotlin metadata (used by reflection in Kotlin)
-keepclassmembers class kotlin.Metadata { *; }

# Disable obfuscation (optional – for debugging; remove this line if you want shrinking)
-dontobfuscate

# --- ANDROIDX NAVIGATION SUPPORT ---
# Keep all navigation-related classes and annotations
-keep class androidx.navigation.** { *; }
-keepclassmembers class * {
    @androidx.navigation.Navigator$Name *;
}
-keep @androidx.navigation.Navigator$Name class * {*;}


# Gson reflection support
-keep class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keep class com.yourpackage.model.** { *; }

# Hilt (Dependency Injection)
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.EntryPoint

# Android DataBinding
-keep class **BR { *; }
-keep class * extends androidx.databinding.ViewDataBinding { *; }

