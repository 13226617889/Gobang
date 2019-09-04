# Size 19 * 19
# 玩家跟电脑下棋，率先下满5个成线的，则赢
import random

WIDTH = 19
HEIGHT = 19


# 1.谁先走
# 2.选择棋子
# 3.打印游戏版
# 4.第二种打印游戏版的显示
# 5.新得空白游戏版
# 6.给电脑做AI模拟用的游戏版
# 7.判定坐标是否超出了游戏版宽度
# 8.游戏第一层的判定  xxx
# 9.游戏第一层的判定  x x
#
def whoGhostFirst():
    if random.randint(0, 1) == 0:
        return 'computer'
    else:
        return 'play'


def enterXY():
    tile = ''
    while not (tile == 'X' or tile == 'O'):
        print('Enter you X Y ?')
        tile = input().upper()

    if tile == 'X':
        return ['X', 'O']
    else:
        return ['O', 'X']


def drawBoard(board):
    print(' +----------------------------------+')
    for y in range(HEIGHT):
        if 0 <= y <= 8:
            print(' %s|' % (y + 1), end='')
        else:
            print('%s|' % (y + 1), end='')
        for x in range(WIDTH):
            print(board[x][y] + '|', end='')
        print('%s' % (y + 1))

    print(' +----------------------------------+')


def print_board():
    # 打印列号
    print('  ', end='')
    for i in range(1, 16):
        c = chr(ord('a') + i - 1)  # ord 字母转ASCLL码
        print(c, end='')
    print()
    # 棋盘
    for i in range(1, 16):
        if 1 <= i <= 9:
            print(' ', end='')
        print(i, end='')
        for j in range(1, 16):
            print('x', end='')
        print()


def getplaymove(board, playTile):
    # 先判定输入的X，y宽度是否符合
    Dis = ('1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19').split()
    while True:
        move = input('请输入你的移动').lower().split(' ')
        if move == 'quit' or move == 'hints':
            return move

        # 两个判定
        if len(move) == 2 and move[0] in Dis and move[1] in Dis:
            x = int(move[0]) - 1
            y = int(move[1]) - 1
            break
    return [x, y]


def markmove(board, tile, xstart, ystart):
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    board[xstart][ystart] = tile
    return True


def newBoard():
    board = []

    for i in range(HEIGHT):
        board.append([' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '])
    return board


def getCopyBoard(board):
    BOARD = newBoard()
    for i in range(WIDTH):
        for j in range(HEIGHT):
            BOARD[i][j] = board[i][j]
    return BOARD


def isOnBoard(x, y):
    return (x <= WIDTH - 1 and x >= 0) and (y <= HEIGHT - 1 and y >= 0)


def oneIF(board, tile, xstart, ystart):
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        x += -xdir
        y += -ydir
        Score = 0
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir

            while isOnBoard(x, y) and board[x][y] == tile:
                Score += 1
                x += xdir
                y += ydir
                if Score == 3 and board[x][y] == ' ':
                    return True
        if isOnBoard(x, y) and board[x][y] == tile:
            x, y = xstart, ystart
            x += xdir
            y += ydir

            while isOnBoard(x, y) and board[x][y] == tile:
                Score += 1
                x += xdir
                y += ydir
                if Score == 2 and board[x][y] == ' ':
                    return True

    return False


def OneIf2(board, tile, xstart, ystart):
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        x += -xdir
        y += -ydir
        tileNumber = 0
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir
            if isOnBoard(x, y) and board[x][y] == ' ':
                while isOnBoard(x, y):
                    x += xdir
                    y += ydir
                    if isOnBoard(x, y) and board[x][y] == ' ':
                        break
                    if isOnBoard(x, y) and board[x][y] == tile:
                        tileNumber += 1
                        if isOnBoard(x + xdir, y + ydir) and tileNumber == 2 and board[x + xdir][y + ydir] == ' ':
                            return True
            if isOnBoard(x, y) and board[x][y] == tile:
                x += xdir
                y += ydir
                if isOnBoard(x, y) and board[x][y] == ' ':
                    x += xdir
                    y += ydir
                    if isOnBoard(x, y) and board[x][y] == tile:
                        if isOnBoard(x + xdir, y + ydir) and board[x + xdir][y + ydir] == ' ':
                            return True


def one(board, tile, xstart, ystart):
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        x += -xdir
        y += -ydir
        Score = 0
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir

            if isOnBoard(x, y) and board[x][y] == tile:
                x += xdir
                y += ydir
                if board[x][y] == ' ':
                    return True
    return False


