# Python3 Optimized implementation of Bubble sort

# An optimized version of Bubble Sort


def bubble_sort(array: list) -> list:

    # Traverse through all array elements
    for i in range(len(array)):
        swapped = False

        # Last i elements are already
        # in place
        for j in range(0, len(array) - i - 1):

            # traverse the array from 0 to
            # len.array-i-1. Swap if the element
            # found is greater than the
            # next element
            if array[j] > array[j + 1]:
                array[j], array[j + 1] = array[j + 1], array[j]
                swapped = True

        # IF no two elements were swapped
        # by inner loop, then break
        if not swapped:
            break
    return array
