CSC 316 HOMEWORK 4 

Programming assignment 

	Yefei Huang
	yhuang25@ncsu.edu
	#200009692

***************************************************************************************************************************
0. Hash Function: 

ELFhash Function(Textbook page 314) since I treated every key from the input as a String. It takes as input a string of arbitrary length. It works well with both short and long strings, and every letter of the string has equal effect. It mixes up the decimal values of the characters in a way that is likely to give an even distribution among positions in the hash table.

It performs well in my program and the time consumed is very limited.  See details in below table of running information.

***************************************************************************************************************************
1. Collision avoidance policy:

I chose linear probing at first and it turned out an easy implementation with good performance, since it does not bring too many calculation but avoid the collision in such an efficient way.

This collision avoidance policy works well and brings a good help to my program, as expected.

***************************************************************************************************************************

2. Running time and memory usage of each data sets

	File name				Running time(s)			Memory usage(bytes)                 M(used in the program)							
0	sat10000				4.44						895191                                      27127
1	sat1000				0.422				       143121                                      4337
2	sat100				0.109					23727                                        719
3	sat10				0.05						2343                                          71
4	ncd1				0.032					161                                            23
5	ncd2				0.132					3223                                          293
6	ncd3				0.300					19547                                        1777
7	ncd4				0.377					60181                                        5471
8	multiklas				0.355					48976                                        3061 

	Average running time = 0.69s
	
	Average memory usage = 132941 bytes

***************************************************************************************************************************
3. Steps to run the program:

	3.0 For different files, use different M in above table. Change the value of M in line 7.
	
	3.1 Change the file name in line 116.

	3.2 Run the program, it will give the running information and it provides you the way to search the key and index. 

