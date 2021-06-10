# Python program for implementation of Insertion Sort

def insertion_sort(array: list) -> list:
    for i in range(1, len(array)):
        key = array[i]

        # Move elements of array[0..i-1], that are
        # greater than key, to one position ahead
        # of their current position
        j = i-1
        while j >= 0 and key < array[j]:
            array[j + 1] = array[j]
            j -= 1
        array[j + 1] = key
    return array
