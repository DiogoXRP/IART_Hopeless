%%% :- include('nomeficheiro').
cls:-write('\e[2J'). %Clear Screen on 

%%%%%%%%%%%%%%% IMPRIME TAULEIRO %%%%%%%%%%%%%%%%%%%%%%%%%%%
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

print_coord:-
						write('  '),
						write('A'),
						write(' '),
						write('B'),
						write(' '),
						write('C'),
						write(' '),
						write('D'),
						write(' '),
						write('E'),
						nl.

print_tab(T,Rpool,Bpool):-
				nl,
				write('SIXTEEN STONE'),
				nl,
				nl,
				print_coord,
				Nr is 0,
				print_matrix(T,Nr),
				nl,
				write('    POOL'),
				nl,
				write('Red:'),
				write(Rpool), 
				write('  Blue:'),
				write(Bpool),
				nl,
				write(' ').


tab_init([ ['.','.','.','.','.'],
    	   ['.','.','.','.','.'],
    	   ['.','.','.','.','.'],
    	   ['.','.','.','.','.'],
    	   ['.','.','.','.','.'] ] ).

%%%% TESTAR PRINT_TAB
testar_p_tab :-
    tab_init(Teste),
    R is 0,
    B is 0,
    print_tab(Teste,R,B).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%



%%%%%%%%%%%%%%%%% FIM IMPRIMIR TABULEIRO %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


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


%%%%%%%%%%%%%%%%%%%%%%%%%%%% UTILIDADES %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

choose_piece(Player,Piece):-
	(	Player=1 -> (Piece = 'R');
		Player=2 -> (Piece = 'B');true).

choose_enemy_piece(Player,Piece):-
	(	Player=1 -> (Piece = 'B');
		Player=2 -> (Piece = 'R');true).

enemy_who(Player,Enemy):-
	(	Player=1 -> (Enemy is 2);
		Player=2 -> (Enemy is 1);true).



translateOrient(X, Y, O, X2, Y2) :-
	X3 is (-1), Y3 is (-1), YM is Y - 1, XM is X - 1, YP is Y + 1, XP is X + 1,
	(	O = 'N' -> (Y2 is YM,X2 is X);
		O = 'NE'-> (Y2 is YM, X2 is XP);
		O = 'E' -> (X2 is XP, Y2 is Y);
		O = 'SE'-> (Y2 is YP, X2 is XP);
		O = 'S' -> (Y2 is YP, X2 is X);
		O = 'SW'-> (Y2 is YP, X2 is XM);
		O = 'W' -> (X2 is XM, Y2 is Y);
		O = 'NW'-> (Y2 is YM, X2 is XM);
		X2 is X3, Y2 is Y3).

reverseList([],X,X).
reverseList([H|T],X,Acc) :- reverseList(T,X,[H|Acc]).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


%%%%%%%%%%%%%%%%%%%%% COLOCA PEÇA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
verifica_vazio(Bool,Coord_x,Coord_y,Tab_entrada):-
	getMatrixElemAt(Coord_y, Coord_x, Tab_entrada, Elemento),
	(	Elemento\= '.' -> (Bool = 1);
		Elemento = '.' -> (Bool = 0) 
							).

coord_AlphatoNum(Aplpha_x,Num_x):-
	(	Aplpha_x='A' -> (Num_x = 1);
		Aplpha_x='B' -> (Num_x = 2);
		Aplpha_x='C' -> (Num_x = 3);
		Aplpha_x='D' -> (Num_x = 4);
		Aplpha_x='E' -> (Num_x = 5) ).


