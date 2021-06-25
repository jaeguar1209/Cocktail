import socket
import main
host = '172.30.1.46' # 호스트 ip를 적어주세요
port = 8080           # 포트번호를 임의로 설정해주세요
while (True):
    server_sock = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
    server_sock.bind((host, port))
    server_sock.listen(1)
    print("기다리는 중")
    client_sock, addr = server_sock.accept()

    print('Connected by', addr)
    data = client_sock.recv(3000)
    data= data.decode("utf-8")[2:-1]
    data= data.split(",")
    para1=data[0].split()
    para2=data[1:]
    print(para1)
    print(para2)
    a=main.Cook(para1,para2)

    data2 =int (a)
    print(data2)
    client_sock.send(data2.to_bytes(4, byteorder='little'))
client_sock.close()
server_sock.close()
