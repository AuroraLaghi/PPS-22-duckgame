% Roll one dice and return the result
% Max = seed, X = random integer between 1 and 6
rollDice(Max, X):-rand_int(Max,N), X is N+1.