put_piece(Player,Stack_entrada,Stack_saida,Tab_entrada,Tab_saida):-
	write('Select the column [A-E]'),
	nl,
	read(Alpha_x),
	coord_AlphatoNum(Alpha_x,Coord_x),
	write('Select the row [1-5]'),
	nl,
	read(Coord_y),
	choose_piece(Player,Piece),
	verifica_vazio(Bool,Coord_x,Coord_y,Tab_entrada),
	(	Bool=1 -> (put_piece(Player,Stack_entrada,Stack_saida,Tab_entrada,Tab_saida));
		Bool=0 -> (set_piece_tabuleiro(Coord_y,Coord_x,Piece,Tab_entrada,Tab_saida))
					),
	Stack_saida is Stack_entrada -1.

	%%%%% TESTAR COLOCAÇÃO DE PEÇA
	testar_colocar_peca:-
		tab_init(Teste),
		R is 1,
		B is 2,
		cls,
		print_tab(Teste,R,B),
		put_piece(1,R,Stack_saida,Teste,Tab_saida),
		cls,
		print_tab(Tab_saida,Stack_saida,B),
		put_piece(2,B,Stack_nova,Tab_saida,Tab_novo),
		cls,
		print_tab(Tab_novo,Stack_saida,Stack_nova).
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%% MOVER PEÇA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

move_piece(Player,Tab_entrada,Tab_saida):-
	choose_piece(Player,Piece),
	nl,
	write('Select the Stone you want to move'),
	nl,
	write('Column [A-E]'),
	nl,
	read(Alpha_x),
	coord_AlphatoNum(Alpha_x,Coord_x),
	write('Row [1-5]'),
	nl,
	read(Coord_y),
    getMatrixElemAt(Coord_y, Coord_x, Tab_entrada, Elem),
	(	Elem \= Piece -> (	write('That is not your stone, dude!'),
					nl,
					move_piece(Player,Tab_entrada,Tab_saida)
					);
		Elem =  Piece -> (	write('In which direction?'),
							read(Orient),
							write('Orient:'),
							write(Orient),
							nl,
							translateOrient(Coord_x,Coord_y,Orient,Dest_x,Dest_y),
							write('Dest_x:'),
							write(Dest_x),
							nl,
							write('Dest_y:'),
							write(Dest_y),
							%%check_capture(, Listanoncapturable),
							verifica_vazio(Bool,Dest_x,Dest_y,Tab_entrada),
							(	Bool=1 -> (	write('Cannot move there!'),
											move_piece(Player,Tab_entrada,Tab_saida)
											);
								Bool=0 -> (	set_piece_tabuleiro(Coord_y, Coord_x, '.', Tab_entrada, S),
											set_piece_tabuleiro(Dest_y, Dest_x, Elem, S, Tab_saida)
											)
							)
						 )
	).


	%%%%% TESTAR COLOCAÇÃO DE PEÇA
	testar_move:-
		tab_init(Teste),
		R is 5,
		B is 5,
		cls,
		print_tab(Teste,R,B),
		put_piece(1,R,Stack_saida,Teste,Tab_saida),
		cls,
		print_tab(Tab_saida,Stack_saida,B),
		put_piece(1,Stack_saida,Stack_nova,Tab_saida,Tab_novo),
		cls,
		print_tab(Tab_novo,Stack_nova,B),
		move_piece(1,Tab_novo,Tab_novissimo),
		cls,
		print_tab(Tab_novissimo,Stack_nova,B).

	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% FIM MOVER PEÇA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% EMPURRAR PEÇA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
