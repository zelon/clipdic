# coding: utf-8

import sys
import urllib

def testmain(word):

		html = urllib.urlopen("http://endic.naver.com/small_search.nhn?query=" + word + "&x=0&y=0")

		enc = unicode(html.read(), "cp949", "ignore").encode("utf-8")

		print enc
		start = enc.find("<!-- 검색결과 -->");
		end = enc.find("<!--//검색결과-->");

		if start == -1 or end == -1:

			start = enc.find("<!-- 뜻풀이-->")
			end = enc.find("<!-- //뜻풀이-->")

			if start == -1 or end == -1:
				print("cannot find start or end position")
				return "There is no result or cannot find start or end position."

		print(enc[start:end])
		return enc[start:end]

if __name__ == "__main__":
	testmain()
