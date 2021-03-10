cmake_minimum_required(VERSION 3.4.1)

# 查找目录下的所有源文件
# 并将名称保存到 DIR_SRCS 变量
aux_source_directory(. DIR_SRCS)

#找到所有的NDK的库
find_library(log-lib log)
find_library(opensles OpenSLES)

include_directories(./include)

link_directories(../../.././libs/${ANDROID_ABI}/)


add_library( # Sets the name of the library.
        williamopensles

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${DIR_SRCS})


target_link_libraries( # Specifies the target library.
        williamopensles

        ${log-lib}
        ${opensles}
        )