check_line(Player,TabEntrada,Coord_x,Coord_y,Orient,Lista_x,Lista_y,Lista_x_sai,Lista_y_sai,Inimigo_x,Inimigo_y,TamanhoFila_ent,TamanhoFila_sai,Erro):-
	getMatrixElemAt(Coord_y, Coord_x,TabEntrada, Elem),
	choose_piece(Player,Piece),
	choose_enemy_piece(Player,EnemyPiece),
	
	write(Lista_x),
	(	Elem=Piece 		-> 		(	
									append(Lista_x,[Coord_x],L_x),
									append(Lista_y,[Coord_y],L_y),
									Tmaior is TamanhoFila_ent + 1,
									translateOrient(Coord_x,Coord_y,Orient,Dest_x,Dest_y),
									check_line(Player,TabEntrada,Dest_x,Dest_y,Orient,L_x,L_y,Lista_x_sai,Lista_y_sai,Inimigo_x,Inimigo_y,Tmaior,TamanhoFila_sai,Erro)
								);
		Elem='.' 		-> 		(	Erro is 1,TamanhoFila_sai is TamanhoFila_ent,Lista_x_sai = Lista_x,Lista_y_sai = Lista_y);
		Elem='U'		->		(	Erro is 2,TamanhoFila_sai is TamanhoFila_ent,Lista_x_sai = Lista_x,Lista_y_sai = Lista_y);
		Elem=EnemyPiece ->		(	Erro is 0,TamanhoFila_sai is TamanhoFila_ent,Inimigo_x is Coord_x,Inimigo_y is Coord_y,Lista_x_sai = Lista_x,Lista_y_sai = Lista_y)
	).


push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s):-
	choose_piece(Player,Piece),
	nl,
	write('Select the first stone of the line you want to push'),
	nl,
	write('Column [A-E]'),
	nl,
	read(Alpha_x),
	coord_AlphatoNum(Alpha_x,Coord_x),
	write('Row [1-5]'),
	nl,
	read(Coord_y),
    getMatrixElemAt(Coord_y, Coord_x, Tab_entrada, Elem),
	(	Elem \= Piece -> (	write('That is not your stone, dude!'),
							nl,
							push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
							);true
	),
	write('In which direction?'),
	read(Orient),
	TamanhoFila_ent is 0,
	check_line(Player,Tab_entrada,Coord_x,Coord_y,Orient,[],[],Lista_x_sai,Lista_y_sai,Inimigo_x,Inimigo_y,TamanhoFila_ent,TamanhoFila_sai,Erro),
	(	
		TamanhoFila_sai=1 	-> 	(	write('A single stone is not a line, man!'),
			push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
			);

		Erro=1 				-> 	(	write('Cannot push the air!'),
			push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
			);
		Erro=2 				-> 	(	write('You are pushing your stones to the abyss, dude!'),
			push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
			);true
	),	

	write('[debug] tamanhoLista: '), write(TamanhoFila_sai),nl,
	write('[debug] Inimigo_x: '), write(Inimigo_x),nl,
	write('[debug] Inimigo_y: '), write(Inimigo_y),nl,

	enemy_who(Player,Enemy),
	check_line(Enemy,Tab_entrada,Inimigo_x,Inimigo_y,Orient,[],[],Lista_x_sai_Inimigo,Lista_y_sai_Inimigo,_,_,TamanhoFila_ent,TamanhoFila_sai_Inimigo,Erro_Inimigo),
	write('[debug] Erro_Inimigo: '), write(Erro_Inimigo),nl,
	(Erro_Inimigo=0 -> (	write('Cannot push through your own stone, dude!'),
		push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
		);true
	),

	write('[debug] tamanhoListaInimiga: '), write(TamanhoFila_sai_Inimigo),nl,
	write('[debug] tamanhoListaAmiga: '), write(TamanhoFila_sai),nl,
	
	(TamanhoFila_sai_Inimigo>TamanhoFila_sai -> (	
		write('Sorry but the other player line is a bit longer, dude...'),
		push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
		);true
	),
	(TamanhoFila_sai_Inimigo=TamanhoFila_sai -> (	
		write('Same size lines. That is great, cannot move the other one tho, dude!'),
		push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s)
		);true
	),
	append(Lista_x_sai,Lista_x_sai_Inimigo,Lista_x_total),
	append(Lista_y_sai,Lista_y_sai_Inimigo,Lista_y_total),
	Orient_s = Orient.


push_piece_move([],[],_,Tab_entrada,Tab_saida,Stack_R_entrada,Stack_B_entrada,Stack_R_saida,Stack_B_saida):-
	Tab_saida = Tab_entrada,
	Stack_R_saida is Stack_R_entrada,
	Stack_B_saida is Stack_B_entrada.
