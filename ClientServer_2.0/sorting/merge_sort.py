# Python program for implementation of MergeSort

def merge_sort(array: list) -> list:
    if len(array) > 1:
        mid = len(array) // 2  # Finding the mid of the array
        left = array[:mid]  # Dividing the array elements
        right = array[mid:]  # into 2 halves

        merge_sort(left)  # Sorting the first half
        merge_sort(right)  # Sorting the second half

        i = j = k = 0

        # Copy data to temp arrays left[] and right[]
        while i < len(left) and j < len(right):
            if left[i] < right[j]:
                array[k] = left[i]
                i += 1
            else:
                array[k] = right[j]
                j += 1
            k += 1

        # Checking if any element was left
        while i < len(left):
            array[k] = left[i]
            i += 1
            k += 1

        while j < len(right):
            array[k] = right[j]
            j += 1
            k += 1

    return array


def print_merge(array):
    merge_sort(array)
    for i in range(len(array)):
        print(array[i], end=" ")
