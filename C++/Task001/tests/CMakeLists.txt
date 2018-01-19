project(Tests)
include(CTest)
find_package(GTest REQUIRED)

enable_testing()
include_directories(${PROJECT_NAME} ${gtest_SOURCE_DIR}/include ${gtest_SOURCE_DIR})


file(GLOB_RECURSE TESTS ${CMAKE_CURRENT_SOURCE_DIR}/tests/*.h ${CMAKE_CURRENT_SOURCE_DIR}/tests/*.cpp)


add_executable(${PROJECT_NAME} ${TESTS} SimpleListTest.cpp main.cpp)
target_link_libraries(${PROJECT_NAME} gtest gtest_main)
add_test(test1 ${PROJECT_NAME})