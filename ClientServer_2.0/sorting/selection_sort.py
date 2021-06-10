# Python program for implementation of Selection

# array = [10, 8, 55, 24, 45, 12, 4, 50]


def selection_sort(array: list) -> list:
    for i in range(len(array)):

        # Find the minimum element in remaining
        # unsorted array
        min_idx = i
        for j in range(i + 1, len(array)):
            if array[min_idx] > array[j]:
                min_idx = j

        # Swap the found minimum element with the first element
        array[i], array[min_idx] = array[min_idx], array[i]

    return array
