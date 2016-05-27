?- use_module(library(lists)).

%%%%%%%%%%%%%%%%% PRINT TABULEIRO    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
print_list([]).
print_list([C|Rest]) :- 
							write(C),
							write(' '),
							print_list(Rest).
print_matrix([],_).
print_matrix([C|Rest],N):-
						Nr is N + 1,
						write(Nr),
						write(' '),
						print_list(C),
						nl,
						print_matrix(Rest,Nr).

%% print_coord:-
%%						write('  '),
%%						write('A'),
%%						write(' '),
%%						write('B'),
%%						write(' '),
%%						write('C'),
%%						write(' '),
%%						write('D'),
%%						write(' '),
%%						write('E'),
%%						nl.


%%%%%%%%%%%%%%%%% MANIPULAR TABULEIRO %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
set_piece_list(1, Elem, [_|L], [Elem|L]).
set_piece_list(I, Elem, [H|L], [H|ResL]):-
  I > 1,
  I1 is I-1,
  set_piece_list(I1, Elem, L, ResL).

set_piece_tabuleiro(1, Col, NewElem, [H|T], [NewH|T]) :-  set_piece_list(Col, NewElem, H, NewH).
set_piece_tabuleiro(Row, Col, NewElem, [H|T], [H|ResT]):-
  Row > 1,
  Row1 is Row-1,
  set_piece_tabuleiro(Row1, Col, NewElem, T, ResT).          
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%% VERIFICAR PEÇAS %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
getListElemAt(1, [ElemAtTheHead|_], ElemAtTheHead).
getListElemAt(Pos, [_|RemainingElems], Elem):-
	Pos > 1,
	Pos1 is Pos-1,
	getListElemAt(Pos1, RemainingElems, Elem).

getMatrixElemAt(ElemRow, ElemCol,_, Elem) :-
ElemRow > 5, Elem = 'U', ! ; ElemCol > 5, Elem = 'U', ! ; ElemRow < 1, Elem = 'U', ! ; ElemCol < 1, Elem = 'U', ! .

getMatrixElemAt(1, ElemCol, [ListAtTheHead|_], Elem):-
    getListElemAt(ElemCol, ListAtTheHead, Elem).

getMatrixElemAt(ElemRow, ElemCol, [_|RemainingLists], Elem):-
	ElemRow > 1,
	ElemRow1 is ElemRow-1,
	getMatrixElemAt(ElemRow1, ElemCol, RemainingLists, Elem).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%% estado inicial
estado_inicial(b(0,0)).

%% estado final
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
