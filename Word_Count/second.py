import string
import sys
import threading

from nltk.corpus import stopwords

hashmap = {}
mutex = threading.Lock()


def read_file(filepath: str) -> list:
    with open(filepath, 'r', encoding='utf-8') as fd:
        return fd.readlines()


def word_cleaner(word: str) -> str:
    return ''.join(list(filter(lambda ch: ch not in string.punctuation, word)))


def extract_words(filepath: str, cutset: str):
    global hashmap
    global mutex

    for line in read_file(filepath):
        for word in line.strip().split():
            clean_word = word_cleaner(word.strip()).lower()

            if clean_word not in cutset:
                if mutex.acquire():
                    if hashmap.get(clean_word, None):
                        hashmap[clean_word] += 1
                    else:
                        hashmap[clean_word] = 1

                    mutex.release()


if __name__ == '__main__':
    if len(sys.argv) < 2:
        print(f'usage {sys.argv[0]} <files ...>', file=sys.stderr)
        sys.exit(1)

    cutset = stopwords.words('portuguese')
    threads = []

    for file in sys.argv[1:]:
        threads.append(threading.Thread(
            target=extract_words, args=(file, cutset)))

    for thread in threads:
        thread.start()

    for thread in threads:
        thread.join()

    print('Frequency:', hashmap)
