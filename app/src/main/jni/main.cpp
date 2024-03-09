#include <sys/types.h>
#include <pthread.h>
#include <jni.h>
#include <string>
#include "ESP.h"
#include "Hacks.h"


extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_MainAct(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("com.xero.ui.MainActivity");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_titleLie(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("小宇插件");//名字
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_titleShooter(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("小宇");//名字
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_MainActivity_titleLie(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("q群：");//名字
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_MainActivity_titleShooter(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("792649610");//名字
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_getkey(JNIEnv *env, jobject thiz ) {
    return env->NewStringUTF("https://t.me/xyGG886AN");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_Dev(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("小宇插件", '~'));
    return str;//名字
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_SplashActivity_Dev(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("小宇插件", '~'));
    return str;//名字
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_loginURL(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("link--php", '~'));//Link PHP Files
    return str;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_MainActivity_Dev(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("小宇插件", '~'));
    return str;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_FloatingActivity_txtESP(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("小宇插件", '~'));
    return str;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_FloatingActivity_txtINJ(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("小宇插件", '~'));
    return str;
}

ESP espOverlay;
int type=1,utype=2;

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_Overlay_DrawOn(JNIEnv *env, jclass , jobject espView, jobject canvas) {
    espOverlay = ESP(env, espView, canvas);
    if (espOverlay.isValid()){
        DrawESP(espOverlay, espOverlay.getWidth(), espOverlay.getHeight());
    }
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_MainActivity_Tele(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("https://t.me/xyGG886AN", '-'));//Link Telegram Channel
    return str;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_xero_ui_LoginActivity_Tele(JNIEnv *env, jobject activityObject) {
    jstring str = env->NewStringUTF(OBFUSCATE_KEY("https://t.me/xyGG886AN", '-'));//Link Telegram Channel
    return str;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_xero_ui_MainActivity_EXP(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(EXP.c_str());
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_Overlay_Close(JNIEnv *,  jobject ) {
    Close();
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_SettingValue(JNIEnv *,  jobject ,jint code,jboolean jboolean1) {
    switch((int)code){
        
        case 2:
            isTopLine = jboolean1;  break;
        case 3:
            isPlayerDist = jboolean1;  break;
        case 4:
            isPlayerHealth = jboolean1;  break;
        case 5:
            isPlayerName = jboolean1;  break;
        case 6:
            isPlayerHead = jboolean1;  break;
        case 7:
            isr360Alert = jboolean1;  break;
          
        case 9:
            isEnemyWeapon=jboolean1;  break;
        case 10:
            isGrenadeWarning=jboolean1;
            break;
        case 11:
            isFPS=jboolean1;
            break;
        case 12:
            isCount2=jboolean1;
            break;
        case 13:
            isCount1=jboolean1;
            break;
        case 14:
            isCount3=jboolean1;
            break;
        case 15:
            isMiddleLine=jboolean1;
            break;
        case 16:
            isBottomLine=jboolean1;
            break;
        case 17:
            isTopBottomLine=jboolean1;
            break;
        case 18:
            isVisibility=jboolean1;
            break;
        case 19:
            isHealth1=jboolean1;
            break;
        case 20:
            isHealth2=jboolean1;
            break;
            
            //ColorLine
        case 21:
            isLineRed=jboolean1;
            break;
        case 22:
            isLineYellow=jboolean1;
            break;
        case 23:
            isLineGreen=jboolean1;
            break;
        case 24:
            isLineBlue=jboolean1;
            break;
            
            //ColorBox
        case 25:
            isBoxRed=jboolean1;
            break;
        case 26:
            isBoxYellow=jboolean1;
            break;
        case 27:
            isBoxGreen=jboolean1;
            break;
        case 28:
            isBoxBlue=jboolean1;
            break;    
            
            //SkeletonColor
        case 29:
            isSkeletonWhite=jboolean1;
            break;
        case 30:
            isSkeletonRed=jboolean1;
            break;
        case 31:
            isSkeletonYellow=jboolean1;
            break;
        case 32:
            isSkeletonGreen=jboolean1;
            break;    
            //BotStyle
        case 33:
            isBotWhite=jboolean1;
            break;
        case 34:
            isBotLBlue=jboolean1;
            break;    
 
            //Memory
        case 35:
            memory.LessRecoil=jboolean1;
            break;
        case 36:
            isTimID = jboolean1;  break;
            break;
		case 37:
            isPlayerBoxStroke = jboolean1;   break;
		case 38:
            isPlayerBoxFilled = jboolean1;   break;
		case 39:
            isSkeltonPlayer = jboolean1;  break;     
		case 40:
            isSkeltonPlayerBot = jboolean1;  break;     
            
        case 43:
            if(jboolean1 != 0)
                options.openState=0;
            else
                options.openState=-1;
            break;
        case 44:
            options.pour=jboolean1;
            break;
        case 45:
            options.tracingStatus=jboolean1;
            break;
        case 46:
            options.iBot=jboolean1;
            break;
        case 47:
            memory.WideView = jboolean1;
            break;
        case 48:
            memory.SmallCrosshair = jboolean1;
            break;
        case 49:
            memory.Aimlock = jboolean1;
            break;
			
}
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatAimbot_AimbotPOV(JNIEnv *,  jobject ,jint code,jboolean jboolean1) {
    switch((int)code){
        case 43:
            if(jboolean1 != 0)
                options.openState=0;
            else
                options.openState=-1;
            break;
    }
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_Range(JNIEnv *,  jobject ,jint range) {
    options.aimingRange=1+range;
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_distances(JNIEnv *,  jobject ,jint distances) {
    options.aimingDistance=distances;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_recoil(JNIEnv *env, jobject thiz, jint recoil) {
    options.Recoil = recoil;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_Bulletspeed(JNIEnv *env, jobject thiz, jint bulletspeed) {
    options.bulletSpeed = bulletspeed;
}

extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_Target(JNIEnv *,  jobject ,jint target) {
    options.aimbotmode=target;
}
extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_AimWhen(JNIEnv *,  jobject ,jint state) {
    options.aimingState=state;
}
extern "C" JNIEXPORT void JNICALL
Java_com_xero_ui_FloatingActivity_AimBy(JNIEnv *,  jobject ,jint aimby) {
    options.priority=aimby;
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_xero_ui_Overlay_getReady(JNIEnv *, jclass ,int typeofgame) {
    int sockCheck=1;

    if (!Create()) {
        perror("Creation failed");
        return false;
    }
    setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&sockCheck, sizeof(int));
    if (!Bind()) {
        perror("Bind failed");
        return false;
    }

    if (!Listen()) {
        perror("Listen failed");
        return false;
    }
    if (Accept()) {
        SetValue sv{};
        sv.mode=typeofgame;
        sv.type=utype;
        send((void*)&sv,sizeof(sv));
        // Close();
        return true;
    }

}

int Register1(JNIEnv *env) {
    JNINativeMethod methods[] = {{"Check", "(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;", (void *) native_Check}};

    jclass clazz = env->FindClass("com/xero/ui/LoginActivity");
    if (!clazz)
        return -1;

    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) != 0)
        return -1;

    return 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    if (Register1(env) != 0)
        return -1;
    return JNI_VERSION_1_6;
}
