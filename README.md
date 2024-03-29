# Ring of Rings Algorithm Implementation
## Getting started
IDE version: Intellij IDEA 2021.2
SDK: JKD1.8.0_301
### How to run:
Class *SingleRingDemo* is the implementation of task *3.1*. In this demo there is a user-interaction interface. It will tell you to enter the number of processors and display the ring initialized. Then it will let you choose to show the whole procedure or just the final result. In summary, run the main method and just follow its instructions.

Class *RingOfRingsDemo* is the implementation of task *3.2*. This demo is more complicated and there's also a user-interaction interface. It will guide you to decide the number of total rings and the amount of processors in each ring. Then it will tell you to choose the type of id distribution of rings. Finally, it let you choose to show the whole procedure or just the final result. Just run the main method and following the instructions would be fine.
## Content of the program
### Class introduction
Node class is the processor model. Ring class is the model of ring-network. LCR_Algorithm class is some implementation about leader-election procedure in the ring or ring-of-rings network. They are quite straightforward to understand with annotations.
### Data structure used to realize the model
Linked list is used to implement the network ring model. A Node object holds another object as its attribute, one by one, then form a loop. For example, the first node connects to the second node, second connects to the third, ... , the last but one connects to the last and the last connects back to the first. The display of a ring in this project is printing from top to bottom in the console, but it should be recognized that it's actually a loop, which means the node printed at the bottom connects back to the first node printed on the top.
### Attention
1. When the simulator asks user to press 'Enter' to continue, *the cursor has to be at that input line in the console* (by default it is so don't worry too much), otherwise it wouldn't respond at the first time you press 'Enter'.
2. If the size of rings constructed by user is very large, user may need to slide up for a while to see the detailed process of every round in every ring. If not necessary, try to avoid constructing very large rings or very large amount of rings for the convenience of checking the whole process.  