push_piece_move([Coord_x|Rest_x],[Coord_y|Rest_y],Orient,Tab_entrada,Tab_saida,Stack_R_entrada,Stack_B_entrada,Stack_R_saida,Stack_B_saida):-
	translateOrient(Coord_x, Coord_y, Orient, New_x, New_y),
	getMatrixElemAt(Coord_y,Coord_x,Tab_entrada,Pedra),
	getMatrixElemAt(New_y,New_x,Tab_entrada,Elem),
	write('[debug] Pedra: '), write(Pedra),nl,
	write('[debug] Elem: '), write(Elem),nl,
	write('[debug] New_x: '), write(New_x),nl,
	write('[debug] New_y: '), write(New_y),nl,
	write('[debug] Old_x: '), write(Coord_x),nl,
	write('[debug] Old_y: '), write(Coord_y),nl,
	(Elem='U' -> 	(
						set_piece_tabuleiro(Coord_y, Coord_x, '.', Tab_entrada, Tab_new),
						(	
							Pedra='R'	->	(
												New_R_Stack is Stack_R_entrada + 1,
												push_piece_move(Rest_x,Rest_y,Orient,Tab_new,Tab_saida,New_R_Stack,Stack_B_entrada,Stack_R_saida,Stack_B_saida)
											);
							Pedra='B'	->	(
												New_B_Stack is Stack_B_entrada + 1,
												push_piece_move(Rest_x,Rest_y,Orient,Tab_new,Tab_saida,Stack_R_entrada,New_B_Stack,Stack_R_saida,Stack_B_saida)
											)
						)

					);

					(
						set_piece_tabuleiro(Coord_y, Coord_x, '.', Tab_entrada, Tab_new),
						set_piece_tabuleiro(New_y, New_x, Pedra, Tab_new, Tab_SuperNew),
						push_piece_move(Rest_x,Rest_y,Orient,Tab_SuperNew,Tab_saida,Stack_R_entrada,Stack_B_entrada,Stack_R_saida,Stack_B_saida)
					)

	).

	push_piece_main(Player,Tab_entrada,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida):-
		push_piece_validate(Player,Tab_entrada,Lista_x_total,Lista_y_total,Orient_s),
		reverseList(Lista_x_total,Lista_x_total_ordenada,[]),
		reverseList(Lista_y_total,Lista_y_total_ordenada,[]),
		push_piece_move(Lista_x_total_ordenada,Lista_y_total_ordenada,Orient_s,Tab_entrada,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida).
  






	%%%%%%%%% TESTES
	teste_listas_x([]).
	teste_listas_x([H|Rest]):-
		write('[debug] x: '),
		write(H),
		nl,
		teste_listas_x(Rest).

	teste_listas_y([]).
	teste_listas_y([H|Rest]):-
		write('[debug] y: '),
		write(H),
		nl,
		teste_listas_y(Rest).


	test_push:-
		Tab =[ ['B','B','B','R','R'],
	    	   ['.','.','B','.','.'],
	    	   ['.','.','B','B','.'],
	    	   ['B','R','R','R','R'],
	    	   ['.','R','.','.','.'] ],
	   	Stack_R is 5,
	   	Stack_B is 2,
		cls,
		print_tab(Tab,Stack_R,Stack_B),
		push_piece_main(1,Tab,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida),
		print_tab(Tab_saida,Stack_R_saida,Stack_B_saida).
	%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%% FIM EMPURRAR PEÇA %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%    CHECK_CAPTURE     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

aux_append(Arg, L, L1) :-
	Lis = [],
	(var(L) -> ((append(Arg, Lis, L1)));
	(append(Arg, L, L1))).

	
aux_check_already_exists([], [], _, _).
aux_check_already_exists([HA|ResA], [H1|Res1], X, Y) :-
	%%nl, write(HA), nl, write(H1),
	(HA = X -> (H1 = Y -> (fail);true 
				); true
	),
	aux_check_already_exists(ResA, Res1, X, Y).



