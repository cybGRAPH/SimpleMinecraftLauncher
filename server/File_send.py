import socket
import threading
def file_deal(filename):
    try:
        files = open(filename, "rb")
        filedir = files.read()
    except:
        print("Nofile")
    else:
        files.close()
        return filedir
def filesend(file):
    tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_socket.bind(("",2006))
    tcp_socket.listen(128)
    while True:
        client_socket = tcp_socket.accept()
        file_name = client_socket.recv(4096)
        filedir = file_deal(file_name)
        if filedir:
            client_socket.send(filedir)
        client_socket.close()
if __name__ == "__main__" :
    filesend("requirements")