import socket
import json
import struct
import os
share_dir = 'E:/SimpleMinecraftLauncher/server'
send = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
send.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
send.bind(("127.0.0.1",2006))
send.listen()
while True:
    # 接收客户端连接请求
    conn, client_addr = send.accept()
    while True:
        # 接收客户端数据/命令
        res = conn.recv(1024)
        if not res:
            continue
        # 解析命令 
        cmds = res.decode('utf-8')
        filename = cmds  
        header_dic = {
            'filename': filename,
            'file_size': os.path.getsize('{}/{}'.format(share_dir, filename))
        }
        # 序列化报头
        header_json = json.dumps(header_dic)  
        header_bytes = header_json.encode('utf-8')  
        conn.send(struct.pack('i', len(header_bytes)))
        conn.send(header_bytes)
        with open('{}/{}'.format(share_dir, filename), 'rb') as f:
            for line in f:
                conn.send(line)
send.close()
conn.close() 