aux_check_equal(Elem2, Elem3, EPiece, Piece, LA, L1, X2, Y2) :-
	Elem2 = EPiece, Elem3 = Piece,/* write('LA::: '),write(LA), nl,*/
	aux_check_already_exists(LA, L1, X2, Y2).
	
	
	
aux_check_get(Row, Col, EPiece, Piece, LAlf, LNum, LAlff, LNumm, Tab) :-
%%write('LALF - '), write(LAlf),nl,
	RowM is Row - 1, ColM is Col - 1, RowP is Row + 1, ColP is Col + 1,
	Row2M is Row - 2, Col2M is Col - 2, Row2P is Row + 2, Col2P is Col + 2,
	/*(var(LAlf) -> (LA = [], LN = []);
	true),*/
	%%(LA = LAlf, LN = LNum)),
	getMatrixElemAt(RowP, ColP, Tab, Elem2), getMatrixElemAt(Row2P, Col2P, Tab, Elem3),
	(aux_check_equal(Elem2, Elem3, EPiece, Piece, LAlf, LNum, ColP, RowP) -> (write('chega1'), aux_append([ColP], LAlf, LAlf2), aux_append([RowP], LNum, LNum2));
	(append([], LAlf, LAlf2), append([], LNum, LNum2))),  %%else
	%%write('LALF2 - '), write(LAlf2),nl,
	/*getMatrixElemAt(RowP, Col, Tab, Elem4), getMatrixElemAt(Row2P, Col, Tab, Elem5),
	(aux_check_equal(Elem4, Elem5, EPiece, Piece) -> (write('chega2'), aux_append([Col], LAlf2, LAlf3), aux_append([RowP], LNum2, LNum3));
	(append([], LAlf2, LAlf3), append([], LNum2, LNum3))),*/ %%NO Need to test both sides (North and South for instance)
	
	getMatrixElemAt(RowP, ColM, Tab, Elem6), getMatrixElemAt(Row2P, Col2M, Tab, Elem7), 
	(aux_check_equal(Elem6, Elem7, EPiece, Piece, LAlf2, LNum2, ColM, RowP) -> (write('chega3'),aux_append([ColM], LAlf2, LAlf4), aux_append([RowP], LNum2, LNum4));
	(append([], LAlf2, LAlf4), append([], LNum2, LNum4))),
	%%write('LALF4 - '), write(LAlf4),nl,
	
	/*getMatrixElemAt(Row, ColM, Tab, Elem8), getMatrixElemAt(Row, Col2M, Tab, Elem9), 
	(aux_check_equal(Elem8, Elem9, EPiece, Piece) -> (write('chega4'),aux_append([ColM], LAlf4, LAlf5), aux_append([Row], LNum4, LNum5));
	(append([], LAlf4, LAlf5), append([], LNum4, LNum5))),*/
	
	getMatrixElemAt(RowM, ColM, Tab, Elem10), getMatrixElemAt(RowM, Col2M, Tab, Elem11),
	(aux_check_equal(Elem10, Elem11, EPiece, Piece, LAlf4, LNum4, ColM, RowM) -> (write('chega5'),aux_append([ColM], LAlf4, LAlf6), aux_append([RowM], LNum4, LNum6));
	(append([], LAlf4, LAlf6), append([], LNum4, LNum6))),
	getMatrixElemAt(RowM, Col, Tab, Elem12), getMatrixElemAt(Row2M, Col, Tab, Elem13),
	(aux_check_equal(Elem12, Elem13, EPiece, Piece, LAlf6, LNum6, Col, RowM) -> (write('chega6 '), aux_append([Col], LAlf6, LAlf7), aux_append([RowM], LNum6, LNum7));
	(append([], LAlf6, LAlf7), append([], LNum6, LNum7))),
	
	getMatrixElemAt(RowM, ColP, Tab, Elem14), getMatrixElemAt(RowM, Col2P, Tab, Elem15), 
	(aux_check_equal(Elem14, Elem15, EPiece, Piece, LAlf7, LNum7, ColP, RowM), write(LAlf7)-> (write('chega7'),aux_append([ColP], LAlf7, LAlf8), aux_append([RowM], LNum7, LNum8));
	(append([], LAlf7, LAlf8), append([], LNum7, LNum8))),
	getMatrixElemAt(Row, ColP, Tab, Elem16), getMatrixElemAt(Row, Col2P, Tab, Elem17), 
	(aux_check_equal(Elem16, Elem17, EPiece, Piece, LAlf8, LNum8, ColP, Row) -> (write('chega8'),aux_append([ColP], LAlf8, LAlf9), aux_append([Row], LNum8, LNum9));
	(append([], LAlf8, LAlf9), append([], LNum8, LNum9))),
	
	aux_append(LAlf9, [], LAlff), aux_append(LNum9, [], LNumm)/*,nl,write(' ---> '), write(LAlff)*/, nl.
	
	
