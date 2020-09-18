# código reutilizado de uma implementação do google exercicies

##################################################################
# read archive;-----------------------------------------------OK #
# count words: all -------------------------------------------OK #
# process the counted words with strip -----------------------OK #
# Read about map equivalency
# process the counted words with stopwords -------------------OK #
# parallelism: Multiprocessing--------------------------------   #
# add a converter to txt????
# add a timer??
##################################################################

import string
import sys
import time
from multiprocessing import Pool
from threading import Lock

import nltk

mutex = Lock()
nltk.download('stopwords')
stopwords = nltk.corpus.stopwords.words('portuguese')

filenames = ['test2.txt', 'test.txt']


#contagem das palavras e tratamento
def word_counted_dict(filename):
    word_counted = {}
    input_file = open(filename, 'r')
    for line in input_file:
        words = line.split()
        for word in words:
            if word not in stopwords:
                word = word.lower()
                word = word.strip(string.punctuation)
                if word not in word_counted:
                    word_counted[word] = 1
                else:
                    word_counted[word] = word_counted[word] + 1

    input_file.close()
    return word_counted


#imprime alfabeticamente ordenado
def print_words(filename):
    word_counted = word_counted_dict(filename)

    words = sorted(word_counted.keys())

    for word in words:
        mutex.acquire()
        print(word, word_counted[word])
        mutex.release()


#Imprime ordenado por quantidade
def print_top(filename):
    word_counted = word_counted_dict(filename)

    items = sorted(word_counted.items(), key=get_count, reverse=True)

    for item in items[:20]:  # Max of 20
        print(item[0], item[1])


def get_count(word_counted_tuple):
    return word_counted_tuple[1]


def main():
    start = time.time()
    if len(sys.argv) != 3:
        print('usage: ./main.py {--count | --topcount} file')
        sys.exit(1)

    option = sys.argv[1]
    filename = sys.argv[2]

    if option == '--count':
        print_words(filename)
    elif option == '--topcount':
        print_top(filename)
    else:
        print('unknown option: ' + option)
        sys.exit(1)

    end = time.time()
    print('\nTempo de execução: ', end-start)


def pool_func():
    p = Pool(2)
    p.map(print_words, filenames)


if __name__ == '__main__':
    pool_func()
