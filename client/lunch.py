from recv import recv
from inputUI import InputVersionName
import os
username=os.getlogin()#获取用户名
MinecraftDir=f"C:\user\{username}\appdata\Roaming\.minecraft"#minecraft路径
minecraft_username_list=InputVersionName()#调用inputUI.py中的输入函数
username=minecraft_username_list[1] #分割用户名
version=minecraft_username_list[0] #分割用户需求的版本
recv(dir,"lobbynetwork.online",version) #下载版本