def TWOIf(board, tile, xstart, ystart):
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        x += -xdir
        y += -ydir
        Score = 0
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir

            while isOnBoard(x, y) and board[x][y] == tile:
                Score += 1
                x += xdir
                y += ydir
                if Score == 2 and board[x][y] == ' ':
                    return True
    return False


"""
        这里判定组成第一层的条件   

        这里判定组成第一层的条件 
        tileNumber = 0
        space = 0
        while True:
            x += xdir
            y += ydir
            if isOnBoard(x, y) and board[x][y] == tile and space <= 1:
                tileNumber += 1
            elif isOnBoard(x, y) and board[x][y] == ' ':
                space += 1
            if tileNumber >= 3 and space == 1: # 这里就能返回是否是这种整容了
                MaxSpace += 1 
                break
            elif space > 1:
                break
        """


def chess(board, tile, xstart, ystart):
    if tile == 'X':
        theTile = 'O'
    else:
        theTile = 'X'
    MaxSpace = 0
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        Score = 0
        x += -xdir
        y += -ydir
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir
            space = 0
            tileNumber = 0
            if isOnBoard(x, y) and board[x][y] == ' ':
                while isOnBoard(x, y):
                    x += xdir
                    y += ydir
                    if isOnBoard(x, y) and board[x][y] == ' ':
                        break
                    if isOnBoard(x, y) and board[x][y] == tile:
                        tileNumber += 1
                        if isOnBoard(x + xdir, y + ydir) and tileNumber == 2 and board[x + xdir][y + ydir] == ' ':
                            MaxSpace += 1
            if isOnBoard(x, y) and board[x][y] == tile:
                x += xdir
                y += ydir
                if isOnBoard(x, y) and board[x][y] == ' ':
                    x += xdir
                    y += ydir
                    if isOnBoard(x, y) and board[x][y] == tile:
                        if isOnBoard(x + xdir, y + ydir) and board[x + xdir][y + ydir] == ' ':
                            MaxSpace += 1

            x, y = xstart, ystart
            x += xdir
            y += ydir
            while isOnBoard(x, y) and board[x][y] == tile:
                Score += 1
                x += xdir
                y += ydir
                if Score >= 2 and board[x][y] == ' ':
                    MaxSpace += 1
                    break
        if isOnBoard(x, y) and board[x][y] == tile and board[x + -xdir][y + -ydir] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir
            if isOnBoard(x, y) and board[x][y] == ' ':
                x += xdir
                y += ydir
                if isOnBoard(x, y) and board[x][y] == tile and board[x + xdir][y + ydir] == ' ':
                    MaxSpace += 1

    if MaxSpace >= 2:
        return True

    return False


def chessWin(board, tile, xstart, ystart):
    if tile == 'X':
        theTile = 'O'
    else:
        theTile = 'X'

    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        MaxSocre = 0
        x, y = xstart, ystart
        # 只要两方向条件都成立，则为棋阵。
        x -= xdir
        y -= ydir
        while isOnBoard(x, y) and board[x][y] == tile:
            MaxSocre += 1
            x -= xdir
            y -= ydir
        x, y = xstart, ystart
        # 只要两方向条件都成立，则为棋阵。
        x += xdir
        y += ydir
        while isOnBoard(x, y) and board[x][y] == tile:
            MaxSocre += 1
            x += xdir
            y += ydir
        if MaxSocre >= 4:
            return True

    return False


def CHESS(board, tile, xstart, ystart):
    TScore = 0
    if tile == 'X':
        theTile = 'O'
    else:
        theTile = 'X'
    # 在进行方向判定先首先要确定判定方向的对面，是否有空格
    if board[xstart][ystart] != ' ' or not isOnBoard(xstart, ystart):
        return False
    for xdir, ydir in [[0, 1], [1, 1], [1, 0], [1, -1], [0, -1], [-1, -1], [-1, 0], [-1, 1]]:
        x, y = xstart, ystart
        Score = 0
        x += -xdir
        y += -ydir
        if isOnBoard(x, y) and board[x][y] == ' ':
            x, y = xstart, ystart
            x += xdir
            y += ydir
            # 这里就可开始判定了
            while isOnBoard(x, y) and board[x][y] == tile:
                Score += 1
                x += xdir
                y += ydir
                if Score >= 2 and board[x][y] == ' ':
                    TScore += 1
                    return True

    return False


# 创建一个列表存储可以下的棋型
def winchess(board, tile):
    win = []
    tileSome = []
    for x in range(WIDTH):
        for y in range(HEIGHT):
            if chessWin(board, tile, x, y) != False:
                win.append([x, y])
            if chess(board, tile, x, y):
                tileSome.append([x, y])
    return win, tileSome


