import socket
def recv(dir:str,version:str,ip:str,port:int):#输入
    recv=socket.socket(socket.AF_INET, socket.SOCK_STREAM)#建立套接字
    recv.connect((ip,port))#连接到服务端
    while True:
        filename = version.strip()
        if not filename:
            continue