import socket

from sorting.bubble_sort import bubble_sort
from sorting.insertion_sort import insertion_sort
from sorting.merge_sort import merge_sort
from sorting.selection_sort import selection_sort
from data.data import Request, Response

HOST = '0.0.0.0'
PORT = 8080
BUFFER = 4096

methods = {
    'bubble': bubble_sort,
    'inserction': insertion_sort,
    'merge': merge_sort,
    'selection': selection_sort
}


def server():
    with socket.socket() as listener:
        print('Waiting Connection... ')
        listener.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
        listener.bind((HOST, PORT))
        listener.listen(1)

        conn, addr = listener.accept()
        print('Connected by', addr)

        while True:
            rawBytes = conn.recv(BUFFER)

            if not rawBytes:
                break

            request = Request()
            parsedRequest = request.deserializar(rawBytes)

            method = parsedRequest.get('method', None)
            sortFn = methods.get(method, None)

            if sortFn:
                sortedList = sortFn(parsedRequest.get('payload', []))
                response = Response(sortedList)
                conn.send(response.serializar())

    print('Connection finalized with client:', addr)


if __name__ == "__main__":
    server()
