// Description in Armenian: https://web.archive.org/http://olymp.am/sites/default/file/problems/Inform-Ar-Marz18-2.pdf

#include <string>
#include <iostream>
#include <vector>

typedef std::vector<std::string> StringVector;

StringVector rotateRight(const StringVector &in, int count) {
	if(count <= 0) {
		return in;
	}

	StringVector ret(in.size(), "");

	for(int j = 0; j < in.size(); ++j) {
		for (int i = in.size() - 1; i >= 0; --i) {
			ret[j] += in[i][j];
		}
	}

	if(count > 1) {
		return rotateRight(ret, count - 1);
	} else {
		return ret;
	}
}

inline std::string decypher(const std::string &encrypted, const StringVector &key) {
	StringVector decyphered(key.size(), std::string(key.size(), ' '));

	auto encryptedIter = encrypted.crbegin();

	for(int step = 3; step >= 0; --step) {
		const StringVector rotatedKey = rotateRight(key, step);
		for(int i = rotatedKey.size() - 1; i >= 0; --i) {
			for(int j = rotatedKey.size() - 1; j >= 0; --j) {
				if(rotatedKey[i][j] == '.') {
					decyphered[i][j] = *(encryptedIter++);
				}
			}
		}
	}

	std::string ret;
	ret.reserve(decyphered.size() * decyphered.size());

	for(auto str : decyphered) {
		ret += str;
	}
	return ret;
}

// NOTE: No safety checks.
int main() {
	/*
	 * Example input:
	 * 4
	 * X.XX
	 * .XXX
	 * X.XX
	 * XXX.
	 * UASGNZRMRAONNICI
	 *
	 * Output:
	 * RUNNAZARISCOMING
	 */

	int n = 0;
	std::cin >> n;

	StringVector key;
	key.reserve(n);
	for(int i = 0; i < n; ++i) {
		std::string tmp;
		tmp.reserve(n);
		std::cin >> tmp;
		key.push_back(tmp);
	}

	std::string encrypted;
	encrypted.reserve(n * n);

	std::cin >> encrypted;

	std::cout << decypher(encrypted, key) << std::endl;

	return 0;
}