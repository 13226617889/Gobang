import pygame
import sys
from Gobang import *
import random
#可以添加个碰撞检测
def whoGhostFirst():
    if random.randint(0, 1) == 0:
        return 'computer'
    else:
        return 'play'

def getXY():
    tile = ''
    if tile == '黑':
        return ['X', 'O']
    else:
        return ['O', 'X']
    #黑=X 白=O
pygame.init()
size = width,height = 640,640
screen = pygame.display.set_mode(size)
color = (0, 0, 0)
ball = pygame.image.load(r'C:\Users\Administrator\Desktop\微信图片_20190829161113.jpg')
mouse_cursor = pygame.image.load(r'C:\Users\Administrator\Desktop\未标题-2.png')
mouse_White = pygame.image.load(r'C:\Users\Administrator\Desktop\3.png')
ballrect = ball.get_rect()
text = []
x = -10
score = 32
scorex = 32
ZT = []
board = newBoard()
who = whoGhostFirst()
print(who)
for i in range(19):
    if i == 0:
        y = -13.5
    else:
        y += 32.3
    x = -10
    ZT.append([])
    text.append([])
    for j in range(19):
        if j != 0:
            x += 32.5
        ZT[i].append(False)
        text[i].append([x, y])

print(ZT)

print(mouse_cursor.get_rect())


init1,init2 = -10, -13
#获取的鼠标是 40 37
#宽度间隔 32
#高度间隔 32
#除以2 就是他们的差距 获取的位置 - 图片宽度/ 2
list = []
x1, y1 = 100,100
boke = 0
book = []
BOOK = []
WIN = False
#状态
dog = False
play, Computer = getXY()
# 让pygame完全控制鼠标
"""=
pygame.mouse.set_visible(False)
pygame.event.set_grab(True)
"""
while True:
    for event in pygame.event.get():

        if event.type == pygame.QUIT:
            sys.exit()

        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_ESCAPE:
                exit()


    screen.blit(ball,ballrect)
    pygame.display.set_caption('图片拖拽')

    x, y = pygame.mouse.get_pos()
    b = pygame.mouse.get_pressed()

   #这里解决了鼠标一直按住的方法
    if WIN == False:
        if who == 'play':
            if b[0] == 1:
                dog = True
                #可以添加进方法
                boke += 1
            else:
                dog = False
                if boke > 1 and dog == False:
                    x1, y1 = pygame.mouse.get_pos()

                    for j in range(len(text)):
                        for i in range(len(text[j])):
                            if text[j][i][0] > (x1 - 50):
                                IFx = text[j][i][0] - (x1 - 50) <= 15
                            else:
                                IFx = (x1 - 50) - text[j][i][0] <= 15
                            if text[j][i][1] > (y1 - 50):
                                IFy = text[j][i][1] - (y1 - 50) <= 15
                            else:
                                IFy = (y1 - 50) - text[j][i][1] <= 15
                            if IFx and IFy:
                                if ZT[i][j] == False:
                                    ZT[i][j] = True
                                    #还需添加黑白棋判定
                                    #就是如果等于play 则返回O棋子和他的坐标
                                    #电脑就先提供棋子给他，再返回坐标
                                    x1, y1 = text[j][i][0],text[j][i][1]
                                    book.append([x1, y1])
                                    markmove(board, play, i, j)
                                    if WINWIN(board,play, i, j):
                                        print('Win')
                                        WIN = True

                                    who = 'computer'
                                    #这边要添加吧游戏函数判定条件 这边提供x,y
                    boke = 0
        else:
            #这里到电脑移动
            #电脑这边返回x,y,显示

            j, i = ComputerMove(board, Computer)
            print(j,i)
            print(ZT[j][i])
            if ZT[j][i] == False:
                ZT[j][i] = True
                X, Y = text[i][j]
                markmove(board, Computer, j, i)
                BOOK.append([X, Y])

            if WINWIN(board, Computer, j, i):
                print('Win')
                WIN = True
            who = 'play'

    for i in range(len(book)):
        screen.blit(mouse_cursor,(book[i][0],book[i][1]))

    for i in range(len(BOOK)):
        screen.blit(mouse_White, (BOOK[i][0], BOOK[i][1]))
    pygame.mouse.set_visible(True)
    x -= mouse_cursor.get_width() / 2
    y -= mouse_cursor.get_height() / 2
    c =screen.blit(mouse_cursor, (x, y))

    pygame.display.flip()
pygame.quit()