check_capture(_, _, 0, 0, _, _, LAlff3, LNumm3, L80, L81):-
	L80 = LAlff3,
	L81 = LNumm3.
	
check_capture(Player, Tab, 5, 0, LAlf, LNum, _, _, L80, L81):-
	check_capture(Player, Tab, 0, 0, LAlf, LNum, LAlf, LNum, L80, L81).
	
check_capture(Player, Tab, 0, Row, LAlf,LNum, LAlff3, LNumm3, L80, L81) :-
	RowM is Row - 1,
	(RowM is 0 -> (check_capture(Player, Tab, 5, RowM, LAlf, LNum, LAlf, LNum, L80, L81));
				(check_capture(Player, Tab, 5, RowM, LAlf, LNum, LAlff3, LNumm3, L80, L81))).
	
	
check_capture(Player, Tab, Col, Row, LAlf,LNum, LAlff3, LNumm3, L80, L81) :-
	Col > 0,
	Row > 0,
	choose_piece(Player, Piece),
	choose_enemy_piece(Player, EnemyPiece),
	getMatrixElemAt(Row, Col, Tab, Elem),
	(Elem = Piece -> (aux_check_get(Row, Col, EnemyPiece, Piece, LAlf, LNum, LAlff, LNumm, Tab)); true),
	%%write( 'FORA ---> '),write(LAlff),nl,nl,

	(var(LAlff) -> (aux_append([], LAlf, LAlff0));
				aux_append([], LAlff, LAlff0)),
	(var(LNumm) -> (aux_append([], LNum, LNumm0));
				aux_append([], LNumm, LNumm0)),
	Col2 is Col - 1,
	check_capture(Player, Tab, Col2, Row, LAlff0, LNumm0, LAlff3, LNumm3, L80, L81).
	
	
	
	
	
teste_check_capture:-
		Tab =[ ['.','.','B','.','.'],
    	   ['.','R','R','R','.'],
    	   ['.','R','B','B','.'],
    	   ['.','R','R','R','.'],
    	   ['.','.','.','B','.'] ],
		R is 1,
		B is 2,
		Player is 1,
		cls,
		print_tab(Tab,R,B),
		check_capture(Player, Tab, 5, 5, [], [], [], [], L80, L81),
		test_capture(Player, L80, L81).
		/*nl,
		write(L80),
		nl,
		write(L81),
		*/
   
   
  
			
			
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%  FIM CHECK_CAPTURE  %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%





%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%       CAPTURE       %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


parify_list([], [], Lres, Lres).

parify_list([HA|ResA], [HN|ResN],L, Lres):-    %% Creates a list with pairs that represent a piece position
append([HA], [HN], L1),
append([L1], L, L2),
parify_list(ResA, ResN, L2, Lres).

%%LIST  MANIP%%
	
delete_all(_,[],[]).

delete_all(X, [Y|R], [Y | R2]) :-
	X\=Y,
	delete_all(X,R,R2).
	
