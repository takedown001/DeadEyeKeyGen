#include <jni.h>
#include <string>
#include <Includes/Utils.h>
#include "Includes/obfuscate.h"
#include "Includes/Logger.h"
#include "Security/enc.h"


extern "C"
JNIEXPORT jstring JNICALL
Java_com_game_sploitkeygen_HomeActivity_tg(JNIEnv *env, jclass thiz) {
    return env->NewStringUTF(OBS("https://t.me/DeadEye_TG"));

}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_game_sploitkeygen_AdsActivity_KeyGen(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBS("https://gamesploit.com/project/KeyGen/freekey.php"));
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_game_sploitkeygen_AdsActivity_getduration(JNIEnv *env, jclass clazz) {
    jint i = 5000;
    return i;
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_game_sploitkeygen_HomeActivity_Donate(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF(OBS("https://shop.gamesploit.com/product/bgmi-internal-esp/"));
}