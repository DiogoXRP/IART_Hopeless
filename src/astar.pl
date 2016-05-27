%estado inicial
estado_inicial(b(0,0)).

%estado final
estado_final(b(2,0)).

		%% A*
		%% Problema para A* conta quanta agua é movida [e tenta minimizar]
		
sucessor(b(X,Y), b(4,Y), C):- X<4,
       						C is 4 - X .
							
sucessor(b(X,Y), b(X,3), C):- Y<3, C is 4 - Y .
sucessor(b(X,Y), b(0,Y), C):- X>0, C is X .
sucessor(b(X,Y), b(X,0), C):- Y>0, C is Y .
sucessor(b(X,Y), b(4,Y1), C):-
                        X+Y>=4,
                        X<4,
                        Y1 is Y-(4 - X), C is 4 - X .
sucessor(b(X,Y), b(X1,3), C):-
                        X+Y>=3,
                        Y<3,
                        X1 is X-(3 - Y), C is 3 - Y .
sucessor(b(X,Y), b(X1,0), C):-
                        X+Y<4,
                        Y>0,
                        X1 is X+Y, C is Y.
sucessor(b(X,Y), b(0,Y1), C):-
                        X+Y<3,
                        X>0,
                        Y1 is X+Y, C is X .
						
						
						
%%%%%%%%%%%%%%%%%%%%% Algoritmo em si
%% definir Heuristic function  [distancia ao estado final]

h(b(X,Y), H) :- 
estado_final(b(Xf, Yf)),
H is max(abs(Xf - X), abs(Y-Yf)).


astar([(F,_,[E|Path])|_], (F,[E|Path])):- estado_final(E) .

astar([(_, G, [E|Path])|R], Sol):-
findall((F1, G1, [E1|[E|Path]]), 
		(sucessor(E,E1,C), G1 is G + C,
		 h(E1, H1), F1 is G1 + H1),
		 LS),
append(R,LS,L), 
sort(L, LOrd),
astar(LOrd,Sol).

solve_astar:-
estado_inicial(Ei), h(Ei, Hi),
astar([(Hi, 0, [Ei])], Sol),
write(Sol).
