# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
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

########################################
#
# 一些基本指令
#
########################################
#指定代码混淆压缩级别  0-7 之间
-optimizationpasses 5

#混合时不适应大小写混合,混合后类名为小写
-dontusemixedcaseclassnames

#指定不忽略非公共库的类
-dontskipnonpubliclibraryclasses

#指定不忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

#使项目混淆后产生映射文件,包含类名 -> 混淆后类名的映射关系
-verbose

#不需要preverify,加快混淆速度
-dontpreverify

#保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

#避免混淆泛型
-keepattributes Signature

#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

########################################
#
# Android 一定要保留的公共部分
#
########################################

#四大组件相关,Application类 不能混淆, 因为可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
#-keep public class com.android.vending.licensing.ILicensingService

#保留R下面的资源
-keep class **.R$* {*;}

#保留本地native方法不被混淆
-keepclasseswithmembernames class *{native<methods>;}

#保留Activity中方法参数是view的方法
#这样在layout中写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
  public void * (android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保留自定义控件不被混淆
-keep public class * extends android.view.View{
  *** get*();
  void set*(***);
  public <init>(android.content.Context);
  public <init>(android.content.Context,android.util.AttributeSet);
  public <init>(android.content.Context,android.util.AttributeSet,int);
}

#保留Parcelable序列化类不被混淆
 -keep class * implements android.os.Parcelable{
 public static final android.os.Parcelable$Creator *;}

 # 保留Application
 -keep class android.app.**{*;}

  #coroutines
 -keep class kotlinx.coroutines.**{*;}

########################################
#
# 第三方混淆规则
#
########################################

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# for DexGuard only
#-keep resourcexmlelements manifest/application/meta-data@value=GlideModule

#BottomNavigationViewEx
-keep public class com.google.android.material.bottomnavigation.BottomNavigationView { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationMenuView { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationPresenter { *; }
-keep public class com.google.android.material.bottomnavigation.BottomNavigationItemView { *; }
#ToastUtils
-keep class com.hjq.toast.** {*;}

#Retrofit
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

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

# data 包不要混淆
-keep class com.shijing.weather.** {*;}
-keep class com.shijing.weather.viewModel.** {*;}

# 友盟
-keep class com.umeng.** {*;}

-keep class com.uc.** {*;}

-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class com.zui.** {*;}
-keep class com.miui.** {*;}
-keep class com.heytap.** {*;}
-keep class a.** {*;}
-keep class com.vivo.** {*;}

-keep public class com.hyt.cupcake.R$*{
public static final int *;
}

#EventBus
-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# And if you use AsyncExecutor:
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

#高德
-keep class com.mapbox.mapboxsdk.**{ *; }