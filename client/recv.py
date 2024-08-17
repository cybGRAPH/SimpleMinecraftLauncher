import socket
import struct
import json
def recvin(dir:str,ip:str,port:int,filename:str):#输入
    recvdata=socket.socket(socket.AF_INET, socket.SOCK_STREAM)#建立套接字
    recvdata.connect((ip,port))#连接到服务端
    recvdata.send(filename.encode('utf-8'))#向服务端发送文件需求
    #从此开始接收服务端数据
    #开始收报头长度
    obj = recvdata.recv(4)
    header_size = struct.unpack('i', obj)[0]
    header_bytes = recvdata.recv(header_size)
    header_json = header_bytes.decode('utf-8')
    header_dic = json.loads(header_json)
    total_size = header_dic['file_size']
    filename = header_dic['filename']
    #收报头,解析报头,发送指令
    with open('%s/%s' % (dir, filename), 'wb') as f:
        recv_size = 0
        while recv_size < total_size:
            line = recvdata.recv(1024)
            f.write(line)
            recv_size += len(line)
            print('总大小：%s     已下载：%s' % (total_size, recv_size))
    recvdata.close()