delete_all(X, [X|R], L):-
	delete_all(X, R, L).

delete_all_list([], L5, L5).
	
delete_all_list([X|R], L5, L6):-
	delete_all(X, L5, L3),
	delete_all_list(R, L3, L6).
	
%%LIST MANIP%%
test_capture(Player, Lista_A, Lista_N) :-
Tab =[ ['.','.','.','.','.'],
    	   ['.','.','R','B','R'],
    	   ['.','.','B','B','.'],
    	   ['.','.','.','.','R'],
    	   ['.','.','.','.','.'] ],
		R is 1,
		B is 2,
		%%cls,
		print_tab(Tab,R,B),
		capture(Player, Lista_A, Lista_N,R, B, Re, Be, Tab, TabEx),
		print_tab(TabEx, Re, Be).
		
		

get_coords_from_pair([H|Res], X, Y):-
X is H,
Y is Res.

		
replace_piece(_, [], Tab, Tab).

replace_piece(Piece, [CH|CRes], Tab, TabEx):-
	get_coords_from_pair(CH, X, Y),
	set_piece_tabuleiro(Y, X, Piece, Tab, TabEx2),
	replace_piece(Piece, CRes, TabEx2, TabEx).

capture(Player, Lista_A, Lista_N, Stack_Pl, Stack_En, Stack_Exit_Pl, Stack_Exit_En, Tab, TabEx):-

	
		check_capture(1, Tab, 5, 5, [], [], [], [], L80, L81),
		parify_list(Lista_A, Lista_N, [], LresR),
		parify_list(L80, L81, [], Lres8),
		delete_all_list(LresR, Lres8, Lres),
		/*nl, 
		write(Lres),
		nl,*/
		choose_piece(Player, Piece),
		replace_piece(Piece, Lres, Tab, Tab2),
		Stack_Exit_Pl is Stack_Pl -1,
		Stack_Exit_En is Stack_En + 1,
		TabEx = Tab2.
		
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     FIM CAPTURE     %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

test_quantas:-
	Tab =[ ['.','R','.','.','.'],
    	   ['R','.','R','B','R'],
    	   ['.','R','B','B','.'],
    	   ['.','.','.','.','R'],
    	   ['.','.','.','.','.'] ],
	quantas(Tab, 'R', 0, 5, 5, Number_P),
	write(Number_P).

	
	
quantas(_, _, Counter, 0, 0, Counter).
quantas(Tab, Piece, Counter, 5, 0, Number_P):-
	quantas(Tab, Piece, Counter, 0, 0, Number_P).
quantas(Tab, Piece, Counter, 0, Row, Number_P):-
	Row2 is Row - 1,
	quantas(Tab, Piece, Counter, 5, Row2, Number_P).

quantas(Tab, Piece, Counter, Col, Row, Number_P):-
	Col > 0,
	Row > 0,
	getMatrixElemAt(Row, Col, Tab, Elem2),
	(Elem2 = Piece -> (Counter2 is Counter + 1);
					Counter2 is Counter
	),
	Col2 is Col - 1,
	quantas(Tab, Piece, Counter2, Col2, Row, Number_P).
	
	

	
%%%%%%%%%%%%%%%%%%%%%%%%%%%%% Overview Functions   %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


valid_moves(_, _, ListOfMoves):-
ListOfMoves = ['move(Player, Tab_entrada, Tab_saida)', 'push(Player,Tab_entrada,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida)', 'sacrifice[Not_acessible_out_of_game]'].

move(Player,Tab_entrada,Tab_saida):-
cls,
print_tab(Tab_entrada, 0, 0),
move_piece(Player, Tab_entrada, Tab_saida).


push(Player,Tab_entrada,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida):-
cls,
push_piece_main(Player,Tab_entrada,Tab_saida,Stack_R,Stack_B,Stack_R_saida,Stack_B_saida).



game_over(Tab, Winner):-
vencedor(Tab, Winner, NF).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%