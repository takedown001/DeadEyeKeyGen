#include <jni.h>
#include <string>
#include <Includes/Utils.h>
#include "Includes/obfuscate.h"
#include "Includes/Logger.h"
#include "Security/enc.h"



extern "C"
JNIEXPORT jstring JNICALL
Java_com_gcc_deadeyekeygen_AdsActivity_00024GetKey_Here(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBS("https://gcc-org.com/DeadEyE/keyGen.php"));
}extern "C"
JNIEXPORT jstring JNICALL
Java_com_gcc_deadeyekeygen_AESUtils_00024DarKnight_AESKey(JNIEnv *env, jclass thiz) {
    return env->NewStringUTF(OBS("tAeKpEcDe6410111"));
}
extern "C"
JNIEXPORT int JNICALL
Java_com_gcc_deadeyekeygen_AdsActivity_getduration(JNIEnv *env, jclass thiz) {
    jint i = 31000;
    return i;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_memory_xploiter_ESPView_DeadEye(JNIEnv *env, jobject thiz) {
    return env->NewStringUTF(OBS("DeadEye"));
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_gcc_deadeyekeygen_HomeActivity_tg(JNIEnv *env, jclass thiz) {
    return env->NewStringUTF(OBS("https://t.me/DeadEye_TG"));
}
