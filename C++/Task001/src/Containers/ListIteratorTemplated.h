//
// Created by alik on 12/7/17.
//

#ifndef TASK001_LISTITERATOR_H
#define TASK001_LISTITERATOR_H

#include <memory>

namespace Containers {
    template<typename Node, typename T>
    class ListIteratorTemplated : public std::iterator<
            std::bidirectional_iterator_tag,
            std::shared_ptr<T>,
            std::ptrdiff_t,
            const std::shared_ptr<T> *,
            std::shared_ptr<T>
    > {
        std::shared_ptr<Node> currentNode_;
		std::shared_ptr<Node> getNode() {
			return currentNode_;
		}
		template<typename T2> friend class SimpleList;
    public:
        explicit ListIteratorTemplated(std::shared_ptr<Node> currentNode) : currentNode_(currentNode) {}

        ListIteratorTemplated &operator++() {
            if (currentNode_->next) {
                currentNode_ = currentNode_->next;
            } else {
                currentNode_ = {};
            }
            return *this;
        }

        ListIteratorTemplated operator++(int) {
            auto prev = *this;
            ++(*this);
            return prev;
        }

        // TODO: end()--
        ListIteratorTemplated &operator--() {
            if (auto prev = currentNode_->prev.lock()) {
                currentNode_ = prev;
            } else {
                throw std::runtime_error("No prev element!");
            }
            return *this;
        }

        ListIteratorTemplated operator--(int) {
            auto prev = *this;
            --(*this);
            return prev;
        }

        bool operator==(ListIteratorTemplated other) const { return currentNode_ == other.currentNode_; }

        bool operator!=(ListIteratorTemplated other) const { return !(other == *this); }

        T &operator*() const { return *currentNode_->data.get(); }
    };
}
#endif //TASK001_LISTITERATOR_H
