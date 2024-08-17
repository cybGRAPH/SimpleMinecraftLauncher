import socket
def recv(dir:str,version:str,ip:str,port:int):#输入
    recv=socket.socket(socket.AF_INET, socket.SOCK_STREAM)#建立套接字
    recv.connect((ip,port))#连接到服务端
    while True:
        mes = tcp_socket.recv(4096)
        if mes:
            # 解码->文件内写入
            new_file.write(mes.decode())
            time += len(mes)
        else:
            if time == 0:
                new_file.close()
                os.remove(file_name)
                print("nope")
            else:
                print("success")
            break
    tcp_socket.close()
if __name__ == '__main__':
    connect()