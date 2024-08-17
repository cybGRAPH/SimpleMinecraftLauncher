import socket,os
def connect(tcp_port,tcp_ip,file_name):
    tcp_socket = socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_ip = str(tcp_ip)#服务器ip
    tcp_port = int(tcp_port)#服务器端口
    file_name=str(file_name)#文件名称
    tcp_socket.connect((tcp_ip, tcp_port))
    tcp_socket.send(file_name.encode())
    new_file = open(file_name, "wb")
    time = 0
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