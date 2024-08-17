import pywebio
import pywebio.output as outi
import pywebio.input as ini
def InputVersionName():
    outi.put_markdown("## 欢迎使用SimpleMinecraftLuncher")
    outi.put_markdown("******")
    version=ini.input("输入版本")#输入版本
    username=ini.input("输入用户名")#输入用户名
    outi.put_markdown("您现在可以关闭本标签页")
    print(version,username)
    pywebio.session.hold()#保持标签页直到用户关闭
    return [version,username]
