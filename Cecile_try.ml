type cellule =
  | C (* case *)
  | N (* nord *) | S | E | O

type food =
  | Pomme
  | Fraise
  | Fromage
  | Riz

type action =
  | Rien
  | Avancer
  | Tourner_vers of cellule
  | Manger of food

type condition =
  | Vide
  | Ami of cellule
  | Ennemi of cellule
  | Food of food



type etat = int
type transition = etat * condition * action * etat
type automate = transition list

      
	
let autsimple = 	
  [ (4, Food(Pomme), Manger(Pomme), 3) ;
    (1, Ennemi(S), Tourner_vers(S), 2) ;
    (1, Ennemi(E), Tourner_vers(E), 2) ;
    (4, Ennemi(O), Tourner_vers(O), 1) ;
    (2, Ami(N), Avancer, 3) ;
    (3, Vide, Rien, 4) ;
  ]
;;


  
let (cellule_to_int: cellule -> int) = function
  | C -> 0
  | N -> 1
  | S -> 2
  | E -> 3
  | O -> 4

let (condition_to_int: condition -> int) =  function
  | Vide -> 0
  | Ami(cellule) -> 1 + (cellule_to_int cellule) (* 1..5 *)
  | Ennemi(cellule) -> 6 + (cellule_to_int cellule) (* 6..10 *)
  | Food(food) -> 20

let (action_to_int: action -> int) = function
   | Rien -> 0
   | Avancer -> 1
   | Manger -> 2
   | Tourner_vers (cellule) -> 12 + (cellule_to_int cellule)

   
let (traduction_transition: transition -> int * int * int * int) = fun (src,condition,action,tgt) ->
   (src, condition_to_int condition, action_to_int action, tgt)

let (traduction_automate: automate -> (int * int * int * int) list) = fun automate ->
   List.map traduction_transition automate ;;


let trad_aut1 = traduction_automate autsimple ;;
