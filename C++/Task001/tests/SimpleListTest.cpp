//
// Created by alik on 12/7/17.
//
#include "gtest/gtest.h"
#include "../src/Containers/SimpleList.h"

using namespace Containers;
#define SIMPLE_LIST_SIZE  100

struct SimpleListTest : public ::testing::Test {

    SimpleList<int> simpleList;

    SimpleListTest() {
        for (std::size_t i = 0; i < SIMPLE_LIST_SIZE; ++i) {
            simpleList.push_back(i);
        }
    }
};

TEST_F(SimpleListTest, SizeTest) {
    EXPECT_EQ(SIMPLE_LIST_SIZE, simpleList.size());
}

TEST_F(SimpleListTest, PopBackTest) {
    for (std::size_t i = 0; i < SIMPLE_LIST_SIZE; ++i) {
        ASSERT_EQ(SIMPLE_LIST_SIZE - i - 1, simpleList.back());
        // TODO: end-- iterator test;

        ASSERT_EQ(SIMPLE_LIST_SIZE - i, simpleList.size());
        simpleList.pop_back();
    }
}

TEST_F(SimpleListTest, PushBackTest) {
    SimpleList<int> test;

    for (std::size_t i = 0; i < SIMPLE_LIST_SIZE; ++i) {
        test.push_back(i);
        ASSERT_EQ(test.back(), i);
    }

    ASSERT_EQ(test, simpleList);
}

TEST_F(SimpleListTest, PopFrontTest) {
    for (std::size_t i = 0; i < SIMPLE_LIST_SIZE; ++i) {
        ASSERT_EQ(i, simpleList.front());
        ASSERT_EQ(i, *simpleList.begin());

        ASSERT_EQ(SIMPLE_LIST_SIZE - i, simpleList.size());
        simpleList.pop_front();
    }
}

TEST_F(SimpleListTest, PushFrontTest) {
    SimpleList<int> test;

    for (std::size_t i = SIMPLE_LIST_SIZE - 1; i != 0; --i) {
        test.push_front(i);
        ASSERT_EQ(*test.begin(), i);
        ASSERT_EQ(test.front(), i);
    }

    test.push_front(0);

    ASSERT_EQ(*test.begin(), 0);
    ASSERT_EQ(test.front(), 0);

    ASSERT_EQ(test, simpleList);
}

TEST_F(SimpleListTest, EqualOperatorTest) {
    SimpleList<int> test;

    for (std::size_t i = 0; i < SIMPLE_LIST_SIZE; ++i) {
        test.push_back(i);
    }

    ASSERT_EQ(test, simpleList);
}
