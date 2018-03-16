// Nearest number without repetitions
#include <climits>
#include <cstdint>
#include <iostream>
#include <limits>
#include <algorithm>

inline bool checkForRepetitions(int64_t in) {
	int rep[10]; // Should be enough xD
	int size = 0;

	while(in != 0) {
		int n = in % 10;
		if(n == 0) {
			return false;
		}

		auto end = reinterpret_cast<int *>(&rep + size);

		if(std::find(rep, end, n) != end) {
			return false;
		}

		rep[size++] = n;
		in /= 10;
	}

	return true;
}

int main() {
	int64_t in = 0;
	std::cin >> in;

	++in;

	while(!checkForRepetitions(in)) {
		if(in == std::numeric_limits<int64_t>::max()) {
			std::cout << "Can't find number" << std::endl;
			exit(EXIT_FAILURE);
		}
		++in;
	}

	std::cout << in << std::endl;
	return EXIT_SUCCESS;
}