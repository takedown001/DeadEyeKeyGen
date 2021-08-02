LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE    := tersafe2
LOCAL_ARM_MODE := arm

LOCAL_SRC_FILES := main.cpp \

LOCAL_C_INCLUDES += $(LOCAL_PATH)
LOCAL_LDLIBS := -llog -landroid

include $(BUILD_SHARED_LIBRARY)

