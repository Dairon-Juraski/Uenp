import socket
import sys

from data.data import Request, Response

HOST = '127.0.0.1'
PORT = 8080
BUFFER = 4096


def client():
    with socket.socket() as dial:
        metodo = input(
            'Digite o nome do método (bubble, merge, inserction, selection): ').lower()
        payload = list(
            map(int, input('Digite os números que irão compor o vetor: ').split()))

        dial.connect((HOST, PORT))

        if len(metodo) > 0 and len(payload) > 0:
            request = Request(metodo, payload)
            dial.sendall(request.serializar())
            rawData = dial.recv(BUFFER)
            response = Response()
            sortedList = response.deserializar(rawData).get('response', None)

            if sortedList:
                print('Resposta do servidor:', sortedList)
        else:
            print('Dados inválidos', file=sys.stderr)
            sys.exit(1)


if __name__ == "__main__":
    client()
