% Return the coordinates of the N-th cell in a grid of given rows and columns
% N = Nth cell, Y = size of columns in grid, X = size of rows in grid
% Y1 = column in given position, X1 = row in given position

getCellInGrid(N, Y, X, Y1, X1):- R is (N mod Y), R = 0 -> Y1 is Y -1, X1 is N//Y - 1;
Y1 is ((N mod Y)-1), X1 is N//Y.


% Return the coordinates of a grid of cell given player's position
% P = position, C = CELLS IN SIDE (default 7)
% Y = column of N cell, X = row of N cell

getCoordinateFromPosition(P,C,Y,X):- P < C -> Y is P, X is 0;
P < C*2 -> Y is C, X is P - C;
P < C*3 -> Y is (C * 3) - P, X is C;
P < (C*4) - 1 -> Y is 0, X is (C*4)-P;
P < (C*5) - 2 -> Y is P - ((C*4) - 1) , X is 1;
P < (C*5) + 3 -> Y is C - 1, X is P -((C + 1)*4);
P =< C*6 -> Y is ((C*6)+2) - P, X is 6;
P < (C**2) - 2 -> Y is 1, X is ((C**2)-P);
P < (C**2)+2 -> Y is P-((C**2)-3), X is 2;
P < (C*8) -2 -> Y is 5 , X is P-(C**2);
P =< C*8 -> Y is ((C*8)+3)-P , X is 5;
P < (C*8) + 3 -> Y is 2 , X is ((C*9)-1)-P;
P < (C*9) - 2 -> Y is P-((C*8)+1) , X is 3;
P < C*9 -> Y is 4 , X is P-((C*8)+2);
Y is 3, X is 4.