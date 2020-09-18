# -*- coding: utf-8 -*-
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
from multiprocessing import Pool
import nltk

nltk.download('stopwords')
stopwords = nltk.corpus.stopwords.words('portuguese')

filenames = ['test.txt', 'test2.txt']


def read_file(filename: str) -> str:
    with open(filename, 'r', encoding='utf-8') as fd:
        return fd.readlines()


# contagem das palavras e tratamento
def word_counted_dict(filename: str) -> dict:
    word_counted = {}
    input_file = read_file(filename)

    for line in input_file:
        for word in line.split():
            if word not in stopwords:
                word = word.lower().strip(string.punctuation)

                if word not in word_counted:
                    word_counted[word] = 1
                else:
                    word_counted[word] = word_counted[word] + 1

    return word_counted


# imprime alfabeticamente ordenado
def print_words(filename: str):
    word_counted = word_counted_dict(filename)

    for word in sorted(word_counted.keys()):
        print(word, word_counted[word])


# Imprime ordenado por quantidade
def print_top(filename: str):
    word_counted = word_counted_dict(filename)

    # Max of 20
    for item in sorted(word_counted.items(), key=get_count, reverse=True)[:20]:
        print('Top', item, item[0], item[1])


def get_count(word_counted_tuple: tuple):
    return word_counted_tuple[1]


def main():
    if len(sys.argv) != 3:
        print(
            f'usage: {sys.argv[0]} < --count | --topcount> <file>', file=sys.stderr)
        sys.exit(1)

    option = sys.argv[1]
    filename = sys.argv[2]

    options = {
        '--count': print_words,
        '--topcount': print_top
    }

    execFunction = options.get(option, None)

    if execFunction:
        execFunction(filename)
        return

    print('unknown option:', option)
    sys.exit(1)


def pool_func():
    p = Pool(2)
    p.map(print_words, filenames)


if __name__ == '__main__':
    print('test')
    pool_func()
