//
// Created by alik on 12/7/17.
//

#ifndef TASK001_SIMPLEQUEUE_H
#define TASK001_SIMPLEQUEUE_H

#include <memory>
#include "ListIteratorTemplated.h"

namespace Containers {

    class EmptyListException : public std::runtime_error {
    public:
        EmptyListException() : runtime_error("List is empty") {}
    };

    template<typename T>
    class SimpleList {
    private:
        struct Node {
            std::shared_ptr<T> data;
            std::shared_ptr<Node> next;
            std::weak_ptr<Node> prev;
        };

        std::shared_ptr<Node> root_ = std::make_shared<Node>();
        std::shared_ptr<Node> tail_ = root_;
        std::size_t size_ = 0;

        inline void checkEmpty() const {
            if (!root_->next || size_ == 0)
                throw EmptyListException();
        }

    public:

        void push_back(const T &data) {
            auto last = tail_;
            last->next = std::make_shared<Node>();

            last->next->data = std::make_shared<T>(data);
            last->next->prev = last;
            tail_ = last->next;
            ++size_;
        }

        void pop_back() {
            auto last = tail_;
            if (auto prev = tail_->prev.lock()) {
                tail_ = prev;
                tail_->next = {};
                --size_;
            } else {
                throw EmptyListException();
            }
        }

        void push_front(const T &data) {
            auto curNodeCopy = root_->next;

            auto &curNode = root_->next;
            curNode = std::make_shared<Node>();
            curNode->data = std::make_shared<T>(data);
            curNode->prev = root_;
            curNode->next = curNodeCopy;
            ++size_;
        }

        void pop_front() {
            checkEmpty();

            auto &curNode = root_->next;

            if (curNode->next) {
                curNode = curNode->next;
            } else {
                if (curNode == tail_) {
                    tail_ = root_;
                }
                curNode = {};
            }
            --size_;
        }

        bool remove(const T &data) {
            for(auto it = this->begin(); it != this->end(); ++it) {
                if (*it == data) {
                    auto node = it.getNode();
                    if (auto prev = node->prev.lock()) {
                        prev->next = node->next;
                        if (prev->next) {
                            prev->next->prev = prev;
                        }
                        --size_;
                    }
                    else {
                        throw std::runtime_error("Unreachable code!");
                    }
                    return true;
                }
            }
            return false;
        }

        T &back() const {
            checkEmpty();
            return *tail_->data.get();
        }

        T &front() const {
            checkEmpty();
            return *root_->next->data.get();
        }

        typedef ListIteratorTemplated<Node, T> iterator;

        iterator begin() const {
            return iterator(root_->next);
        }

        iterator end() const {
            return iterator({});
        }

        std::size_t size() const {
            return size_;
        }

        bool operator==(const SimpleList<T> &other) const {
            if (other.size() != this->size())
                return false;

            for (auto iterator = this->begin(), other_iterator = other.begin();
                 iterator != this->end() && other_iterator != other.end(); ++iterator, ++other_iterator
                    ) {
                if (*iterator != *other_iterator) {
                    return false;
                }
            }

            return true;
        }
    };
}

#endif //TASK001_SIMPLELIST_H
