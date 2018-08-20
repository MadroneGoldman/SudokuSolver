# SudokuSolver
An application that solves Sudoku puzzles with a constraint propagation algorithm

# Usage
Download .jar file and puzzles from the [Release Page](https://github.com/MadroneGoldman/SudokuSolver/releases/tag/1) or clone the repository and build the project yourself. If using the executable .jar file, follow instruction. There are two options either select the option to run all 5 challenge puzzles or select the option to run a specifice puzzle and then enter the file path for that puzzle.

# The Problem
[Sudoku](https://en.wikipedia.org/wiki/Sudoku) is a number-placment puzzle where you start with a 9x9 grid with 17 or more cells filled in.
The objective is to fill the grid with digits so that each column, each row, and each of the nine 3×3 subgrids that compose the grid (also called "boxes", "blocks", or "regions") contains all of the digits from 1 to 9.

# Method
I chose to model Sudoku as a constraint satisfaction problem where I build a sequence of constraints based on Sudoku rules and reduce the domain size of the variables (each cell) through constraint propagation. Lots of "easier" sudoku boards are can be solved with just constraint propagation but unfortunatly harder puzzles can never be solved through constraint propagation alone. Because of this, I use a standard backtracking search algorithm after the constraint propagation. Backtracking is a brute force approach to a constraint satisfaction problem and although it can be slow, it will always find the answer to any valid puzzle. For easier puzzles the backtracker in my application simply places the already solved values into eah cell and double checks that they are valid placments. But for harder puzzles the backtracker is used to find the solution after the search tree has been significantly pruned down by constraint propagation.

The advantage of this method is that it is simple, has a faster solving time than backtracking, and has the ability to solve all sudokus.

## Constraint Propagation
My constraint propagation is based off of the [AC-3 algorithm](https://en.wikipedia.org/wiki/AC-3_algorithm):

```

 Input:
   A set of variables X
   A set of domains D(x) for each variable x in X. D(x) contains vx0, vx1... vxn, the possible values of x
   A set of unary constraints R1(x) on variable x that must be satisfied
   A set of binary constraints R2(x, y) on variables x and y that must be satisfied
   
 Output:
   Arc consistent domains for each variable.
 
 function ac3 (X, D, R1, R2)
  Initial domains are made consistent with unary constraints.
     for each x in X
         D(x) := { vx in D(x) | R1(x) }   
      'worklist' contains all arcs we wish to prove consistent or not.
     worklist := { (x, y) | there exists a relation R2(x, y) or a relation R2(y, x) }
 
     do
         select any arc (x, y) from worklist
         worklist := worklist - (x, y)
         if arc-reduce (x, y) 
             if D(x) is empty
                 return failure
             else
                 worklist := worklist + { (z, x) | z != y and there exists a relation R2(x, z) or a relation R2(z, x) }
     while worklist not empty
 
 function arc-reduce (x, y)
     bool change = false
     for each vx in D(x)
         find a value vy in D(y) such that vx and vy satisfy the constraint R2(x, y)
         if there is no such vy {
             D(x) := D(x) - vx
             change := true
         }
     return change
```

but with a few simple rules as constraints.
###### Rules
1.Only values allowed by the cellDomain HashSet are checked.
2.A certain value is allowed in no other cell in the same unit.
3.If a certain value is only allowed in one cell in a given peer group then it must be the value of that cell.

I'm calling a collection of nine squares (column, row, or box) a unit and the squares that share a unit the peers.A peer group is the collection of all peers in a unit with a given cell.With this information you can see that rule 2. is actually a generalization of three fundamental sudoku rules: A certain value is allowed in no other cell in the same column, row, or box.

In my application I have two crucial data structures. One is a 2-dimensional 9x9 integer array that represents the puzzle board and the other is an 81 unit long list of HashSets called **cellDomain**. Each element in the list represents one cell on the board and the HashSet holds the possible values or domain of the given cell. Two classes **Eliminator** and **OnlyChoice** represent rule 2. and rule 3. respectively. These classes iterate through cellDomain and enforce their rules removing values from the HashSet's before turning the puzzle over to the search method.

## Search
The backtracking portion of my code is a depth first search that tries all possible combinations of the each value left in cellDomain for each cell on the board and checks for validity. Like I said earlier; for the easiest sudokus only 1 possible value is left for each cell at this point because the puzzle has already been solved with constraint propagation, but harder sudokus may have many possible values left to check.

# Results
Here are the solutions and average running times for each of the 5 challenge puzzles on my computer a 4.20GHz processor with 16 GB of RAM. 

## Puzzle 1 Solution
```

428159673
196374825
375862941
981423567
564718392
732596184
243681759
617945238
859237416
Average SolveTime: < 1 Millisecond
```

## Puzzle 2 Solution
```

921768543
463519872
875432961
594283617
712645389
638971425
349827156
256194738
187356294
Average SolveTime: < 1 Millisecond
```

## Puzzle 3 Solution
```

921768543
463519872
875432961
594283617
712645389
638971425
349827156
256194738
187356294
Average SolveTime: < 1 Millisecond
```

## Puzzle 4 Solution
```

534678912
672195348
198342567
859761423
426853791
713924856
961537284
287419635
345286179
Average SolveTime: < 1 Millisecond
```

## Puzzle 5 Solution
```

915348627
348672159
762159483
697814235
534726918
821935764
476581392
183297546
259463871
Average SolveTime: 1 Millisecond
```
# Testing
I did unit testing for the following cases:
* Invalid Board(less than 17 clues)
* Invalid Board(greater or less than 81 cells)
* Invalid Board(non unique cells in a row,column, or box together)
* Invalid Board Format
* Very Hard and Very easy Sudokus puzzles
* Wrong Input File Type
* No Input File 

# Notes
I would like to point out a possible downfall of my program: Some Sudoku puzzles have more than one solution but my algorithm will only ever find one. My algorithm will also never know if more than one solution is possible for a Sodoku.

It should also be noted that my OnlyChoice class and methods which enforce Rule 3: If a certain value is only allowed in one cell in a given peer group then it must be the value of that cell. Ended up doing very little pruning on most puzzles. All the constraint propagation heavy lifting was done by the methods enforcing rules 1 and 2. But some light benchmarking revealed that OnlyChoice still speeds up the solution time more than it slows it down. There are also some additional rules/constraints that can be added to further speed up the algorithm and extend the functionality of the application. I listed a few below.
## Additional Rules
1. A certain value is allowed only on one column or row inside a section, thus we can eliminate this value from that row or column in the other sections.
2. [Naked Pairs.](https://www.sudokuoftheday.com/techniques/naked-pairs-triples/) If two cells in the same peer group have the same two possible values then those values can be removed from the domain of the rest of the peer group.
3. Naked Triples. Same idea but with three cells.

