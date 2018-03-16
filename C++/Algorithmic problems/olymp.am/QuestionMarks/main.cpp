// Description in Armenian: https://web.archive.org/web/20180316161811/http://olymp.am/sites/default/file/problems/Inform-Ar-Marz18-3.pdf

#include <iostream>
#include <functional>
#include <sstream>

std::string bruteforce(std::string in, int test, const bool &shouldTerminate, const std::function<void(int)> &callback) {
	for(int i = 0; i < in.size() && !shouldTerminate; ++i) {
		if(in[i] == '?') {
			for(int j = 0; j <= 9 && !shouldTerminate; ++j) {

				int temp = in[i];
				in[i] = '0' + j;
				std::string val = bruteforce(in, test, shouldTerminate, callback);
				in[i] = temp;


				if(!val.empty()) {
					int valInt = std::stoi(val);
					if(valInt % test == 0) {
						callback(valInt);
					}
				}

			}

			return "";
		}
	}

	return in;
}

int main() {
	/*
	 * Example input:
	 * 2
	 * 2 14
	 * 4?
	 * 2 17
	 * 4?
	 *
	 * Output:
	 * 42
	 * -1
	 */
	int T = 0;
	std::cin >> T;

	std::stringstream output;

	while(T > 0) {
		int N = 0;
		std::cin >> N;

		int K = 0;
		std::cin >> K;

		std::string in;
		in.reserve(N);

		std::cin >> in;

		// C++ crutch style programming :D
		bool shouldTerminate = false;
		bruteforce(in, K, shouldTerminate,[&](const int result) -> bool {
			output << result << std::endl;
			shouldTerminate = true;
		});

		if(!shouldTerminate) {
			output << "-1" << std::endl;
		}

		--T;
	}

	std::cout << output.rdbuf();
	return 0;
}