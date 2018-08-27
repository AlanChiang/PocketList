ifneq ($(BOARD_USE_DEFAULT_HDMISWITCH),false)
LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

LOCAL_STATIC_JAVA_LIBRARIES := slide_fragment
LOCAL_SRC_FILES := $(call all-subdir-java-files)

LOCAL_PACKAGE_NAME := Tab_test
LOCAL_CERTIFICATE := platform

include $(BUILD_PACKAGE)
include $(call all-makefiles-under,$(LOCAL_PATH))
endif


include $(CLEAR_VARS)

LOCAL_MODULE_TAGS := optional

#add lib
LOCAL_PREBUILT_STATIC_JAVA_LIBRARIES := slide_fragment:libs/android-support-v4.jar
include $(BUILD_MULTI_PREBUILT)


include $(call all-makefiles-under,$(LOCAL_PATH))