def winchesss(board, tile):
    One = []
    Two = []
    ONE = []
    for x in range(WIDTH):
        for y in range(HEIGHT):
            if OneIf2(board, tile, x, y):
                Two.append([x, y])
            if oneIF(board, tile, x, y):
                One.append([x, y])
            if TWOIf(board, tile, x, y):
                Two.append([x, y])
            if one(board, tile, x, y):
                ONE.append([x, y])
    return One, Two, ONE


def allspace(board):
    space = []
    for x in range(WIDTH):
        for y in range(HEIGHT):
            if board[x][y] == ' ':
                space.append([x, y])
    return space


def center(board):
    Center = []
    for i in range(7, 12):
        for j in range(7, 12):
            if board[i][j] == ' ':
                Center.append([i, j])
    return Center


def ComputerMove(board, tile):
    if tile == 'X':
        thetile = 'O'
    else:
        thetile = 'X'
    Win, tileSome = winchess(board, tile)
    One, Two, ONE = winchesss(board, tile)
    playWin, playtileSome = winchess(board, thetile)
    playOne, playTwo, playONE = winchesss(board, thetile)
    # 赢
    if len(Win) != 0:
        random.shuffle(Win)
        return Win[0]
    else:
        if len(playWin) != 0:
            random.shuffle(playWin)
            return playWin[0]
    # 三个
    if len(One) != 0:
        random.shuffle(One)
        return One[0]
    # 阵型
    if len(playOne) != 0:
        random.shuffle(playOne)
        return playOne[0]
    else:
        if len(tileSome) != 0:
            random.shuffle(tileSome)
            return tileSome[0]

    if len(Two) != 0:
        random.shuffle(Two)
        return Two[0]
    else:
        if len(playTwo) != 0:
            random.shuffle(playTwo)
            return playTwo[0]
    if len(ONE) != 0:
        random.shuffle(ONE)
        return ONE[0]
    if len(center(board)) != 0:
        random.shuffle(center(board))
        return center(board)[0]


# 如果对面没有One  则阵型
def play:
    if allspace(board) != []:
        move = getplaymove(board, play)


def playGame(play, computer):
    who = whoGhostFirst()  # 谁先动
    board = newBoard()
    for i in board:
        print(i)
    while True:
        # 缺一层判定，判断是否能赢，判定是否全图没有空格
        if who == 'play':
            if allspace(board) != []:
                drawBoard(board)
                move = getplaymove(board, play)
                if move == 'quit':
                    print('Thanks for playing!')
                    sys.exit()
                else:
                    markmove(board, play, move[0], move[1])
            who = 'computer'
        elif who == 'computer':
            if allspace(board) != []:
                drawBoard(board)
                input('Press Enter to see the computer\'s move.')
                move = ComputerMove(board, computer)
                markmove(board, computer, move[0], move[1])
            who = 'play'


if __name__ == '__main__':

    play, computer = enterXY()
    print(play, computer)
    while True:
        RunGame = playGame(play, computer)
        scores = getScore()
        print('X scored %s points. 0 scored %s points.' % (scores['X'], scores['O']))
        if scores[play] > scores[computer]:
            print('You beat the computer by %s points! Congratulations!' % (scores[playerTile] - scores[computerTile]))
        elif scores[play] < scores[computer]:
            print('You lost. The computer beat you by %s points.' % (scores[computerTile] - scores[playerTile]))
        else:
            print('The game was a tim!')
        print('Do you want to play again? (yes or no)')
        if not input().lower().startswith('y'):
            break
"""
print(ComputerMove(board,'X'))
Win, tileSome = winchess(board, 'X')
One, Two = winchesss(board, 'X')
print('Win: ', Win)
print('阵型: ', tileSome)
print('One:', One, ' ', 'Two:', Two)
move = getplaymove(board,'O')
#print(OneIf2(board,'X',1,10))

drawBoard(board)
D, C = winchess(board, 'X')
print(D)
print(C)

    先判定下一步棋子是不是连成5个
    再进行以下判定
    第一阶段判定 xx_x  or xxx 完成
    第二阶段才判定阵型
    还要写一个判定对方是否有即将获胜的 和 即将组成阵型
    如果自己不能获胜或者不能组成阵型
    则判定对方能否获胜或者组成阵型

    优先级
    获胜大过阵型
    例:
    如果自己可以获胜，优先下自己的，如果没有获胜则判定对方是否获胜
    对方可以获胜,则先堵对方
    对方不可以获胜则进行以下判定
    先判定自己是否有已经成型的阵容，如果有则下自己的。
    如果没有则判定对方是否有成型的阵容
    都没有 再进行下层判定
    判定自己能不能组成阵容
    再判定对方能不能组成阵容


"""