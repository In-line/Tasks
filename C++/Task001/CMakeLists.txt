cmake_minimum_required(VERSION 3.9)
project(Task001)

set(CMAKE_CXX_STANDARD 11)
add_subdirectory(tests)

add_executable(Task001 src/main.cpp src/Containers/SimpleList.h src/Containers/ListIteratorTemplated.h src/Student.cpp src/Student.h src/Mark.cpp src/Mark.h src/ClassRoom.cpp src/ClassRoom.h)